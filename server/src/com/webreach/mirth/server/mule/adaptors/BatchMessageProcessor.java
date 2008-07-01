package com.webreach.mirth.server.mule.adaptors;

/**
 * The interface that must be implemented by classes that handle message
 * callbacks from BatchAdapters.
 * 
 * @author Erik Horstkotte (erikh@webreachinc.com)
 */ 
public interface BatchMessageProcessor {

	public void processBatchMessage(String message)
		throws Exception;
}