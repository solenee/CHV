package fr.lirmm.advanse.acquisition.uima.writer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import fr.lirmm.advanse.acquisition.ReaderPOC;
import fr.lirmm.advanse.chv.definitionMining.uima.type.TokenTreetagger;

public class CasToHtmlWriter extends JCasAnnotator_ImplBase {

	static Logger logger = UIMAFramework.getLogger(CasToHtmlWriter.class);
	public static final String LF = System.getProperty("line.separator");

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		logger.log(Level.INFO, "process");
		StringBuilder sb = new StringBuilder();
		sb.append("=== CAS ===");
		sb.append(LF);
		sb.append("-- Document Text --");
		sb.append(LF);
		sb.append(jcas.getDocumentText());
		sb.append(LF);
		sb.append("-- Annotation --");
		sb.append(LF);

		// Add something to sb
//		for (TokenTreetagger a : JCasUtil.select(jcas, TokenTreetagger.class)) {
//			sb.append("[" + a.getType().getShortName() + "] ");
//			sb.append("(" + a.getBegin() + ", " + a.getEnd() + ") ");
//			sb.append("[Word: "+a.getWord()+"] ");
//			sb.append("[CoveredText: " + a.getCoveredText() + "] ");
//			// sb.append(a.getCoveredText());
//			sb.append(LF);
//		}
//		sb.append(LF);

