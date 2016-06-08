package fr.lirmm.advanse.chv.acquisition.uima;

/**
 * Raised when an application is launched without the correct configuration
 * @author solene eholie
 *
 */
public class ConfigurationException  extends RuntimeException  {
	
	/** Initialize a ConfigurationException with a message.
	  * @param message explanations
	  */
	public ConfigurationException(String message) {
		super(message);
	}
}
