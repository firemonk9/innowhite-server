package com.innowhite.PlayBackWebApp.messaging;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WhiteboardDataMsgListener {

	
	
private static final Logger log = LoggerFactory.getLogger(WhiteboardDataMsgListener.class);
	
	public void onMessage(HashMap<String, Object> message) {
		//MapMessage mapMessage = (MapMessage) message;
		log.debug(" enter onMessage for Whiteboard Data ");
		try {
			
			
			
		} catch (Exception e) {
			log.error("jms exception", e.fillInStackTrace());
		}
	}
}
