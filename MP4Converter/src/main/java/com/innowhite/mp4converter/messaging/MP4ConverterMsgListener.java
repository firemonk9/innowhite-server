package com.innowhite.mp4converter.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;

import com.innowhite.mp4converter.service.MP4ConverterService;

/**
 * @author tanuja
 * @Date Feb 27, 2012
 */

public class MP4ConverterMsgListener implements MessageListener {

	MP4ConverterService mp4ConverterService;

	public void setMp4ConverterService(MP4ConverterService mp4ConverterService) {
		this.mp4ConverterService = mp4ConverterService;
	}

	private static final Logger log = LoggerFactory.getLogger(MP4ConverterMsgListener.class);

	public void onMessage(Message message) {
		log.debug(" enter onMessage for MP4ConverterMsgListener Data " + message);

		try {
			if (message instanceof TextMessage) {

				String msg = message.getStringProperty("text");
				String arr[] = msg.split("\\^");

				String filePath = arr[0];

				String fileId = arr[1];
				log.debug(" filePath and fileId for MP4ConverterMsgListener " + filePath + "#####" + fileId);
				mp4ConverterService.processFLVFile(filePath, fileId);

			} else {
				log.warn("the message is :: " + message);
			}
		} catch (JmsException e) {
			log.error(" exception", e.fillInStackTrace());
		} catch (Exception e) {
			log.error(" exception", e.fillInStackTrace());
		}
	}

}
