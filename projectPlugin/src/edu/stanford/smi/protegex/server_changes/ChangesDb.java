package edu.stanford.smi.protegex.server_changes;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;

import edu.stanford.smi.protege.model.Frame;
import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.server.RemoteSession;
import edu.stanford.smi.protege.server.Server;
import edu.stanford.smi.protege.server.framestore.ServerFrameStore;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protege.util.URIUtilities;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel.ChangeCls;
import edu.stanford.smi.protegex.server_changes.model.generated.Change;
import edu.stanford.smi.protegex.server_changes.model.generated.Ontology_Component;
import edu.stanford.smi.protegex.server_changes.model.generated.Timestamp;
import edu.stanford.smi.protegex.storage.rdf.RDFBackend;

public class ChangesDb {

    private KnowledgeBase changes;
    private Project changesProject;
    ChangeModel model;
    private TransactionUtility transactionUtility;
    
    private Map<RemoteSession, Integer> transCount = new HashMap<RemoteSession, Integer>();
    private Map<RemoteSession, Stack> transStack   = new HashMap<RemoteSession, Stack>();
    
    private Set<RemoteSession> inTransaction = new HashSet<RemoteSession>();
    private Set<RemoteSession> inCreateClass = new HashSet<RemoteSession>();
    private Set<RemoteSession> inCreateSlot  = new HashSet<RemoteSession>();
    
    private Map<String, Instance> lastCreateByName = new HashMap<String, Instance>();
    
    private Map<FrameID, String> frameIdMap = new HashMap<FrameID, String>();
    
    private Map<String, Ontology_Component> nameToOntologyComponentMap
                    = new HashMap<String, Ontology_Component>();
    
    public ChangesDb(KnowledgeBase kb) {
        getOrCreateChangesProject(kb);
        model = new ChangeModel(changes);
        transactionUtility = new TransactionUtility(kb, changes);
        Timestamp.initialize(model);
        addNameChangeListener(kb);
    }
    
    private void getOrCreateChangesProject(KnowledgeBase kb) {
        final Project project = kb.getProject();

        if (project.isMultiUserServer()) {
            Server server = Server.getInstance();
            String annotationName = (String) new GetAnnotationProjectName(kb).execute();
            if (annotationName == null) {
                throw new RuntimeException("Annotation project not configured on server (use the " + 
                        GetAnnotationProjectName.METAPROJECT_ANNOTATION_PROJECT_SLOT +
                " slot)");
            }
            changesProject = server.getProject(annotationName);
            changes = changesProject.getKnowledgeBase();
            return;
        }

        ArrayList errors = new ArrayList();

        URI annotationProjURI = getAnnotationProjectURI(project);

        File annotationProjFile = new File(annotationProjURI);
        
        //TODO: TT Check whether this works with real URIs
        if (annotationProjFile.exists()) {
            //annotation ontology exists                    
            changesProject = Project.loadProjectFromURI(annotationProjURI, errors);

        } else {
            //annotations ontology does not exist and it will be created
            URI changeOntURI = null;
            try {
                changeOntURI = ChangesProject.class.getResource("/projects/changes.pprj").toURI();
            } catch (URISyntaxException e) {
                Log.getLogger().log(Level.WARNING, "Could not find Changes Ontology", e);
            }

            changesProject = Project.loadProjectFromURI(changeOntURI, errors);

            RDFBackend.setSourceFiles(changesProject.getSources(), ChangesProject.ANNOTATION_PROJECT_NAME_PREFIX + project.getName() + ".rdfs", ChangesProject.ANNOTATION_PROJECT_NAME_PREFIX + project.getName() + ".rdf", ChangesProject.PROTEGE_NAMESPACE);
            changesProject.setProjectURI(annotationProjURI);

        }


        if (changesProject == null) {
            Log.getLogger().warning("Failed to find or create annotation project");
            ChangesProject.displayErrors(errors);
        }

        changes = changesProject.getKnowledgeBase();
    }
    
