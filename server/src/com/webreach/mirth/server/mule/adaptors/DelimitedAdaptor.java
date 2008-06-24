package com.webreach.mirth.server.mule.adaptors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Scanner;

import org.mule.umo.MessagingException;
import org.mule.umo.UMOException;

import com.webreach.mirth.model.MessageObject;
import com.webreach.mirth.model.converters.DelimitedSerializer;
import com.webreach.mirth.model.converters.IXMLSerializer;
import com.webreach.mirth.model.converters.SerializerFactory;

public class DelimitedAdaptor extends Adaptor implements BatchAdaptor {

	protected void populateMessage(boolean emptyFilterAndTransformer) throws AdaptorException {

		messageObject.setRawDataProtocol(MessageObject.Protocol.DELIMITED);
		messageObject.setTransformedDataProtocol(MessageObject.Protocol.XML);
		messageObject.setEncodedDataProtocol(MessageObject.Protocol.DELIMITED);
		
		try {
			String message = serializer.toXML(source);
			messageObject.setTransformedData(message);
			populateMetadataFromXML(message);
		} catch (Exception e) {
			handleException(e);
		}

		if (emptyFilterAndTransformer) {
			messageObject.setEncodedData(source);
		}
	}

	@Override
	public IXMLSerializer<String> getSerializer(Map properties) {
		return SerializerFactory.getSerializer(MessageObject.Protocol.DELIMITED, properties);
	}

	public void processBatch(Reader src, Map properties, BatchMessageProcessor dest)
		throws MessagingException, UMOException, IOException
	{
		DelimitedSerializer serializer = new DelimitedSerializer(properties);
		BufferedReader in = new BufferedReader(src);
		String message;
		boolean skipHeader = true;
		while ((message = serializer.getMessage(in, skipHeader)) != null) {
			dest.processBatchMessage(message);
			skipHeader = false;
		}
	}
}
