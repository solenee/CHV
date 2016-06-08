package fr.lirmm.advanse.chv.acquisition.uima;

/**
 * Raised when an exception occurs while performing the distributional analysis
 * @author solene eholie
 *
 */
public class DistributionalAnalysisException  extends RuntimeException {

	/** Initialize a DistributionalAnalysisException with a message.
	  * @param message explanations
	  */
	public DistributionalAnalysisException(String message) {
		super(message);
	}

}