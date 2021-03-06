package fr.lirmm.advanse.chv.acquisition.uima.writer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasFileWriter_ImplBase;
import fr.lirmm.advanse.chv.acquisition.type.BioEntity;
import fr.lirmm.advanse.chv.acquisition.type.MedEntity;
import fr.lirmm.advanse.chv.acquisition.type.PatEntity;
import fr.lirmm.advanse.chv.acquisition.type.Post;

public class CasToHtmlWriter_BioEntity extends JCasConsumer_ImplBase {

	Logger logger = UIMAFramework.getLogger(CasToHtmlWriter_BioEntity.class);
	public static final String LF = System.getProperty("line.separator");
	public static File outputFile = new File("src/main/resources/corpus/poc_BioEntity.html");
	
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		logger.log(Level.INFO, "process");
		StringBuilder sb = new StringBuilder();
		StringBuilder sbHtml = new StringBuilder();
		sb.append("=== CAS ===");
		sb.append(LF);
		sb.append("-- Document Text --");
		sb.append(LF);
		//sb.append(jcas.getDocumentText());
		sb.append(LF);
		logger.log(Level.INFO, sb.toString());
		
		sb = new StringBuilder();
		sb.append("-- Annotation --");
		sb.append(LF);

		// Add something to sb
		for (BioEntity a : JCasUtil.select(jcas, BioEntity.class)) {
			sb.append("[" + a.getType().getShortName() + "] ");
			sb.append("[NormalizedForm: " + a.getNormalizedForm() + "] ");
			sb.append("[CoveredText: " + a.getCoveredText() + "] ");
			sb.append(LF);
		}
		logger.log(Level.FINE, sb.toString());
		
		// Build HTML file
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
		sbHtml.append(header);
		for (Post post : JCasUtil.select(jcas, Post.class)) {
			for (BioEntity e : JCasUtil.selectCovered(jcas, MedEntity.class, post)) {
				//System.out.println("=================="+e.getNormalizedForm());
				sbHtml.append("<i id=\"medecin\">"+e.getNormalizedForm()+"</i> ");
			}
			sbHtml.append("<br/>\n");
			for (BioEntity e : JCasUtil.selectCovered(jcas, PatEntity.class, post)) {
				//System.out.println("=================="+e.getNormalizedForm());
				sbHtml.append("<i id=\"patient\">"+e.getNormalizedForm()+"</i> ");
			}
			sbHtml.append("<br/>\n");
			sbHtml.append("<p>"+post.getCoveredText()+"</p>\n");
//			for (BioEntity e : JCasUtil.selectCovering(jcas, BioEntity.class, post)) {
//				System.out.println("=!================="+e.getNormalizedForm());
//				sbHtml.append(e.getCoveredText()+" - "+e.getNormalizedForm()+"<br/>\n");
//			}
		}
		sbHtml.append(
				"</body>\n"
				+ "</html>");
		
		// Write file
		try {
			FileUtils.write(outputFile, sbHtml.toString(), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
//		ReaderPOC.scanPatMed(jcas.getDocumentText());
		try {
			CasToHtml(jcas, "src/main/resources/corpus/poc.html");
			CasToHtml_BioEntity(jcas, "src/main/resources/corpus/poc_BioEntity.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}
/*
	public static void CasToHtml_BioEntity(JCas jcas, String outputFile)
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
						
			String initialText = str;
			String searchIn = str;
			for (String medTerm : ReaderPOC.medecinTargets) {
				int fromIndex = 0;
				int index = -1;
				searchIn = str;
				while ( (index = searchIn.indexOf(medTerm, fromIndex)) != -1 ) {
					if (ReaderPOC.exactMatch(medTerm,
							searchIn.substring(index, index + medTerm.length() + 1))) {
						System.out.println(medTerm
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
						System.out.println(patTerm
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
						System.out.println(medTerm
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
						System.out.println(patTerm
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
*/
}
