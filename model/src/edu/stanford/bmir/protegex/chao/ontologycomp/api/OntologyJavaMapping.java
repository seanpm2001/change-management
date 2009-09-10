package edu.stanford.bmir.protegex.chao.ontologycomp.api;

import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultOntology_Class;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultOntology_Component;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultOntology_Individual;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultOntology_Property;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultReviewer;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultTimestamp;
import edu.stanford.bmir.protegex.chao.ontologycomp.api.impl.DefaultUser;
import edu.stanford.smi.protege.code.generator.wrapping.OntologyJavaMappingUtil;

/**
 * Generated by Protege (http://protege.stanford.edu).
 *
 * @version generated on Mon Aug 18 21:08:59 GMT-08:00 2008
 */
public class OntologyJavaMapping {

    public static void initMap() {
        OntologyJavaMappingUtil.add("Ontology_Class", Ontology_Class.class, DefaultOntology_Class.class);
        OntologyJavaMappingUtil.add("Ontology_Component", Ontology_Component.class, DefaultOntology_Component.class);
        OntologyJavaMappingUtil.add("Ontology_Individual", Ontology_Individual.class, DefaultOntology_Individual.class);
        OntologyJavaMappingUtil.add("Ontology_Property", Ontology_Property.class, DefaultOntology_Property.class);
        OntologyJavaMappingUtil.add("Reviewer", Reviewer.class, DefaultReviewer.class);
        OntologyJavaMappingUtil.add("Timestamp", Timestamp.class, DefaultTimestamp.class);
        OntologyJavaMappingUtil.add("User", User.class, DefaultUser.class);
    }
}
