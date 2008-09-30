package edu.stanford.bmir.protegex.chao.annotation.api;

import java.util.ArrayList;
import java.util.Collection;

import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultAdvice;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultAgreeDisagreeVote;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultAgreeDisagreeVoteProposal;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultComment;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultExample;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultExplanation;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultFiveStarsVote;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultFiveStarsVoteProposal;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultQuestion;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultSeeAlso;
import edu.stanford.bmir.protegex.chao.annotation.api.impl.DefaultSimpleProposal;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultTimestamp;
import edu.stanford.smi.protege.code.generator.wrapping.AbstractWrappedInstance;
import edu.stanford.smi.protege.code.generator.wrapping.OntologyJavaMappingUtil;
import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;

/**
 * Generated by Protege (http://protege.stanford.edu).
 *
 * @version generated on Mon Aug 18 21:11:09 GMT-08:00 2008
 */
public class AnnotationFactory {
    static { OntologyJavaMapping.initMap(); }

    private KnowledgeBase kb;

    public AnnotationFactory(KnowledgeBase kb) {
        this.kb = kb;
    }


    // ***** Class Advice *****

    public Cls getAdviceClass() {
        final String name = "Advice";
        return kb.getCls(name);
    }

    public Advice createAdvice(String name) {
        Cls cls = getAdviceClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultAdvice(inst);
    }

