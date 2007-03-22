
// Created on Wed Mar 21 17:52:27 PDT 2007
// "Copyright Stanford University 2006"

package edu.stanford.smi.protegex.server_changes.model.generated;

import java.util.*;
import java.beans.*;
import edu.stanford.smi.protege.model.*;
import edu.stanford.smi.protege.util.*;


/** 
 */
public abstract class Proposal extends Annotation {

	public Proposal(KnowledgeBase kb, FrameID id ) {
		super(kb, id);
	}

	public void setSubject(String subject) {
		ModelUtilities.setOwnSlotValue(this, "subject", subject);	}
	public String getSubject() {
		return ((String) ModelUtilities.getOwnSlotValue(this, "subject"));
	}
// __Code above is automatically generated. Do not change
}
