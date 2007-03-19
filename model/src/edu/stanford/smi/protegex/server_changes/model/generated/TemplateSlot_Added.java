
// Created on Mon Mar 19 14:22:16 PDT 2007
// "Copyright Stanford University 2006"

package edu.stanford.smi.protegex.server_changes.model.generated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import edu.stanford.smi.protege.model.DefaultSimpleInstance;
import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.ModelUtilities;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protegex.server_changes.model.ChangeDateComparator;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel.ChangeCls;


/** 
 */
public class TemplateSlot_Added extends Class_Change {

	public TemplateSlot_Added() {
	}


	public TemplateSlot_Added(KnowledgeBase kb, FrameID id ) {
		super(kb, id);
	}


	public void setAssociatedProperty(Instance associatedProperty) {
		ModelUtilities.setOwnSlotValue(this, "associatedProperty", associatedProperty);	}
	public Instance getAssociatedProperty() {
		return ((Instance) ModelUtilities.getOwnSlotValue(this, "associatedProperty"));
	}

	public void setChanges_Slot_0(String changes_Slot_0) {
		ModelUtilities.setOwnSlotValue(this, "changes_Slot_0", changes_Slot_0);	}
	public String getChanges_Slot_0() {
		return ((String) ModelUtilities.getOwnSlotValue(this, "changes_Slot_0"));
	}
// __Code above is automatically generated. Do not change
}
