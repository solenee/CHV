package fr.lirmm.advanse.acquisition.uima.model.api;

/**
 * Stopwords list
 * @author soleneeholie
 *
 */
public interface IStopwords {
	
	public static int MIN_LENGTH_ADMITTED = 3;
	
	public boolean isStopword(String token);
}
