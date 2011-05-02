package com.innowhite.PlayBackWebApp.messaging;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.innowhite.PlayBackWebApp.model.WhiteboardDataDao;


public class WhiteboardDataMsgListener {

	@Autowired
	WhiteboardDataDao whiteboardDataDao;	
	
	
public void setWhiteboardDataDao(WhiteboardDataDao whiteboardDataDao) {
		this.whiteboardDataDao = whiteboardDataDao;
	}

private static final Logger log = LoggerFactory.getLogger(WhiteboardDataMsgListener.class);
	
	public void onMessage(HashMap<String, Object> message) {
		//MapMessage mapMessage = (MapMessage) message;
		System.err.println(" ..entered onMessage "+message);
		log.debug(" enter onMessage for Whiteboard Data ");
		try {
			
			
			whiteboardDataDao.saveWhitebordObj(message);
			


			
		} catch (Exception e) {
			log.error("jms exception", e.fillInStackTrace());
		}
	}
}
