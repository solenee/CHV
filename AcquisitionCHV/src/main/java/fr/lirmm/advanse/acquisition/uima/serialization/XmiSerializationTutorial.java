package fr.lirmm.advanse.acquisition.uima.serialization;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;

/**
 * This tutorial demonstrates how to serialize JCas objects easily with DKPro Core's 
 * {@link XmiWriter} and {@link XmiReader}.
 * 
 * In order for the XmiReader to know the target filename, it needs an instance of 
 * {@link DocumentMetaData} and at least its <code>documentId</code> or 
 * <code>URI/baseURI</code> property needs to be set (the URI takes precedence over 
 * the document ID). 
 * 
 * @author Roland Kluge
 */
public class XmiSerializationTutorial
{
    public static void main(final String[] args)
        throws UIMAException, IOException
    {
    	boolean deleteOldCache = false;
        final File cachingDirectory = new File("./target/cached_cases/");
        // Delete old cached documents
        if (deleteOldCache && cachingDirectory.exists()) {
        	FileUtils.deleteDirectory(cachingDirectory);
        }
        cachingDirectory.mkdir();

        if (cachingDirectory.listFiles().length == 0) {
            UIMAFramework.getLogger().log(Level.INFO, "No cached CASES found.");
            final JCas jCas = JCasFactory.createJCas();

            // This is needed - set BaseURI or documentId property
            final DocumentMetaData metaData = new DocumentMetaData(jCas);

            metaData.setDocumentId("document1.txt");

            /*
             * If the code below were uncommented, the URI property would take 
             * precedence over the document ID and the writer would create a 
             * subdirectory 'docs' in the caching directory and put the serialized 
             * CAS there.
             */
            // metaData.setDocumentUri("file:///tmp/docs/document1.txt");
            // metaData.setDocumentBaseUri("file:///tmp/");

            metaData.addToIndexes();
            final AnalysisEngineDescription xmiWriter = AnalysisEngineFactory
                    .createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION,
                            cachingDirectory);
            SimplePipeline.runPipeline(jCas, xmiWriter);

            UIMAFramework.getLogger().log(Level.INFO,
                    "Files in caching directory: " + Arrays.toString(cachingDirectory.listFiles()));
        }
        else {
            UIMAFramework.getLogger().log(Level.INFO, "Loading cached CASes...");
            final CollectionReaderDescription xmiReader = CollectionReaderFactory
                    .createReaderDescription(XmiReader.class, XmiReader.PARAM_SOURCE_LOCATION,
                            cachingDirectory.getAbsolutePath(), XmiReader.PARAM_PATTERNS,
                            "[+]*.xmi");
            SimplePipeline.runPipeline(xmiReader,
                    AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class));
        }
    }

}
