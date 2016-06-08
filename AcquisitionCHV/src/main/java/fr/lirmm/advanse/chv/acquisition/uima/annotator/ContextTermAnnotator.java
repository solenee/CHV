package fr.lirmm.advanse.chv.acquisition.uima.annotator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.json.simple.JSONObject;

import fr.lirmm.advanse.chv.acquisition.type.ContextTerm;
import fr.lirmm.advanse.chv.acquisition.uima.model.api.IStopwords;
import fr.lirmm.advanse.chv.acquisition.uima.model.impl.Stopwords;
import fr.lirmm.advanse.chv.definitionMining.uima.type.TokenTreetagger;
import fr.lirmm.advanse.treetagger.TreetaggerConstants;

/**
 * Escape once ' and twice "
 * At the end of the processing, hapaxes should be removed to reduce noise  
 * @author soleneeholie
 *
 */
//TODO Print frequency of each ContexTerm
public class ContextTermAnnotator extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(ContextTermAnnotator.class);
	public static final String LF = System.getProperty("line.separator");

	public static final String PARAM_WORKSPACE_NAME = "WorkspaceName";
	@ConfigurationParameter(name = PARAM_WORKSPACE_NAME, description = "The name of the directory corresponding to the nodeScope", mandatory = true)
	private File nodeWorkspace;
	
	private File frequency_json_output;
	
	/**
	 * Log, for each ContextTerm, how often it appears in the context of a BioEntity
	 */
	private HashMap<String, Integer> frequency;
	
	public static List<String> PATIENT_CANDIDATES = Arrays.asList("chimio",
			"onco", "mammo", "gynéco", "cancers", "crabe");
	public static List<String> MEDECIN_TARGETS = Arrays.asList("cancer du sein",
			"cancer", "oncologue", "oncologie", "mammographie", "gynécologue",
			"tumeur", "chirurgie", "ganglion");
	
	public File stopwordsFile = IStopwords.DEFAULT_FILE;
	private IStopwords stopwords;
	public boolean discardAccents = true;
	public static List<String> DISCARDED_POS = Arrays.asList("PUN", "SENT", "SYM");
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		frequency = new HashMap<String, Integer>();
		try {
			stopwords = new Stopwords(stopwordsFile, discardAccents);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResourceInitializationException();
		}
		frequency_json_output = new File(nodeWorkspace.getAbsolutePath()+"/output/frequency_contextTerms.json");
	}
	
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		logger.log(Level.FINE, "process");
		String contextTerms = "";
		for (TokenTreetagger token : JCasUtil.select(jcas, TokenTreetagger.class)) {
			boolean discard = (DISCARDED_POS.contains(token.getPos())
					|| token.getCoveredText().length() < IStopwords.MIN_LENGTH_ADMITTED)
					|| (token.getLemma().length() < IStopwords.MIN_LENGTH_ADMITTED)
					|| stopwords.isStopword(token.getCoveredText())
					|| stopwords.isStopword(token.getLemma());
			if(! discard) {
				contextTerms += token.getWord()+","+token.getCoveredText()+","+token.getPos()+","+token.getLemma()+"\n";
				ContextTerm term = new ContextTerm(jcas);
				term.setBegin(token.getBegin());
				term.setEnd(token.getEnd());
				if (token.getLemma().equals(TreetaggerConstants.UNKNOWN_LEMMA)) {
					if (discardAccents) {
						term.setNormalizedForm(Stopwords.normalizeDiacritic(token.getWord()));
					} else {
						term.setNormalizedForm(token.getWord());
					}
				} else {
					if (discardAccents) {
						term.setNormalizedForm(Stopwords.normalizeDiacritic(token.getLemma()));
					} else {
						term.setNormalizedForm(token.getLemma());
					}
				}
				term.addToIndexes();
				incFrequency(term.getNormalizedForm());
			}
		}
		try {
			FileUtils.write(new File(nodeWorkspace.getAbsolutePath()+"/output/contextTerm.txt"), contextTerms);//, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

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


}
