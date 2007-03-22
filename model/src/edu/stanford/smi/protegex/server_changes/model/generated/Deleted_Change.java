
// Created on Wed Mar 21 17:52:27 PDT 2007
// "Copyright Stanford University 2006"

package edu.stanford.smi.protegex.server_changes.model.generated;

import java.util.*;
import java.beans.*;
import edu.stanford.smi.protege.model.*;
import edu.stanford.smi.protege.util.*;


/** 
 */
public abstract class Deleted_Change extends Change {

	public Deleted_Change(KnowledgeBase kb, FrameID id ) {
		super(kb, id);
	}

	public void setDeletionName(String deletionName) {
		ModelUtilities.setOwnSlotValue(this, "deletionName", deletionName);	}
	public String getDeletionName() {
		return ((String) ModelUtilities.getOwnSlotValue(this, "deletionName"));
	}
// __Code above is automatically generated. Do not change
}
