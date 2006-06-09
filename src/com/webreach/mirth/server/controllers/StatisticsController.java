package com.webreach.mirth.server.controllers;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.webreach.mirth.model.Statistics;
import com.webreach.mirth.server.util.JMXConnection;

/**
 * The StatisticsContoller provides access to channel statistics.
 * 
 * @author GeraldB
 *
 */
public class StatisticsController {
	private Logger logger = Logger.getLogger(StatisticsController.class);
	private JMXConnection jmxConnection = null;
	
	/**
	 * Returns a Statistics object for the channel with the specified id.
	 * 
	 * @param channelId
	 * @return
	 * @throws ControllerException
	 */
	public Statistics getStatistics(int channelId) throws ControllerException {
		logger.debug("retrieving statistics: " + channelId);
		
		Statistics statistics = new Statistics();
		statistics.setErrorCount(getErrorCount(channelId));
		statistics.setQueueSize(getQueueSize(channelId));
		statistics.setReceivedCount(getReceivedCount(channelId));
		statistics.setSentCount(getSentCount(channelId));
		return statistics;
	}
	
	/**
	 * Clears all of the statistics for the channel with the specified id.
	 * 
	 * @param channelId
	 * @throws ControllerException
	 */
	public void clearStatistics(int channelId) throws ControllerException {
		logger.debug("clearing statistics: " + channelId);
		
		try {
			jmxConnection = new JMXConnection();
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("type", "statistics");
			properties.put("name", String.valueOf(channelId));
			jmxConnection.invokeOperation(properties, "clear", null);
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}
	
	private int getSentCount(int channelId) {
		logger.debug("retrieving message sent count: " + channelId);
		
		try {
			jmxConnection = new JMXConnection();
			return getStatistic(channelId, "TotalEventsSent");
		} catch (Exception e) {
			return -1;
		}
	}

	// This is a hack to address the fact that this statistic is incorrectly
	// incrememnted by 2 in Mule.
	private int getReceivedCount(int channelId) {
		logger.debug("retrieving message received count: " + channelId);
		
		try {
			jmxConnection = new JMXConnection();
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("type", "statistics");
			properties.put("name", String.valueOf(channelId));
			Double count = ((Long) jmxConnection.getAttribute(properties, "TotalEventsReceived")).doubleValue();
			return Double.valueOf(Math.ceil(count / 2)).intValue();
		} catch (Exception e) {
			return -1;
		}
	}

	private int getErrorCount(int channelId) {
		logger.debug("retrieving error count: " + channelId);
		
		try {
			jmxConnection = new JMXConnection();
			return getStatistic(channelId, "ExecutionErrors");
		} catch (Exception e) {
			return -1;
		}
	}

	private int getQueueSize(int channelId) {
		logger.debug("retrieving message queue count: " + channelId);
		
		try {
			jmxConnection = new JMXConnection();
			return getStatistic(channelId, "QueuedEvents");
		} catch (Exception e) {
			return -1;
		}
	}

	private int getStatistic(int channelId, String statistic) throws ControllerException {
		try {
			jmxConnection = new JMXConnection();
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("type", "statistics");
			properties.put("name", String.valueOf(channelId));

			return ((Long) jmxConnection.getAttribute(properties, statistic)).intValue();
		} catch (Exception e) {
			throw new ControllerException();
		}
	}

}