		logger.log(Level.INFO, sb.toString());
		
//		ReaderPOC.scanPatMed(jcas.getDocumentText());
		try {
			CasToHtml(jcas, "src/main/resources/corpus/poc.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void CasToHtml(JCas jcas, String outputFile)
			throws IOException {
		// Build html content
		StringBuffer sb = new StringBuffer();
		String cssStyle = "<style>\n"
				+ "#patient {\n"
				+ "    background-color: yellow;\n" 
				+ "    color: black;\n"
				+ "}\n" 
				+ "#medecin {\n" 
				+ "    background-color: blue;\n"
				+ "    color: white;\n" 
				+ "}\n" + "</style>\n";
		String header = "<!DOCTYPE html>\n" 
				+ "<html>\n" 
				+ cssStyle
				+ "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
				+ "</head>\n"
				+ "<body>\n";
		sb.append(header);

		// TODO les documents doivent avoir un champ htmlText avec <p> text <i
		// id=patient|medecin>terme</i> text </p>
		// for ()
		String body = "";
		String text = jcas.getDocumentText();
		// OK
//		for(String post : text.split("\n") ) {
//			body += "<p>"+post+"</p>\n";
//		}
//		sb.append(body);
		
		Scanner txtscan = new Scanner(text);

		while (txtscan.hasNextLine()) {
			String str = txtscan.nextLine();
			body += "<p>"+str+"</p>\n";
			String txtHtml = str;
			// OK first occurence
//			for (String medTerm : ReaderPOC.medecinTargets) {
//				int index = str.indexOf(medTerm);
//				if (index != -1) {
//					if (ReaderPOC.exactMatch(medTerm,
//							str.substring(index, index + medTerm.length() + 1))) {
//						System.out.println(medTerm
//								+ " EXISTS : "
//								+ str.substring(index, index + medTerm.length()
//										+ 1));
//						txtHtml = str.substring(0, index)
//								+"<i id=\"medecin\">"
//								+ str.substring(index, index + medTerm.length())
//								+ "</i>"
//								+ str.substring(index + medTerm.length());
//						body += "<p style=\"color:red\">"+txtHtml+"</p>\n";
//					}
//				}
//			}
//			for (String patTerm : ReaderPOC.patientCandidates) {
//				int index = str.indexOf(patTerm);
//				if (index != -1) {
//					if (ReaderPOC.exactMatch(patTerm,
//							str.substring(index, index + patTerm.length() + 1))) {
//						System.out.println(patTerm
//								+ " EXISTS : "
//								+ str.substring(index, index + patTerm.length()
//										+ 1));
//						txtHtml = str.substring(0, index)
//								+"<i id=\"patient\">"
//								+ str.substring(index, index + patTerm.length())
//								+ "</i>"
//								+ str.substring(index + patTerm.length());
//						body += "<p style=\"color:blue\">"+txtHtml+"</p>\n";
//					}
//				}
//			}

			// OK Many occurences but annotate separately and with suffixe problem 
//			for (String medTerm : ReaderPOC.medecinTargets) {
//				int fromIndex = 0;
//				int index = -1;
//				while ( (index = str.indexOf(medTerm, fromIndex)) != -1 ) {
//					if (ReaderPOC.exactMatch(medTerm,
//							str.substring(index, index + medTerm.length() + 1))) {
//						System.out.println(medTerm
//								+ " EXISTS : "
//								+ str.substring(index, index + medTerm.length()
//										+ 1));
//						txtHtml = str.substring(0, index)
//								+"<i id=\"medecin\">"
//								+ str.substring(index, index + medTerm.length())
//								+ "</i>"
//								+ str.substring(index + medTerm.length());
//						body += "<p style=\"color:red\">"+txtHtml+"</p>\n";
//					}
//					// iterate
//					fromIndex = index+1;
//				}
//			}
//			for (String patTerm : ReaderPOC.patientCandidates) {
//				int fromIndex = 0;
//				int index = -1;
//				while ( (index = str.indexOf(patTerm, fromIndex)) != -1 ) {
//					if (ReaderPOC.exactMatch(patTerm,
//							str.substring(index, index + patTerm.length() + 1))) {
//						System.out.println(patTerm
//								+ " EXISTS : "
//								+ str.substring(index, index + patTerm.length()
//										+ 1));
//						txtHtml = str.substring(0, index)
//								+"<i id=\"patient\">"
//								+ str.substring(index, index + patTerm.length())
//								+ "</i>"
//								+ str.substring(index + patTerm.length());
//						body += "<p style=\"color:blue\">"+txtHtml+"</p>\n";
//					}
//					// iterate
//					fromIndex = index+1;
//				}
//			}
			
			String initialText = str;
			String searchIn = str;
			for (String medTerm : ReaderPOC.medecinTargets) {
				int fromIndex = 0;
				int index = -1;
				searchIn = str;
				while ( (index = searchIn.indexOf(medTerm, fromIndex)) != -1 ) {
					if (ReaderPOC.exactMatch(medTerm,
							searchIn.substring(index, index + medTerm.length() + 1))) {
						logger.log(Level.INFO, medTerm
								+ " EXISTS : "
								+ searchIn.substring(index, index + medTerm.length()
										+ 1));
						txtHtml = searchIn.substring(0, index)
								+"<i id=\"medecin\">"
								+ searchIn.substring(index, index + medTerm.length())
								+ "</i>"
								+ searchIn.substring(index + medTerm.length());
						searchIn = txtHtml;
					}
					// iterate
					fromIndex = index 
							+ "<i id=\"medecin\">".length() 
							+ medTerm.length() 
							+ "</i>".length();
				}
//				if (!searchIn.equals(initialText)) {
//					body += "<p style=\"color:blue\">"+searchIn+"</p>\n";
//				}
				str = searchIn;
			}
			for (String patTerm : ReaderPOC.patientCandidates) {
				int fromIndex = 0;
				int index = -1;
				searchIn = str;
				while ( (index = searchIn.indexOf(patTerm, fromIndex)) != -1 ) {
					if (ReaderPOC.exactMatch(patTerm,
							searchIn.substring(index, index + patTerm.length() + 1))) {
						logger.log(Level.INFO, patTerm
								+ " EXISTS : "
								+ searchIn.substring(index, index + patTerm.length()
										+ 1));
						txtHtml = searchIn.substring(0, index)
								+"<i id=\"patient\">"
								+ searchIn.substring(index, index + patTerm.length())
								+ "</i>"
								+ searchIn.substring(index + patTerm.length());
						searchIn = txtHtml;
					}
					// iterate
					fromIndex = index 
							+ "<i id=\"patient\">".length() 
							+ patTerm.length() 
							+ "</i>".length();
				}
//				if (!searchIn.equals(initialText)) {
//					body += "<p style=\"color:blue\">"+searchIn+"</p>\n";
//				}
				str = searchIn;
			}
			body += "<p style=\"color:blue\">"+searchIn+"</p>\n";
		}
		sb.append(body);
		txtscan.close();

		String footer = "</body>\n" + "</html>";
		sb.append(footer);
		// Write file
		FileUtils.write(new File(outputFile), sb.toString(), "UTF-8");
	}

}
