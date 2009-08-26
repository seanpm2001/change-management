package edu.stanford.bmir.protegex.chao.ontologycomp.api.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stanford.bmir.protegex.chao.ontologycomp.api.OntologyComponentFactory;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.Timestamp;
import edu.stanford.smi.protege.code.generator.wrapping.AbstractWrappedInstance;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protege.util.Log;

/**
 * Generated by Protege (http://protege.stanford.edu).
 * Source Class: Timestamp
 *
 * @version generated on Mon Aug 18 21:08:59 GMT-08:00 2008
 */
public class DefaultTimestamp extends AbstractWrappedInstance
         implements Timestamp {

    public DefaultTimestamp(Instance instance) {
        super(instance);
    }


    public DefaultTimestamp() {
    }

    // Slot date

    public String getDate() {
        return (String) getWrappedProtegeInstance().getOwnSlotValue(getDateSlot());
    }


    public Slot getDateSlot() {
        final String name = "date";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasDate() {
        return hasSlotValues(getDateSlot());
    }


    public void setDate(String newDate) {
        setSlotValue(getDateSlot(), newDate);
    }

    // Slot sequence

    public int getSequence() {
        java.lang.Integer value = (java.lang.Integer) getWrappedProtegeInstance().getOwnSlotValue(getSequenceSlot());
        return value == null ? null :
            (java.lang.Integer) value.intValue();
    }


    public Slot getSequenceSlot() {
        final String name = "sequence";
        return getKnowledgeBase().getSlot(name);
    }


    public boolean hasSequence() {
        return hasSlotValues(getSequenceSlot());
    }


    public void setSequence(int newSequence) {
        setSlotValue(getSequenceSlot(), new java.lang.Integer(newSequence));
    }

 // __Code above is automatically generated. Do not change

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss zzz");
	private static int sequence = 0;
	private Date time;

	public static void initialize() {
	    ;
	}

	public static Timestamp getTimestamp(KnowledgeBase changesKb) {
	    int counter;
	    Date date;
	    synchronized (Timestamp.class) {
	        date = new Date();
	        counter = sequence++;
	    }
	    return getTimestamp(changesKb, date, counter);
	}

	public static Timestamp getTimestamp(KnowledgeBase changesKb, Date date) {
	    int counter;
	    synchronized (Timestamp.class) {
	        counter = sequence++;
	    }
	    return getTimestamp(changesKb, date, counter);
	}
	
	private static Timestamp getTimestamp(KnowledgeBase changesKb, Date date, int counter) {
	    Timestamp ts = new OntologyComponentFactory(changesKb).createTimestamp(null);
	    ts.setDate(DATE_FORMAT.format(date));
	    ts.setSequence(counter);
	    return ts;
	}

	public long getTimeMillis() {
	    return getDateParsed().getTime();
	}

	public Date getDateParsed() {
	    if (time == null) {
	        String date = getDate();
	        try {
	            time = DATE_FORMAT.parse(date);
	        } catch (ParseException e) {
	            Log.getLogger().severe("Exception caught parsing the changes ontology - it is probably corrupted" + e);
	        }
	    }
	    return time;
	}


    /*
     * Frames are comparable so it is better not to replicate/overwrite that interface.
     */
	public int compareTimestamp(Timestamp other) {

		if (other == null) {
			return 1;
		}

	    Date myDate = getDateParsed();
	    Date otherDate = ((DefaultTimestamp)other).getDateParsed(); //fishy

	    int date_compare = myDate.compareTo(otherDate);
	    if (date_compare > 0) {
	        return 1;
	    }
	    else if (date_compare < 0) {
	        return -1;
	    }
	    else {
	        int mySeq = getSequence();
	        int otherSeq = other.getSequence();

	        if (mySeq > otherSeq) {
	            return 1;
	        }
	        else if (mySeq < otherSeq) {
	            return -1;
	        } else {
				return 0;
			}
	    }
	}
}
