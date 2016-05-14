package fr.lirmm.advanse.chv.acquisition.uima.annotator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
public class ContextTermAnnotator extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(ContextTermAnnotator.class);
	public static final String LF = System.getProperty("line.separator");

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
		try {
			stopwords = new Stopwords(stopwordsFile, discardAccents);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResourceInitializationException();
		}
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
				term.addToIndexes();
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
			}
		}
		try {
			FileUtils.write(new File("contextTerm.txt"), contextTerms);//, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
