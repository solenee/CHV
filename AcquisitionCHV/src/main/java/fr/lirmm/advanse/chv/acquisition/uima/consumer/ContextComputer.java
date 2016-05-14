package fr.lirmm.advanse.chv.acquisition.uima.consumer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import fr.lirmm.advanse.chv.acquisition.type.BioEntity;
import fr.lirmm.advanse.chv.acquisition.type.ContextTerm;
import fr.lirmm.advanse.chv.acquisition.type.Post;
import fr.lirmm.advanse.chv.acquisition.uima.annotator.BioEntityAnnotator;
import fr.lirmm.advanse.chv.acquisition.uima.model.impl.ContextVector;

public class ContextComputer extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(ContextComputer.class);
	public static final String LF = System.getProperty("line.separator");

	private HashMap<String, ContextVector> contextVectors;

	private int WINDOW_SIZE = 2;
	
	public static File PAT_JSON_OUTPUT = new File("pat_context.json");
	public static File MED_JSON_OUTPUT = new File("med_context.json");

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		contextVectors = new HashMap<String, ContextVector>();
		for (String term : BioEntityAnnotator.PATIENT_CANDIDATES) {
			contextVectors.put(term, new ContextVector());
		}
		for (String term : BioEntityAnnotator.MEDECIN_TARGETS) {
			contextVectors.put(term, new ContextVector());
		}
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		logger.log(Level.FINE, "process");
		for (Post message :  JCasUtil.select(jcas, Post.class) ) {
			for (BioEntity bioEntity : JCasUtil.selectCovered(jcas, BioEntity.class, message)) {
				int borderInf = message.getBegin();
				int borderSup = message.getEnd();
				// Compute context
				ContextVector contextV = contextVectors.get(bioEntity.getNormalizedForm());
				for (ContextTerm t : JCasUtil.selectPreceding(ContextTerm.class,
						bioEntity, WINDOW_SIZE)) {
					if (t.getBegin() >= borderInf) {
						contextV.inc(t.getNormalizedForm());
					}
				}
				for (ContextTerm t : JCasUtil.selectFollowing(ContextTerm.class,
						bioEntity, WINDOW_SIZE)) {
					if (t.getEnd() <= borderSup) {
						contextV.inc(t.getNormalizedForm());
					}
				}
			}
		}
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		StringBuffer sbPat = new StringBuffer();
		StringBuffer sbMed = new StringBuffer();
		sbPat.append("{ ");
		sbMed.append("{ ");
		for (String item : contextVectors.keySet()) {
			if (BioEntityAnnotator.PATIENT_CANDIDATES.contains(item)) {
				sbPat.append("\n"+"\""+item+"\""+" : "+contextVectors.get(item).toJSon()+" ,");
			} else if (BioEntityAnnotator.MEDECIN_TARGETS.contains(item)) {
				sbMed.append("\n"+"\""+item+"\""+" : "+contextVectors.get(item).toJSon()+" ,");
			} else {
				logger.log(Level.WARNING, item+" does not belong to any biomedical list (Pat/Med)");
			}
		}
		// Remove ","
		String contentPat = sbPat.substring(0, sbPat.length()-1)+"\n}";
		String contentMed = sbMed.substring(0, sbMed.length()-1)+"\n}";
		try {
			FileUtils.write(PAT_JSON_OUTPUT, contentPat);//, "UTF-8");
			FileUtils.write(MED_JSON_OUTPUT, contentMed);//, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
