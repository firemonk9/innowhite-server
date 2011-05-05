package com.innowhite.PlayBackWebApp.messaging;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.innowhite.PlayBackWebApp.service.WhiteboardDataService;


public class WhiteboardDataMsgListener {

	
	
	WhiteboardDataService whiteboardDataService;	
	
	


public void setWhiteboardDataService(WhiteboardDataService whiteboardDataService) {
		this.whiteboardDataService = whiteboardDataService;
	}

private static final Logger log = LoggerFactory.getLogger(WhiteboardDataMsgListener.class);
	
	public void onMessage(HashMap<String, Object> message) {
		//MapMessage mapMessage = (MapMessage) message;
		System.err.println(" ..entered onMessage "+message);
		log.debug(" enter onMessage for Whiteboard Data ");
		try {
			
			
			whiteboardDataService.saveWhitebordObj(message);
			


			
		} catch (Exception e) {
			log.error("jms exception", e.fillInStackTrace());
		}
	}
}
