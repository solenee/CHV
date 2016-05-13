package fr.lirmm.advanse.acquisition.uima.annotator;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import fr.lirmm.advanse.acquisition.ReaderPOC;
import fr.lirmm.advanse.acquisition.type.BioEntity;
import fr.lirmm.advanse.acquisition.type.MedEntity;
import fr.lirmm.advanse.acquisition.type.PatEntity;
import fr.lirmm.advanse.acquisition.type.Post;


public class BioEntityAnnotator extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(BioEntityAnnotator.class);
	public static final String LF = System.getProperty("line.separator");

	public static List<String> PATIENT_CANDIDATES = Arrays.asList("chimio",
			"onco", "mammo", "gynéco", "cancers", "crabe");
	public static List<String> MEDECIN_TARGETS = Arrays.asList("cancer du sein",
			"cancer", "oncologue", "oncologie", "mammographie", "gynécologue",
			"tumeur", "chirurgie", "ganglion");

	
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
			String txtHtml = str;

			String initialText = str;
			String searchIn = str;
			for (String medTerm : MEDECIN_TARGETS) {
				int fromIndex = 0;
				int index = -1;
				searchIn = str;
				while ( (index = searchIn.indexOf(medTerm, fromIndex)) != -1 ) {
					if (ReaderPOC.exactMatch(medTerm,
							searchIn.substring(index,
									index + medTerm.length() + 1))) {
						containsEntity = true;
						logger.log(Level.INFO, medTerm
								+ " EXISTS : "
								+ searchIn.substring(index, 
										index + medTerm.length() + 1));
						BioEntity e = new MedEntity(jcas);
						e.setNormalizedForm(medTerm);
						e.setBegin(beginPost + index);
						e.setEnd(beginPost + index +  medTerm.length());
						e.addToIndexes();
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
					if (ReaderPOC.exactMatch(patTerm,
							searchIn.substring(index,
									index + patTerm.length() + 1))) {
						containsEntity = true;
						logger.log(Level.INFO, patTerm
								+ " EXISTS : "
								+ searchIn.substring(index, 
										index + patTerm.length() + 1));
						BioEntity e = new PatEntity(jcas);
						e.setNormalizedForm(patTerm);
						e.setBegin(beginPost + index);
						e.setEnd(beginPost + index +  patTerm.length());
						e.addToIndexes();
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

}
