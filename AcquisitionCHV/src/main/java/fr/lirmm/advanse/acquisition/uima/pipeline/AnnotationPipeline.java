package fr.lirmm.advanse.acquisition.uima.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.pipeline.SimplePipeline;

import edu.stanford.nlp.parser.lexparser.CNFTransformers;
import fr.lirmm.advanse.acquisition.uima.annotator.BioEntityAnnotator;
import fr.lirmm.advanse.acquisition.uima.annotator.ContextTermAnnotator;
import fr.lirmm.advanse.acquisition.uima.consumer.ContextComputer;
import fr.lirmm.advanse.acquisition.uima.reader.TreetaggerCollectionReader;
import fr.lirmm.advanse.acquisition.uima.writer.CasToHtmlWriter;
import fr.lirmm.advanse.acquisition.uima.writer.CasToHtmlWriter_BioEntity;



public class AnnotationPipeline {
	public static void main(String[] args) throws UIMAException, IOException {

		// Run in UIMA pipeline
		TreetaggerCollectionReader reader = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
				TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, "src/main/resources/corpus");
		AnalysisEngine writer = createEngine(CasToHtmlWriter.class);

		// Build model : training
		SimplePipeline.runPipeline(reader, writer);
		
		/****************/
		
		TreetaggerCollectionReader reader_BioEntity = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
				TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, "src/main/resources/corpus");
		AnalysisEngine annotator_BioEntity = createEngine(BioEntityAnnotator.class);
		AnalysisEngine writer_BioEntity = createEngine(CasToHtmlWriter_BioEntity.class);

		// Build model : training
		SimplePipeline.runPipeline(reader_BioEntity, annotator_BioEntity, writer_BioEntity);
		
		/****************/
		
		TreetaggerCollectionReader reader_ContextTerm = (TreetaggerCollectionReader) createReader(TreetaggerCollectionReader.class,
				TreetaggerCollectionReader.PARAM_DIRECTORY_NAME, "src/main/resources/corpus");

		// Build model : training
		SimplePipeline.runPipeline(reader_ContextTerm, 
				createEngine(BioEntityAnnotator.class),
				createEngine(ContextTermAnnotator.class),
				createEngine(ContextComputer.class),
				createEngine(CasToHtmlWriter_BioEntity.class));
		
	}
}

