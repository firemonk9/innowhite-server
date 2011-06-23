package com.innowhite.PlayBackWebApp.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.service.AudioVideoDataService;

public class AudioVideoDataMsgListener implements MessageListener{

    AudioVideoDataService audioVideoDataService;

 
    public AudioVideoDataService getAudioVideoDataService() {
        return audioVideoDataService;
    }

    public void setAudioVideoDataService(AudioVideoDataService audioVideoDataService) {
        this.audioVideoDataService = audioVideoDataService;
    }

    private static final Logger log = LoggerFactory.getLogger(AudioVideoDataMsgListener.class);

    public void onMessage(Message message) {

	// System.out
	// .println("entered  onMessage of RoomConfNameMapMessageListener.."
	// + message);
	log.debug(" entered  onMessage of AudioVideoDataMsgListener..");

	if (message instanceof TextMessage) {
	    try {

		String msg =message.getStringProperty("text");
		String msgType = message.getStringProperty("MSG_TYPE");
		String curTime = message.getStringProperty("CURRENT_TIME");
		
		log.debug(" msg "+msg+"  curTime "+curTime);
		
		audioVideoDataService.saveAudioData(msg,msgType,curTime);
		
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