    public static URI getAnnotationProjectURI(Project p) {
        return URIUtilities.createURI(p.getProjectDirectoryURI() + 
                                      "/" + 
                                      ChangesProject.ANNOTATION_PROJECT_NAME_PREFIX + 
                                      p.getName() + ".pprj");
    }
    
    private void addNameChangeListener(KnowledgeBase kb) {
        synchronized (changes) {
            for (Object o : model.getCls(ChangeCls.Change).getInstances()) {
                Change change = (Change) o;
                if (model.isCreateChange(change)) {
                    
                }
            }
        }
    }

    private RemoteSession getCurrentSession() {
        RemoteSession session = ServerFrameStore.getCurrentSession();
        if (session != null) return session;
        else return StandaloneSession.getInstance();
    }
    
    /* -------------------------------------Interfaces ------------------------------*/


    
    public KnowledgeBase getChangesKb() {
        return changes;
    }
    
    public Project getChangesProject() {
        return changesProject;
    }
    
    public ChangeModel getModel() {
        return model;
    }
    
    public TransactionUtility getTransactionUtility() {
        return transactionUtility;
    }
    
    public boolean isInTransaction() {
        return inTransaction.contains(getCurrentSession());
    }
    
    public void setInTransaction(boolean val) {
        RemoteSession session = getCurrentSession();
        if (val) {
            inTransaction.add(session);
        }
        else {
            inTransaction.remove(session);
        }
    }
    
    public int getTransactionCount() {
        RemoteSession session = getCurrentSession();
        Integer count = transCount.get(session);
        if (count == null) return 0;
        return count;
    }
    
    public void incrementTransactionCount() {
        RemoteSession session = getCurrentSession();
        Integer count = getTransactionCount();
        transCount.put(session, count+1);
    }
    
    public void decrementTransactionCount() {
        RemoteSession session = getCurrentSession();
        Integer count = getTransactionCount();
        transCount.put(session, count-1);
    }
    
    @SuppressWarnings("unchecked")
    public void pushTransStack(Object change) {
        Stack currentStack = getTransStack();
        currentStack.push(change);
    }
    
    public void setTransStack(Stack s) {
        transStack.put(getCurrentSession(), s);
    }
    
    public Stack getTransStack() {
        RemoteSession session = getCurrentSession();
        Stack currentStack = transStack.get(session);
        if (currentStack == null) {
            currentStack = new Stack();
            transStack.put(session, currentStack);
        }
        return currentStack;
    }
    
    public void clearTransStack() {
        transStack.remove(getCurrentSession());
    }
  
    public boolean isInCreateClass() {
        return inCreateClass.contains(getCurrentSession());
    }
    
    public void setInCreateClass(boolean val) {
        RemoteSession session = getCurrentSession();
        if (val) {
            inCreateClass.add(session);
        }
        else {
            inCreateClass.remove(session);
        }
    }
    
    public boolean isInCreateSlot() {
        return inCreateSlot.contains(getCurrentSession());
    }
    
    public void setInCreateSlot(boolean val) {
        RemoteSession session = getCurrentSession();
        if (val) {
            inCreateSlot.add(session);
        }
        else {
            inCreateSlot.remove(session);
        }
    }
    
    public Instance getRecentCreate(String name) {
        return lastCreateByName.get(name);
    }
    
    public void removeRecentCreate(String name) {
        lastCreateByName.remove(name);
    }
    
    public void addRecentCreate(String name, Instance change) {
        lastCreateByName.put(name, change);
    }
    
    public void updateMap(FrameID frameId, String name) {
        frameIdMap.put(frameId, name);
    }
    
    public String getPossiblyDeletedFrameName (Frame frame) {
        if (frame.isDeleted()) {
            return (String)frameIdMap.get(frame.getFrameID());
        }
        else {
            return frame.getName();
        }
    }
    
    public String getPossiblyDeletedBrowserText(Frame frame) {
        if (frame.isDeleted()) {
            return (String)frameIdMap.get(frame.getFrameID());
        }
        else {
            return frame.getBrowserText();
        }
    }
}
