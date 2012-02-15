package com.innowhite.whiteboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.dao.PollingDAO;


/** To implement PollingService class
 * @author Tanuja
 * @Date Feb 15, 2012
 */

public class PollingService {
	private static final Logger log = LoggerFactory.getLogger(PollingService.class);
	
	/* To get the pollid for new poll   */
	public static String getPollID() {
		log.debug("Entered getPollID");
		
		int intPollId=0;
		intPollId=PollingDAO.getpollId();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<polling>");
		sb.append("<pollid>" + intPollId + "</pollid>");
		sb.append("</polling>");
		 
		return sb.toString();
	}
}
