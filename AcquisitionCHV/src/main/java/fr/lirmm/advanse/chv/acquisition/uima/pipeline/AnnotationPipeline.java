package fr.lirmm.advanse.chv.acquisition.uima.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import fr.lirmm.advanse.chv.acquisition.uima.ConfigurationException;
import fr.lirmm.advanse.chv.acquisition.uima.annotator.BioEntityAnnotator;
import fr.lirmm.advanse.chv.acquisition.uima.annotator.ContextTermAnnotator;
import fr.lirmm.advanse.chv.acquisition.uima.consumer.ContextComputer;
import fr.lirmm.advanse.chv.acquisition.uima.reader.TreetaggerCollectionReader;
import fr.lirmm.advanse.chv.acquisition.uima.writer.CasToHtmlWriter;
import fr.lirmm.advanse.chv.acquisition.uima.writer.CasToHtmlWriter_BioEntity;


/**
 * Main class
 * @author solene eholie
 *
 */
public class AnnotationPipeline {
	
	public static void main(String[] args) throws UIMAException, IOException {
		Logger logger = UIMAFramework.getLogger(AnnotationPipeline.class);
		
		String corpus_directory;
		int window_size;
		
		/* Other parameters
		String file_candidates_terms;
		String file_sample_terms;
		String file_stopwords;
		*/
		
		try {
			if (args.length != 2) {
				throw new ConfigurationException("invalid number of arguments : " +
						args.length + " instead of 2.");
			}
			corpus_directory = args[0];
			try {
				window_size = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new ConfigurationException("Second parameter (window size) should be an integer");
			}
		  	// Run pipeline
			getContexts(corpus_directory, window_size);
		} catch (ConfigurationException e) {
			logger.log(Level.SEVERE, "Exception : " + e.getMessage());
			printIndications();
			System.exit(1);
		}
		
	}

	/** 
	 * Print the indications about how to run the application
	 */
	public static void printIndications() {
		System.out.println("\n" + "Usage :"
				+ "\n\t" + "java -jar <jar-file> nodeScopeDirectoryPath windowSize"
				+ "\n\t\t" + "nodeScopeDirectoryPath is the path of the directory where the tagged corpus is located"
				+ "\n\t\t" + "windowSize is the size of the sliding window; it must be an integer (usually 2)"
				+ "\n\t\t" + "Other input parameters"
				+ "\n\t\t" + "the file containing the candidates terms must be located at src/main/resources/concepts/candidates.txt"
				+ "\n\t\t" + "the file containing the reference terms must be located at src/main/resources/concepts/reference_samples.txt"
				+ "\n\t\t" + "the file containing the stopwords must be located at src/main/resources/stopwords/stopwords_users.txt"
				+ "\n\t\t" + "Output parameters"
				+ "\n\t\t" + "nodeScopeDirectoryPath/output/pat_context.json"
				+ "\n\t\t" + "nodeScopeDirectoryPath/output/med_context.json"
				+ "\n\t\t" + "nodeScopeDirectoryPath/output/frequency.json"
				+ "\n"
				);
	}

	public static void mainDev(String[] args) throws UIMAException, IOException {

//		String directory = "src/main/resources/poc";
		String directory = "src/main/resources/nodeScope";
		boolean completeTest = false;
		boolean annotationHtmlTest = false;
		boolean makeDA = true;
		// Run in UIMA pipeline
		if (annotationHtmlTest || completeTest) {
			TreetaggerCollectionReader reader = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
					TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, directory);
			AnalysisEngine writer = createEngine(CasToHtmlWriter.class);
	
			// Build model : training
			SimplePipeline.runPipeline(reader, writer);
		}
			
		if (completeTest) {
			/****************/
			
			TreetaggerCollectionReader reader_BioEntity = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
					TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, directory);
			AnalysisEngine annotator_BioEntity = createEngine(BioEntityAnnotator.class);
			AnalysisEngine writer_BioEntity = createEngine(CasToHtmlWriter_BioEntity.class);
	
			// Build model : training
			SimplePipeline.runPipeline(reader_BioEntity, annotator_BioEntity, writer_BioEntity);
		}
		/****************/
		
		if (makeDA) {
			TreetaggerCollectionReader reader_ContextTerm = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
					TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, directory);
	
			// Build model : training
			SimplePipeline.runPipeline(reader_ContextTerm, 
					createEngine(BioEntityAnnotator.class),
					createEngine(ContextTermAnnotator.class),
					createEngine(ContextComputer.class),
					createEngine(CasToHtmlWriter_BioEntity.class));
		}
	}
	
	/**
	 * Lauch distributional analysis on the corpora present in the directory corpus_directory
	 * @param corpus_directory
	 * @param window_size
	 * @throws UIMAException
	 * @throws IOException
	 */
	public static void getContexts(String corpus_directory, int window_size) throws UIMAException, IOException {
	  	// Run pipeline
		TreetaggerCollectionReader reader_ContextTerm = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
				TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, corpus_directory);
		SimplePipeline.runPipeline(reader_ContextTerm, 
				createEngine(BioEntityAnnotator.class,
						BioEntityAnnotator.PARAM_WORKSPACE_NAME, corpus_directory),
				createEngine(ContextTermAnnotator.class,
						ContextTermAnnotator.PARAM_WORKSPACE_NAME, corpus_directory),
				createEngine(ContextComputer.class,
						ContextComputer.PARAM_WINDOW_SIZE, window_size,
						ContextComputer.PARAM_WORKSPACE_NAME, corpus_directory),
				createEngine(CasToHtmlWriter_BioEntity.class));
		
	}
}

