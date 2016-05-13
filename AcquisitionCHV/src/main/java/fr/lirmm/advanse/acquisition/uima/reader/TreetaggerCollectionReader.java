package fr.lirmm.advanse.acquisition.uima.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Logger;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import fr.lirmm.advanse.chv.definitionMining.uima.type.TokenTreetagger;

/**
 * Pretraitements a faire avant POS tagging : replace(")", " )"), replace("j\"", "j\'") 
 * @author soleneeholie
 *
 */
public class TreetaggerCollectionReader extends JCasCollectionReader_ImplBase {

	Logger logger = UIMAFramework.getLogger(TreetaggerCollectionReader.class);

	public static final String DOCUMENT_LANGUAGE = "DOCUMENT_LANGUAGE";
	public static String TO_STRIP = ")"; 

	// @ConfigurationParameter(name = DOCUMENT_LANGUAGE,
	// mandatory = false, defaultValue = "fr")
	private String documentLanguage = "fr";

	public static final String PARAM_DIRECTORY_NAME = "DirectoryName";
	@ConfigurationParameter(name = PARAM_DIRECTORY_NAME, description = "The name of the directory of text files to be read", mandatory = true)
	private File dir;

	/** Documents list */
	private List<File> documents;

	private int i = 0;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		documents = new ArrayList<File>(FileUtils.listFiles(dir,
				new String[] { "treetagger" }, false));
	}

	public boolean hasNext() throws IOException, CollectionException {
		return i < documents.size();
	}

	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(i, documents.size(),
				Progress.ENTITIES) };
	}

	@Override
	public void getNext(JCas jcas) throws IOException, CollectionException {
		File f = documents.get(i);
		String s = "";
		int begin = 0;
		int end = 0;

		BufferedReader b = new BufferedReader(new FileReader(f));
		String line = "";
		while ((line = b.readLine()) != null) {
			//System.out.println(line);
			if (line.contains("#END#")) {
				s += "\n"; 
				end++; // length("\n") ==1
				begin = end;				
			} else {
				TokenTreetagger token = treetaggerToToken(line, begin, jcas);
				s += token.getWord() + " ";
				//System.out.println(s.substring(begin, token.getEnd()));
				end = token.getEnd() +1;  // because of the blank space
				begin = end;
			}
		}
		b.close();

		// Set JCas text
		jcas.setDocumentText(s);
		jcas.setDocumentLanguage(documentLanguage);

		// Iterate
		i++;
	}

	private TokenTreetagger treetaggerToToken(String text, int begin, JCas jcas) {
		String items[] = text.split("\t");
		TokenTreetagger t = new TokenTreetagger(jcas);
		t.setWord(items[0].toLowerCase());
		t.setPos(items[1].split(":")[0]);
		t.setLemma(items[2].toLowerCase());
		t.setBegin(begin);
		t.setEnd(begin + t.getWord().length());
		t.addToIndexes();
		return t;
	}

}
