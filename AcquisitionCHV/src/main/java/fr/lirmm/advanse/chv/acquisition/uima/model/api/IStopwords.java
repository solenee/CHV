package fr.lirmm.advanse.chv.acquisition.uima.model.api;

import java.io.File;

/**
 * Stopwords list
 * @author soleneeholie
 *
 */
public interface IStopwords {
	
	public static int MIN_LENGTH_ADMITTED = 3;
	
	public File DEFAULT_FILE = new File("src/main/resources/stopwords/stopwords_users.txt");
	
	public boolean isStopword(String token);
}
