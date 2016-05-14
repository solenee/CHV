package fr.lirmm.advanse.chv.acquisition.uima.model.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import fr.lirmm.advanse.chv.acquisition.uima.model.api.IStopwords;

/**
 * Stopwords list
 * @author soleneeholie
 *
 */
public class Stopwords implements IStopwords {
	
	private Set<String> stopwords;
	private final File stopwordsFile;
	private final boolean discardAccents; 
	
	public Stopwords(File file, boolean discardAccents) throws IOException {
		this.stopwordsFile = file;
		this.discardAccents = discardAccents;
		initStopwords();
	}

	public boolean isStopword(String token) {
		if (discardAccents) {
			return stopwords.contains(normalizeDiacritic(token));
		} else {
			return stopwords.contains(token);
		}
	}
	
	public static String normalizeDiacritic(String token) {
		return token
				.replace("à", "a")
				.replace("â", "a")
				.replace("ä", "a")
				.replace("é", "e")
				.replace("è", "e")
				.replace("ê", "e")
				.replace("ë", "e")
				.replace("ù", "u")
				.replace("û", "u")
				.replace("ü", "u")
				.replace("î", "i")
				.replace("ï", "i")
				.replace("ô", "o")
				.replace("ö", "o")
				.replace("œ", "oe")
				.replace("ç", "c");
	}
	
	private void initStopwords() throws IOException {
		stopwords = new TreeSet<String>();
		BufferedReader b = new BufferedReader(new FileReader(stopwordsFile));
		String line = "";
		while ((line = b.readLine()) != null) {
			if (! line.equals("")) {
				if (discardAccents) {
					stopwords.add(
							normalizeDiacritic(line.trim().toLowerCase())
							);
				} else {
					stopwords.add(line.trim().toLowerCase());
				}
			}
		}
		b.close();
	}
}
