package fr.lirmm.advanse.chv.acquisition;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.ctc.wstx.util.StringUtil;

public class ReaderPOC {
	public static List<String> patientCandidates = Arrays.asList("chimio",
			"onco", "mammo", "gynéco", "cancers", "crabe");
	public static List<String> medecinTargets = Arrays.asList("cancer du sein",
			"cancer", "oncologue", "oncologie", "mammographie", "gynécologue",
			"tumeur", "chirurgie", "ganglion");

	public static boolean exactMatch(String term, String textPlus1) {
		return StringUtils.strip(textPlus1, ")(!., ").equals(term);
	}

	public static void scanPatMed(String corpus) {
		// Annotate corpus
		Scanner txtscan = new Scanner(corpus);

		while (txtscan.hasNextLine()) {
			String str = txtscan.nextLine();
			for (String medTerm : medecinTargets) {
				int index = str.indexOf(medTerm);
				if (index != -1) {
					if (exactMatch(medTerm,
							str.substring(index, index + medTerm.length() + 1))) {
						System.out.println(medTerm
								+ " EXISTS : "
								+ str.substring(index, index + medTerm.length()
										+ 1));
					}
				}
			}
			for (String patTerm : patientCandidates) {
				int index = str.indexOf(patTerm);
				if (index != -1) {
					if (exactMatch(patTerm,
							str.substring(index, index + patTerm.length() + 1))) {
						System.out.println(patTerm
								+ " EXISTS : "
								+ str.substring(index, index + patTerm.length()
										+ 1));
					}
				}
			}
		}
		txtscan.close();

		// 1- Annotate with patient
		// 2- Annotate with medecin
	}

	public static void main(String[] args) throws IOException {
		String corpus = FileUtils.readFileToString(new File(
				"src/main/resources/corpus/corpusPOC.txt"));
		FileUtils.write(new File(
				"src/main/resources/corpus/corpusPOC2.txt"), corpus.replace(".", " . "));
	}
}
