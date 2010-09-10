package edu.stanford.smi.protegex.server_changes;

import edu.stanford.bmir.protegex.chao.ChAOKbManager;
import edu.stanford.bmir.protegex.chao.change.api.Change;
import edu.stanford.bmir.protegex.chao.util.interval.TimeIntervalCalculator;
import edu.stanford.smi.protege.exception.ProtegeException;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protege.util.ProtegeJob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

public class RetrieveChangesProtegeJob extends ProtegeJob {
    private static final long serialVersionUID = 818316286562363577L;
    private static final Logger logger = Log.getLogger(RetrieveChangesProtegeJob.class);
    private Date lastRunDate;
    private Date end;

    public RetrieveChangesProtegeJob(KnowledgeBase kb, Date lastRunDate, Date end) {
        super(kb);
        this.lastRunDate = lastRunDate;
        this.end = end;
    }

    /**
     * Retrieves the changes from the knowledge base in a thread-safe manner, eliminating the problems we've seen with 'partial' changes.
     *
     * Note that the transaction state detection does not seem to be needed; the Protege Jobs seem to be scheduled one
     * after the other, eliminating any concurrency concerns when run on the server.
     *
     * 'Partial' changes occur when querying the changes KB midway through aggregating a change. They cause a series of
     * odd, almost random-looking changes to be sent out to the client, with bogus findings like moves in the hierarchy,
     * setting of properties all returned by the call to get changes.
     *
     * @return
     * @throws ProtegeException
     */
    public Object run() throws ProtegeException {
        logger.fine("Retrieving changes on the server.");
        final KnowledgeBase changesKb = ChAOKbManager.getChAOKb(getKnowledgeBase());

        final PostProcessorManager processorManager = ChangesProject.getPostProcessorManager(getKnowledgeBase());
        final TransactionState transactionState = processorManager.getTransactionState();
        if (!transactionState.inTransaction()) {
            TimeIntervalCalculator timeIntervalCalculator = TimeIntervalCalculator.get(changesKb);
            Collection<Change> changes = timeIntervalCalculator.getTopLevelChanges(lastRunDate, end);
            if (changes == null) {
                changes = new ArrayList<Change>();
            }
            return changes;
        }
        logger.info("Transaction detected, returning control to client..");
        return null;
    }

}
