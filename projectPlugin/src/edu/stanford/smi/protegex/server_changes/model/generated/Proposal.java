
// Created on Sat Mar 03 08:54:42 PST 2007
// "Copyright Stanford University 2006"

package edu.stanford.smi.protegex.server_changes.model.generated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collection;
import edu.stanford.smi.protege.model.DefaultSimpleInstance;
import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.ModelUtilities;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel.ChangeCls;


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
