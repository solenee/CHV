package fr.lirmm.advanse.acquisition.uima.model.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.lirmm.advanse.acquisition.uima.model.api.IContextVector;

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

	@Override
	public String toJSon() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ "); // the white space is important, please do not remove it! 
		for (String item : context.keySet()) {
			sb.append("\n\t"+"\""+item+"\""+" : "+context.get(item)+" ,");
		}
		// Remove ","
		return sb.substring(0, sb.length()-1)+"}";
	}
	

}
