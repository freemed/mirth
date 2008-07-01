package com.webreach.mirth.server.mule.adaptors;

import java.io.Reader;
import java.util.Map;

/** The base for classes to be implemented for Protocols that need to support
 *  batch processing - i.e., the processing of multiple messages per source
 *  (file).
 *  
 * @author Erik Horstkotte (erikh@webreachinc.com)
 */
public interface BatchAdaptor {

	public void processBatch(Reader src, Map properties, BatchMessageProcessor dest)
		throws Exception;
}
