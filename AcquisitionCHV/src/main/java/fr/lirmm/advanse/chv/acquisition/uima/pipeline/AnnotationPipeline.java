package fr.lirmm.advanse.chv.acquisition.uima.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.pipeline.SimplePipeline;

import fr.lirmm.advanse.chv.acquisition.uima.annotator.BioEntityAnnotator;
import fr.lirmm.advanse.chv.acquisition.uima.annotator.ContextTermAnnotator;
import fr.lirmm.advanse.chv.acquisition.uima.consumer.ContextComputer;
import fr.lirmm.advanse.chv.acquisition.uima.reader.TreetaggerCollectionReader;
import fr.lirmm.advanse.chv.acquisition.uima.writer.CasToHtmlWriter;
import fr.lirmm.advanse.chv.acquisition.uima.writer.CasToHtmlWriter_BioEntity;



public class AnnotationPipeline {
	public static void main(String[] args) throws UIMAException, IOException {

//		String directory = "src/main/resources/poc";
		String directory = "src/main/resources/corpus";
		boolean completeTest = false;
		// Run in UIMA pipeline
		if (completeTest) {
			TreetaggerCollectionReader reader = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
					TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, directory);
			AnalysisEngine writer = createEngine(CasToHtmlWriter.class);
	
			// Build model : training
			SimplePipeline.runPipeline(reader, writer);
		
			/****************/
			
			TreetaggerCollectionReader reader_BioEntity = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
					TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, directory);
			AnalysisEngine annotator_BioEntity = createEngine(BioEntityAnnotator.class);
			AnalysisEngine writer_BioEntity = createEngine(CasToHtmlWriter_BioEntity.class);
	
			// Build model : training
			SimplePipeline.runPipeline(reader_BioEntity, annotator_BioEntity, writer_BioEntity);
		}
		/****************/
		
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