    public Advice getAdvice(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Advice.class);
    }

    public Collection<Advice> getAllAdviceObjects() {
        return getAllAdviceObjects(false);
    }

    public Collection<Advice> getAllAdviceObjects(boolean transitive) {
        Collection<Advice> result = new ArrayList<Advice>();
        final Cls cls = getAdviceClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Advice.class));
        }
        return result;
    }


    // ***** Class AgreeDisagreeVote *****

    public Cls getAgreeDisagreeVoteClass() {
        final String name = "AgreeDisagreeVote";
        return kb.getCls(name);
    }

    public AgreeDisagreeVote createAgreeDisagreeVote(String name) {
        Cls cls = getAgreeDisagreeVoteClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultAgreeDisagreeVote(inst);
    }

    public AgreeDisagreeVote getAgreeDisagreeVote(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), AgreeDisagreeVote.class);
    }

    public Collection<AgreeDisagreeVote> getAllAgreeDisagreeVoteObjects() {
        return getAllAgreeDisagreeVoteObjects(false);
    }

    public Collection<AgreeDisagreeVote> getAllAgreeDisagreeVoteObjects(boolean transitive) {
        Collection<AgreeDisagreeVote> result = new ArrayList<AgreeDisagreeVote>();
        final Cls cls = getAgreeDisagreeVoteClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, AgreeDisagreeVote.class));
        }
        return result;
    }


    // ***** Class AgreeDisagreeVoteProposal *****

    public Cls getAgreeDisagreeVoteProposalClass() {
        final String name = "AgreeDisagreeVoteProposal";
        return kb.getCls(name);
    }

    public AgreeDisagreeVoteProposal createAgreeDisagreeVoteProposal(String name) {
        Cls cls = getAgreeDisagreeVoteProposalClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultAgreeDisagreeVoteProposal(inst);
    }

    public AgreeDisagreeVoteProposal getAgreeDisagreeVoteProposal(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), AgreeDisagreeVoteProposal.class);
    }

    public Collection<AgreeDisagreeVoteProposal> getAllAgreeDisagreeVoteProposalObjects() {
        return getAllAgreeDisagreeVoteProposalObjects(false);
    }

    public Collection<AgreeDisagreeVoteProposal> getAllAgreeDisagreeVoteProposalObjects(boolean transitive) {
        Collection<AgreeDisagreeVoteProposal> result = new ArrayList<AgreeDisagreeVoteProposal>();
        final Cls cls = getAgreeDisagreeVoteProposalClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, AgreeDisagreeVoteProposal.class));
        }
        return result;
    }


    // ***** Class AnnotatableThing *****

    public Cls getAnnotatableThingClass() {
        final String name = "AnnotatableThing";
        return kb.getCls(name);
    }

    public AnnotatableThing getAnnotatableThing(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), AnnotatableThing.class);
    }

    public Collection<AnnotatableThing> getAllAnnotatableThingObjects() {
        return getAllAnnotatableThingObjects(false);
    }

    public Collection<AnnotatableThing> getAllAnnotatableThingObjects(boolean transitive) {
        Collection<AnnotatableThing> result = new ArrayList<AnnotatableThing>();
        final Cls cls = getAnnotatableThingClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, AnnotatableThing.class));
        }
        return result;
    }


    // ***** Class Annotation *****

    public Cls getAnnotationClass() {
        final String name = "Annotation";
        return kb.getCls(name);
    }

    public Annotation getAnnotation(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Annotation.class);
    }

    public Collection<Annotation> getAllAnnotationObjects() {
        return getAllAnnotationObjects(false);
    }

    public Collection<Annotation> getAllAnnotationObjects(boolean transitive) {
        Collection<Annotation> result = new ArrayList<Annotation>();
        final Cls cls = getAnnotationClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Annotation.class));
        }
        return result;
    }


    // ***** Class Comment *****

    public Cls getCommentClass() {
        final String name = "Comment";
        return kb.getCls(name);
    }

    public Comment createComment(String name) {
        Cls cls = getCommentClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultComment(inst);
    }

    public Comment getComment(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Comment.class);
    }

    public Collection<Comment> getAllCommentObjects() {
        return getAllCommentObjects(false);
    }

    public Collection<Comment> getAllCommentObjects(boolean transitive) {
        Collection<Comment> result = new ArrayList<Comment>();
        final Cls cls = getCommentClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Comment.class));
        }
        return result;
    }


    // ***** Class Example *****

    public Cls getExampleClass() {
        final String name = "Example";
        return kb.getCls(name);
    }

    public Example createExample(String name) {
        Cls cls = getExampleClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultExample(inst);
    }

    public Example getExample(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Example.class);
    }

    public Collection<Example> getAllExampleObjects() {
        return getAllExampleObjects(false);
    }

    public Collection<Example> getAllExampleObjects(boolean transitive) {
        Collection<Example> result = new ArrayList<Example>();
        final Cls cls = getExampleClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Example.class));
        }
        return result;
    }


    // ***** Class Explanation *****

    public Cls getExplanationClass() {
        final String name = "Explanation";
        return kb.getCls(name);
    }

    public Explanation createExplanation(String name) {
        Cls cls = getExplanationClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultExplanation(inst);
    }

    public Explanation getExplanation(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Explanation.class);
    }

    public Collection<Explanation> getAllExplanationObjects() {
        return getAllExplanationObjects(false);
    }

    public Collection<Explanation> getAllExplanationObjects(boolean transitive) {
        Collection<Explanation> result = new ArrayList<Explanation>();
        final Cls cls = getExplanationClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Explanation.class));
        }
        return result;
    }


    // ***** Class FiveStarsVote *****

    public Cls getFiveStarsVoteClass() {
        final String name = "FiveStarsVote";
        return kb.getCls(name);
    }

    public FiveStarsVote createFiveStarsVote(String name) {
        Cls cls = getFiveStarsVoteClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultFiveStarsVote(inst);
    }

    public FiveStarsVote getFiveStarsVote(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), FiveStarsVote.class);
    }

    public Collection<FiveStarsVote> getAllFiveStarsVoteObjects() {
        return getAllFiveStarsVoteObjects(false);
    }

    public Collection<FiveStarsVote> getAllFiveStarsVoteObjects(boolean transitive) {
        Collection<FiveStarsVote> result = new ArrayList<FiveStarsVote>();
        final Cls cls = getFiveStarsVoteClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, FiveStarsVote.class));
        }
        return result;
    }


    // ***** Class FiveStarsVoteProposal *****

    public Cls getFiveStarsVoteProposalClass() {
        final String name = "FiveStarsVoteProposal";
        return kb.getCls(name);
    }

    public FiveStarsVoteProposal createFiveStarsVoteProposal(String name) {
        Cls cls = getFiveStarsVoteProposalClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultFiveStarsVoteProposal(inst);
    }

    public FiveStarsVoteProposal getFiveStarsVoteProposal(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), FiveStarsVoteProposal.class);
    }

    public Collection<FiveStarsVoteProposal> getAllFiveStarsVoteProposalObjects() {
        return getAllFiveStarsVoteProposalObjects(false);
    }

    public Collection<FiveStarsVoteProposal> getAllFiveStarsVoteProposalObjects(boolean transitive) {
        Collection<FiveStarsVoteProposal> result = new ArrayList<FiveStarsVoteProposal>();
        final Cls cls = getFiveStarsVoteProposalClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, FiveStarsVoteProposal.class));
        }
        return result;
    }


    // ***** Class Proposal *****

    public Cls getProposalClass() {
        final String name = "Proposal";
        return kb.getCls(name);
    }

    public Proposal getProposal(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Proposal.class);
    }

    public Collection<Proposal> getAllProposalObjects() {
        return getAllProposalObjects(false);
    }

    public Collection<Proposal> getAllProposalObjects(boolean transitive) {
        Collection<Proposal> result = new ArrayList<Proposal>();
        final Cls cls = getProposalClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Proposal.class));
        }
        return result;
    }


    // ***** Class Question *****

    public Cls getQuestionClass() {
        final String name = "Question";
        return kb.getCls(name);
    }

    public Question createQuestion(String name) {
        Cls cls = getQuestionClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultQuestion(inst);
    }

    public Question getQuestion(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Question.class);
    }

    public Collection<Question> getAllQuestionObjects() {
        return getAllQuestionObjects(false);
    }

    public Collection<Question> getAllQuestionObjects(boolean transitive) {
        Collection<Question> result = new ArrayList<Question>();
        final Cls cls = getQuestionClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Question.class));
        }
        return result;
    }


    // ***** Class SeeAlso *****

    public Cls getSeeAlsoClass() {
        final String name = "SeeAlso";
        return kb.getCls(name);
    }

    public SeeAlso createSeeAlso(String name) {
        Cls cls = getSeeAlsoClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultSeeAlso(inst);
    }

    public SeeAlso getSeeAlso(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), SeeAlso.class);
    }

    public Collection<SeeAlso> getAllSeeAlsoObjects() {
        return getAllSeeAlsoObjects(false);
    }

    public Collection<SeeAlso> getAllSeeAlsoObjects(boolean transitive) {
        Collection<SeeAlso> result = new ArrayList<SeeAlso>();
        final Cls cls = getSeeAlsoClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, SeeAlso.class));
        }
        return result;
    }


    // ***** Class SimpleProposal *****

    public Cls getSimpleProposalClass() {
        final String name = "SimpleProposal";
        return kb.getCls(name);
    }

    public SimpleProposal createSimpleProposal(String name) {
        Cls cls = getSimpleProposalClass();
        Instance inst = cls.createDirectInstance(name);
        return new DefaultSimpleProposal(inst);
    }

    public SimpleProposal getSimpleProposal(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), SimpleProposal.class);
    }

    public Collection<SimpleProposal> getAllSimpleProposalObjects() {
        return getAllSimpleProposalObjects(false);
    }

    public Collection<SimpleProposal> getAllSimpleProposalObjects(boolean transitive) {
        Collection<SimpleProposal> result = new ArrayList<SimpleProposal>();
        final Cls cls = getSimpleProposalClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, SimpleProposal.class));
        }
        return result;
    }


    // ***** Class Vote *****

    public Cls getVoteClass() {
        final String name = "Vote";
        return kb.getCls(name);
    }

    public Vote getVote(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), Vote.class);
    }

    public Collection<Vote> getAllVoteObjects() {
        return getAllVoteObjects(false);
    }

    public Collection<Vote> getAllVoteObjects(boolean transitive) {
        Collection<Vote> result = new ArrayList<Vote>();
        final Cls cls = getVoteClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, Vote.class));
        }
        return result;
    }


    // ***** Class VotingProposal *****

    public Cls getVotingProposalClass() {
        final String name = "VotingProposal";
        return kb.getCls(name);
    }

    public VotingProposal getVotingProposal(String name) {
        return OntologyJavaMappingUtil.getSpecificObject(kb, kb.getInstance(name), VotingProposal.class);
    }

    public Collection<VotingProposal> getAllVotingProposalObjects() {
        return getAllVotingProposalObjects(false);
    }

    public Collection<VotingProposal> getAllVotingProposalObjects(boolean transitive) {
        Collection<VotingProposal> result = new ArrayList<VotingProposal>();
        final Cls cls = getVotingProposalClass();
        for (Object element : transitive ? cls.getInstances() : cls.getDirectInstances()) {
            Instance inst = (Instance) element;
            result.add(OntologyJavaMappingUtil.getSpecificObject(kb, inst, VotingProposal.class));
        }
        return result;
    }


    // ***** Getter methods for slots *****

    public Slot getActionSlot() {
        final String name = "action";
        return kb.getSlot(name);
    }

    public Slot getAnnotatesSlot() {
        final String name = "annotates";
        return kb.getSlot(name);
    }

    public Slot getAssociatedAnnotationsSlot() {
        final String name = "associatedAnnotations";
        return kb.getSlot(name);
    }

    public Slot getApplyToSlot() {
        final String name = "applyTo";
        return kb.getSlot(name);
    }

    public Slot getChangesSlot() {
        final String name = "changes";
        return kb.getSlot(name);
    }

    public Slot getAssociatedPropertySlot() {
        final String name = "associatedProperty";
        return kb.getSlot(name);
    }

    public Slot getAuthorSlot() {
        final String name = "author";
        return kb.getSlot(name);
    }

    public Slot getBodySlot() {
        final String name = "body";
        return kb.getSlot(name);
    }

    public Slot getChanges_Slot_0Slot() {
        final String name = "changes_Slot_0";
        return kb.getSlot(name);
    }

    public Slot getChanges_Slot_1Slot() {
        final String name = "changes_Slot_1";
        return kb.getSlot(name);
    }

    public Slot getCommentSlot() {
        final String name = "comment";
        return kb.getSlot(name);
    }

    public Slot getContextSlot() {
        final String name = "context";
        return kb.getSlot(name);
    }

    public Slot getCreatedSlot() {
        final String name = "created";
        return kb.getSlot(name);
    }

    public Slot getCreationNameSlot() {
        final String name = "creationName";
        return kb.getSlot(name);
    }

    public Slot getCurrentNameSlot() {
        final String name = "currentName";
        return kb.getSlot(name);
    }

    public Slot getDateSlot() {
        final String name = "date";
        return kb.getSlot(name);
    }

    public Slot getDeletionNameSlot() {
        final String name = "deletionName";
        return kb.getSlot(name);
    }

    public Slot getInverse_of_annotatesSlot() {
        final String name = "inverse_of_annotates";
        return kb.getSlot(name);
    }

    public Slot getModifiedSlot() {
        final String name = "modified";
        return kb.getSlot(name);
    }

    public Slot getNameSlot() {
        final String name = "name";
        return kb.getSlot(name);
    }

    public Slot getNewNameSlot() {
        final String name = "newName";
        return kb.getSlot(name);
    }

    public Slot getOldNameSlot() {
        final String name = "oldName";
        return kb.getSlot(name);
    }

    public Slot getPartOfCompositeChangeSlot() {
        final String name = "partOfCompositeChange";
        return kb.getSlot(name);
    }

    public Slot getSubChangesSlot() {
        final String name = "subChanges";
        return kb.getSlot(name);
    }

    public Slot getRelatedSlot() {
        final String name = "related";
        return kb.getSlot(name);
    }

    public Slot getSequenceSlot() {
        final String name = "sequence";
        return kb.getSlot(name);
    }

    public Slot getSubjectSlot() {
        final String name = "subject";
        return kb.getSlot(name);
    }

    public Slot getTimestampSlot() {
        final String name = "timestamp";
        return kb.getSlot(name);
    }

    public Slot getTitleSlot() {
        final String name = "title";
        return kb.getSlot(name);
    }

    public Slot getVoteValueSlot() {
        final String name = "voteValue";
        return kb.getSlot(name);
    }

    /********* Utility methods ************/

    public Annotation fillDefaultValues(Annotation annotation) {
    	annotation.setCreated(DefaultTimestamp.getTimestamp(kb));
    	annotation.setAuthor(kb.getUserName());
    	return annotation;
    }

    public String getProtegeName(Annotation annotation) {
    	if (annotation instanceof AbstractWrappedInstance) {
    		return ((AbstractWrappedInstance) annotation).getName();
    	}
    	return null;
    }
    
    public Instance getWrappedProtegeInstance(AnnotatableThing thing) {
    	if (thing instanceof AbstractWrappedInstance) {
    		return ((AbstractWrappedInstance) thing).getWrappedProtegeInstance();
    	}
    	return null;
    }
    
    public String getAnnotationType(Annotation annotation) {
    	Instance protegeInst = getWrappedProtegeInstance(annotation);
    	return protegeInst == null ? null : protegeInst.getDirectType().getBrowserText();
    }

}
