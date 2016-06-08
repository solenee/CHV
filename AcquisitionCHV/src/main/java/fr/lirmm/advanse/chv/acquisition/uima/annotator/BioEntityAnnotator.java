package fr.lirmm.advanse.chv.acquisition.uima.annotator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.json.simple.JSONObject;

import fr.lirmm.advanse.chv.acquisition.ReaderPOC;
import fr.lirmm.advanse.chv.acquisition.type.BioEntity;
import fr.lirmm.advanse.chv.acquisition.type.MedEntity;
import fr.lirmm.advanse.chv.acquisition.type.PatEntity;
import fr.lirmm.advanse.chv.acquisition.type.Post;

// TODO Print frequency of each BioEntity 
public class BioEntityAnnotator extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(BioEntityAnnotator.class);
	public static final String LF = System.getProperty("line.separator");

	public static final String PARAM_WORKSPACE_NAME = "WorkspaceName";
	@ConfigurationParameter(name = PARAM_WORKSPACE_NAME, description = "The name of the directory corresponding to the nodeScope", mandatory = true)
	private File nodeWorkspace;
	
	private File frequency_json_output;
	
	
	/**
	 * Log, for each ContextTerm, how often it appears in the context of a BioEntity
	 */
	private HashMap<String, Integer> frequency;
	
	public static List<String> PATIENT_CANDIDATES = getPatientCandidates();
//	Arrays.asList("chimio",
//			"onco", "mammo", "gynéco", "cancers", "crabe");
	public static List<String> MEDECIN_TARGETS = getMedecinTargets();
//	Arrays.asList("cancer du sein",
//			"cancer", "oncologue", "oncologie", "mammographie", "gynécologue",
//			"tumeur", "chirurgie", "ganglion");

	public synchronized static List<String> getPatientCandidates() {
		TreeSet<String> bag = new TreeSet<String>();
		try {
		BufferedReader b = new BufferedReader(new FileReader("src/main/resources/concepts/candidates.txt"));
		String line = "";
		while ((line = b.readLine()) != null) {
			if (! line.equals("")) {
					bag.add(line.trim().toLowerCase());
				}
			}
		b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>(bag);
	}
	
	public synchronized static List<String> getMedecinTargets() {
		TreeSet<String> bag = new TreeSet<String>();
		try {
		BufferedReader b = new BufferedReader(new FileReader("src/main/resources/concepts/reference_samples.txt"));
		String line = "";
		while ((line = b.readLine()) != null) {
			if (! line.equals("")) {
					bag.add(line.trim().toLowerCase());
				}
			}
		b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>(bag);
	}
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		frequency = new HashMap<String, Integer>();
		frequency_json_output = new File(nodeWorkspace.getAbsolutePath()+"/output/frequency_lexicon.json");
	}
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		logger.log(Level.FINE, "process");
		String text = jcas.getDocumentText();

		Scanner txtscan = new Scanner(text);
		int beginPost = 0;
		int endPost = beginPost;
		while (txtscan.hasNextLine()) {
			String str = txtscan.nextLine();
			boolean containsEntity = false;

			// 1- Annotate patient BioEntity
			String searchIn = str;
			for (String medTerm : MEDECIN_TARGETS) {
				int fromIndex = 0;
				int index = -1;
				searchIn = str;
				while ( (index = searchIn.indexOf(medTerm, fromIndex)) != -1 ) {
					String securityString = null;
					if (index > 0) {
						securityString = searchIn.substring(index-1,
								index + medTerm.length() + 1);
					} else {
						securityString = searchIn.substring(index,
								index + medTerm.length() + 1);
					}
					if (ReaderPOC.exactMatch(medTerm,
							securityString)) {
						containsEntity = true;
						logger.log(Level.INFO, medTerm
								+ " EXISTS : #"
								+ securityString +"#");
						BioEntity e = new MedEntity(jcas);
						e.setNormalizedForm(medTerm);
						e.setBegin(beginPost + index);
						e.setEnd(beginPost + index +  medTerm.length());
						e.addToIndexes();
						incFrequency(e.getNormalizedForm());
					}
					// iterate
					fromIndex = index + medTerm.length();
				}
			}
			for (String patTerm : PATIENT_CANDIDATES) {
				int fromIndex = 0;
				int index = -1;
				searchIn = str;
				while ( (index = searchIn.indexOf(patTerm, fromIndex)) != -1 ) {
					String securityString = null;
					if (index > 0) {
						securityString = searchIn.substring(index-1,
								index + patTerm.length() + 1);
					} else {
						securityString = searchIn.substring(index,
								index + patTerm.length() + 1);
					}
					if (ReaderPOC.exactMatch(patTerm,
							securityString)) {
						containsEntity = true;
						logger.log(Level.INFO, patTerm
								+ " EXISTS : #"
								+ securityString +"#");
						BioEntity e = new PatEntity(jcas);
						e.setNormalizedForm(patTerm);
						e.setBegin(beginPost + index);
						e.setEnd(beginPost + index +  patTerm.length());
						e.addToIndexes();
						incFrequency(e.getNormalizedForm());
					}
					// iterate
					fromIndex = index + patTerm.length();
				}
			}
			
			if (containsEntity) {
				// 2 - Annotate post
				Post post = new Post(jcas);
				post.setBegin(beginPost);
				post.setEnd(beginPost + str.length());
				post.addToIndexes();
				endPost = post.getEnd();
				beginPost = endPost + 1; // length("\n") ==1	
			} else {
				// Iterate
				endPost = beginPost + str.length();
				beginPost = endPost + 1; // length("\n") ==1
			}
		}
		txtscan.close();

	}
	
	private synchronized void incFrequency(String word) {
		Integer v = frequency.get(word);
		if (v == null) {
			frequency.put(word, 1);
		} else {
			frequency.put(word, v + 1);
		}
	}
	
	private void writeFrequency(File outputFile) throws IOException {
		JSONObject obj = new JSONObject();
		for (String item : frequency.keySet()) {
			obj.put(item, frequency.get(item));
		}
		FileUtils.write(outputFile,  obj.toString());//, "UTF-8");
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		try {
			writeFrequency(frequency_json_output);
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
}
