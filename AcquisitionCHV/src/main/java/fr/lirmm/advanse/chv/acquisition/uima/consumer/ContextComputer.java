package fr.lirmm.advanse.chv.acquisition.uima.consumer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.json.simple.JSONObject;

import fr.lirmm.advanse.chv.acquisition.type.BioEntity;
import fr.lirmm.advanse.chv.acquisition.type.ContextTerm;
import fr.lirmm.advanse.chv.acquisition.type.Post;
import fr.lirmm.advanse.chv.acquisition.uima.annotator.BioEntityAnnotator;
import fr.lirmm.advanse.chv.acquisition.uima.model.impl.ContextVector;

public class ContextComputer extends JCasConsumer_ImplBase {

	Logger logger = UIMAFramework.getLogger(ContextComputer.class);
	public static final String LF = System.getProperty("line.separator");

	/**
	 * Second order context vectors for each BioEntity (lay and expert)
	 */
	private HashMap<String, ContextVector> contextVectors;
	
	public static final String PARAM_WINDOW_SIZE = "WindowSize";
	@ConfigurationParameter(name = PARAM_WINDOW_SIZE, description = "The size of the sliding window", mandatory = false, defaultValue="2")
	private int WINDOW_SIZE;
	
	public static final String PARAM_WORKSPACE_NAME = "WorkspaceName";
	@ConfigurationParameter(name = PARAM_WORKSPACE_NAME, description = "The name of the directory corresponding to the nodeScope", mandatory = true)
	private File nodeWorkspace;
	
	private File pat_json_output;
	private File med_json_output;
	

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
		pat_json_output = new File(nodeWorkspace.getAbsolutePath()+"/output/pat_context.json");
		med_json_output = new File(nodeWorkspace.getAbsolutePath()+"/output/med_context.json");
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
			FileUtils.write(pat_json_output, contentPat);//, "UTF-8");
			FileUtils.write(med_json_output, contentMed);//, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
