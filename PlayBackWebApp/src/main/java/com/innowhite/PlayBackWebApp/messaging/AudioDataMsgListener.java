package com.innowhite.PlayBackWebApp.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.service.AudioDataService;

public class AudioDataMsgListener implements MessageListener{

    AudioDataService audioDataService;

    public void setAudioDataService(AudioDataService audioDataService) {
	this.audioDataService = audioDataService;
    }

    private static final Logger log = LoggerFactory.getLogger(AudioDataMsgListener.class);

    public void onMessage(Message message) {

	// System.out
	// .println("entered  onMessage of RoomConfNameMapMessageListener.."
	// + message);
	log.debug(" entered  onMessage of AudioDataMsgListener..");

	if (message instanceof TextMessage) {
	    try {

		String msg =message.getStringProperty("text");
		audioDataService.saveAudioData(msg);
		
	    } catch (JMSException ex) {
		log.error(ex.getMessage(), ex);
	    }
	} else {
	    // throw new
	    // IllegalArgumentException("Message must be of type TextMessage");

	    log.warn("the message is :: " + message);

	}
    }

}
