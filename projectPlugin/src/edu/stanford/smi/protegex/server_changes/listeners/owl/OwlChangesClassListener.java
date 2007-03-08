package edu.stanford.smi.protegex.server_changes.listeners.owl;

import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Model;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.model.RDFSClass;
import edu.stanford.smi.protegex.owl.model.event.ClassAdapter;
import edu.stanford.smi.protegex.server_changes.ChangesDb;
import edu.stanford.smi.protegex.server_changes.ChangesProject;
import edu.stanford.smi.protegex.server_changes.ServerChangesUtil;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel.ChangeCls;
import edu.stanford.smi.protegex.server_changes.model.generated.Change;
import edu.stanford.smi.protegex.server_changes.model.generated.Instance_Added;
import edu.stanford.smi.protegex.server_changes.model.generated.Ontology_Component;
 
public class OwlChangesClassListener extends ClassAdapter {
    private OWLModel om;
    private ChangesDb changes_db;
    private KnowledgeBase changesKb;
    
    public OwlChangesClassListener(OWLModel om) {
        this.om = om;
        changes_db = ChangesProject.getChangesDb(om);
        changesKb = changes_db.getChangesKb();
    }

	public void instanceAdded(RDFSClass arg0, RDFResource arg1) {
		String instText = arg1.getBrowserText();
                String instName = arg1.getName();
		StringBuffer context = new StringBuffer();
		context.append("Instance Added: ");
		context.append(instText);
		context.append(" (instance of: " );
		context.append(arg0.getBrowserText() );
		context.append(")");
        
        Ontology_Component applyTo = changes_db.getOntologyComponent(instName, true);
        
        Instance_Added change = (Instance_Added) changes_db.createChange(ChangeCls.Instance_Added);
        changes_db.finalizeChange(change, applyTo, context.toString());
	}

	public void instanceRemoved(RDFSClass arg0, RDFResource arg1) {
            ChangesDb changesDb = ChangesProject.getChangesDb(om);
            String instText = changesDb.getPossiblyDeletedBrowserText(arg1);
            StringBuffer context = new StringBuffer();
            context.append("Instance Removed: ");
            context.append(instText);
            context.append(" (instance of: ");
            context.append(arg0.getBrowserText());
            context.append(")");
            
            Ontology_Component applyTo = changes_db.getOntologyComponent(arg1, true);
            
            Change change = changes_db.createChange(ChangeCls.Instance_Removed);
            changes_db.finalizeChange(change, applyTo, context.toString());
	}

	public void addedToUnionDomainOf(RDFSClass arg0, RDFProperty arg1) {
	}

	public void removedFromUnionDomainOf(RDFSClass arg0, RDFProperty arg1) {
	}

	public void subclassAdded(RDFSClass arg0, RDFSClass arg1) {
		String clsName = arg1.getName();
		String clsText = arg1.getBrowserText();
		StringBuffer context = new StringBuffer();
		context.append("Subclass Added: ");
		context.append(clsName);
		context.append(" (added to: ");
		context.append(arg0.getBrowserText());
		context.append(")");
		
        Ontology_Component applyTo = changes_db.getOntologyComponent(clsName, true);
        
        Change change = changes_db.createChange(ChangeCls.Subclass_Added);
        changes_db.finalizeChange(change, applyTo, context.toString());
	}

	public void subclassRemoved(RDFSClass arg0, RDFSClass arg1) {
            String clsName = changes_db.getPossiblyDeletedBrowserText(arg1);
            StringBuffer context = new StringBuffer();
            context.append("Subclass Removed: ");
            context.append(clsName);
            context.append(" (removed from: ");
            context.append(arg0.getBrowserText());
            context.append(")");
            
            Ontology_Component applyTo = changes_db.getOntologyComponent(arg1, true);
            
            Change change = changes_db.createChange(ChangeCls.Subclass_Removed);
            changes_db.finalizeChange(change, applyTo, context.toString());
	}

	public void superclassAdded(RDFSClass arg0, RDFSClass arg1) {
		String clsName = arg1.getName();
		String clsText = arg1.getBrowserText();
		StringBuffer context = new StringBuffer();
		context.append("Superclass Added: ");
		context.append(clsText);
		context.append(" (added to: ");
		context.append(arg0.getBrowserText());
		context.append(")");
		
        
        Ontology_Component applyTo = changes_db.getOntologyComponent(clsName, true);
        
        Change change = changes_db.createChange(ChangeCls.Superclass_Added);
        changes_db.finalizeChange(change, applyTo, context.toString());
	}

	public void superclassRemoved(RDFSClass arg0, RDFSClass arg1) {
		String a = arg1.getName();
		String b = arg1.getBrowserText();
		String clsName = changes_db.getPossiblyDeletedBrowserText(arg1);
		StringBuffer context = new StringBuffer();
		context.append("Superclass Removed: ");
		context.append(clsName);
		context.append(" (removed from: ");
		context.append(arg0.getBrowserText());
		context.append(")");
        
        Ontology_Component applyTo = changes_db.getOntologyComponent(arg1, true);
        
        Change change = changes_db.createChange(ChangeCls.Superclass_Removed);
        changes_db.finalizeChange(change, applyTo, context.toString());
	}
}
