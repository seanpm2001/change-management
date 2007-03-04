package edu.stanford.smi.protegex.server_changes.listeners;

import edu.stanford.smi.protege.event.ClsEvent;
import edu.stanford.smi.protege.event.ClsListener;
import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Model;
import edu.stanford.smi.protegex.server_changes.ChangesDb;
import edu.stanford.smi.protegex.server_changes.ChangesProject;
import edu.stanford.smi.protegex.server_changes.ServerChangesUtil;
import edu.stanford.smi.protegex.server_changes.TransactionState;

public class ChangesClsListener implements ClsListener{
    private KnowledgeBase kb;
    private ChangesDb changesDb;
    private KnowledgeBase changesKb;
    
    public ChangesClsListener(KnowledgeBase kb) {
        this.kb = kb;
        changesDb = ChangesProject.getChangesDb(kb);
        changesKb = changesDb.getChangesKb();
    }

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directInstanceAdded(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directInstanceAdded(ClsEvent event) {
		Instance addedInst = event.getInstance();
		Cls clsOfInst = event.getCls();
		
		StringBuffer context = new StringBuffer();
		context.append("Added Instance: ");
		context.append(addedInst.getBrowserText());
		context.append(" (instance of ");
		context.append(clsOfInst.getBrowserText());
		context.append(")");
		
		Instance changeInst = ServerChangesUtil.createChange(kb,
												changesKb,
												Model.CHANGETYPE_INSTANCE_ADDED, 
												clsOfInst.getName(), 
												context.toString(), 
												Model.CHANGE_LEVEL_INFO);
		
	
		ChangesProject.postProcessChange(kb, changesKb, changeInst);
		// Create artificial transaction for create slot
		if (ChangesProject.getInCreateSlot(kb) && ChangesProject.getIsInTransaction(kb)) {
            changesDb.getTransactionState().commitTransaction();
            changesDb.setInCreateClass(false);
		}
		
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directInstanceRemoved(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directInstanceRemoved(ClsEvent event) {
		Instance removedInst = event.getInstance();
		Cls clsOfInst = event.getCls();
		
		StringBuffer context = new StringBuffer();
		context.append("Removed Instance: ");
		context.append(removedInst.getBrowserText());
		context.append(" (instance of ");
		context.append(clsOfInst.getBrowserText());
		context.append(")");
		
		Instance changeInst = ServerChangesUtil.createChange(kb,
												changesKb,
												Model.CHANGETYPE_INSTANCE_REMOVED, 
												clsOfInst.getName(), 
												context.toString(), 
												Model.CHANGE_LEVEL_INFO);
	
		ChangesProject.postProcessChange(kb, changesKb, changeInst);
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directSubclassAdded(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directSubclassAdded(ClsEvent event) {
		Cls subClass = event.getSubclass();
		Cls superClass = event.getCls();
		
		StringBuffer context = new StringBuffer();
		context.append("Added subclass: ");
		context.append(subClass.getBrowserText());
		context.append(" (subclass of ");
		context.append(superClass.getBrowserText());
		context.append(")");
		
		Instance changeInst = ServerChangesUtil.createChange(kb,
												changesKb,
												Model.CHANGETYPE_SUBCLASS_ADDED, 
												subClass.getName(), 
												context.toString(), 
												Model.CHANGE_LEVEL_INFO);
		ChangesProject.postProcessChange(kb, changesKb, changeInst);
		
        TransactionState tstate = changesDb.getTransactionState();
		// Create artificial transaction for create class
		if (tstate.inTransaction() && changesDb.getInCreateClass()) {
            tstate.commitTransaction();
            changesDb.setInCreateClass(false);
			ChangesProject.createTransactionChange(kb, ChangesProject.TRANS_SIGNAL_TRANS_END);
			ChangesProject.setInCreateClass(kb, false);
		}
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directSubclassMoved(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directSubclassMoved(ClsEvent event) {
		// Method is not used/called
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directSubclassRemoved(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directSubclassRemoved(ClsEvent event) {
		Cls subClass = event.getSubclass();
		Cls superClass = event.getCls();
		
		StringBuffer context = new StringBuffer();
		context.append("Removed subclass: ");
		context.append(subClass.getBrowserText());
		context.append(" (subclass of ");
		context.append(superClass.getBrowserText());
		context.append(")");
		
		Instance changeInst = ServerChangesUtil.createChange(kb,
												changesKb,
												Model.CHANGETYPE_SUBCLASS_REMOVED, 
												subClass.getName(), 
												context.toString(), 
												Model.CHANGE_LEVEL_INFO);
		ChangesProject.postProcessChange(kb, changesKb, changeInst);
		
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directSuperclassAdded(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directSuperclassAdded(ClsEvent event) {
		Cls subClass = event.getSubclass();
		Cls superClass = event.getCls();
	
		StringBuffer context = new StringBuffer();
		context.append("Added superclass: ");
		context.append(subClass.getBrowserText());
		context.append(" (subclass of ");
		context.append( superClass.getBrowserText());
		context.append(")");
		
		Instance changeInst =ServerChangesUtil.createChange(kb,
												changesKb,
												Model.CHANGETYPE_SUPERCLASS_ADDED,
												subClass.getName(), 
												context.toString(), 
												Model.CHANGE_LEVEL_INFO);
		ChangesProject.postProcessChange(kb, changesKb, changeInst);
	
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#directSuperclassRemoved(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void directSuperclassRemoved(ClsEvent event) {
		Cls subClass = event.getSubclass();
		Cls superClass = event.getCls();
	
		StringBuffer context = new StringBuffer();
		context.append("Removed superclass: ");
		context.append(subClass.getBrowserText());
		context.append(" (subclass of ");
		context.append(superClass.getBrowserText());
		context.append(")");
		
		Instance changeInst = ServerChangesUtil.createChange(kb,
												changesKb,
												Model.CHANGETYPE_SUPERCLASS_REMOVED, 
												subClass.getName(), 
												context.toString(), 
												Model.CHANGE_LEVEL_INFO);
		
		ChangesProject.postProcessChange(kb, changesKb, changeInst);
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#templateFacetAdded(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void templateFacetAdded(ClsEvent event) {
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#templateFacetRemoved(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void templateFacetRemoved(ClsEvent event) {
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#templateFacetValueChanged(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void templateFacetValueChanged(ClsEvent event) {
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#templateSlotAdded(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void templateSlotAdded(ClsEvent event) {
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#templateSlotRemoved(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void templateSlotRemoved(ClsEvent event) {
	}

	/* (non-Javadoc)
	 * @see edu.stanford.smi.protege.event.ClsListener#templateSlotValueChanged(edu.stanford.smi.protege.event.ClsEvent)
	 */
	public void templateSlotValueChanged(ClsEvent event) {
	}
}
