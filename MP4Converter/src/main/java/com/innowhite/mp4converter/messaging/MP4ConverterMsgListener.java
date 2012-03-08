package com.innowhite.mp4converter.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.mp4converter.service.MP4ConverterService;

/**
 * @author tanuja
 * @Date Feb 27, 2012
 */

public class MP4ConverterMsgListener implements MessageListener{

	MP4ConverterService mp4ConverterService;

    public void setMp4ConverterService(MP4ConverterService mp4ConverterService) {
		this.mp4ConverterService = mp4ConverterService;
	}

	private static final Logger log = LoggerFactory.getLogger(MP4ConverterMsgListener.class);

    public void onMessage(Message message) {
    	log.debug(" enter onMessage for MP4ConverterMsgListener Data " + message);
    	if (message instanceof TextMessage) {
			try{
				String msg = message.getStringProperty("text");
				
				String arr[] = msg.split("_");
				String filePath = arr[0];
				String fileId = arr[1];
				mp4ConverterService.processFLVFile(filePath,fileId);
				
			 } catch (JMSException ex) {
			log.error(ex.getMessage(), ex);
		    }
		} else {
		     log.warn("the message is :: " + message);
		}
		
    }
    
}
