package fr.lirmm.advanse.chv.acquisition.uima.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import fr.lirmm.advanse.chv.acquisition.uima.model.api.IContextVector;

//https://jsonformatter.curiousconcept.com/
public class ContextVector implements IContextVector {

	private Map<String, Integer> context;
	private final Integer VALUE_INIT = new Integer(1);
	
	public ContextVector() {
		context = new HashMap<String, Integer>();
	}
	
	@Override
	public synchronized void inc(String item) {
		Integer v = context.get(item);
		if (v == null) {
			context.put(item, VALUE_INIT);
		} else {
			context.put(item, v+1);
		}
		
	}

	/**
	 * TODO : Escape ", \
	 */
	public String toJSon_deprecated() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ "); // the white space is important, please do not remove it! 
		for (String item : context.keySet()) {
			sb.append("\n\t"+"\""+item+"\""+" : "+context.get(item)+" ,");
		}
		// Remove ","
		return sb.substring(0, sb.length()-1)+"}";
	}
	
	/**
	 * Escape of ", \, ' is handled by JSONObject => \", \\, \u0092
	 */
	@Override
	public String toJSon() {
		JSONObject obj = new JSONObject();
		for (String item : context.keySet()) {
			obj.put(item, context.get(item));
		}
		return obj.toString();
	}
	

}
