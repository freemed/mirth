package com.webreach.mirth.server.builders;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.webreach.mirth.model.Step;
import com.webreach.mirth.model.Transformer;

public class JavaScriptTransformerBuilder {
	private Logger logger = Logger.getLogger(this.getClass());
	
	public String getScript(Transformer transformer) throws BuilderException {
		logger.debug("building java script");
		
		StringBuilder builder = new StringBuilder();

		for (Iterator iter = transformer.getSteps().iterator(); iter.hasNext();) {
			Step step = (Step) iter.next();
			logger.debug("adding step: " + step.getScript());
			builder.append(step.getScript() + "\n");
		}

		return builder.toString();
	}
}
