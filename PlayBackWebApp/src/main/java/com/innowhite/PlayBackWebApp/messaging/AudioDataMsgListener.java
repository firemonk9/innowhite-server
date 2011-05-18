package com.innowhite.PlayBackWebApp.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.service.AudioDataService;

public class AudioDataMsgListener {

    AudioDataService audioDataService;

    public void setAudioDataService(AudioDataService audioDataService) {
	this.audioDataService = audioDataService;
    }

    private static final Logger log = LoggerFactory.getLogger(AudioDataMsgListener.class);

    public void onMessage(String message) {
	// MapMessage mapMessage = (MapMessage) message;
	
	log.debug(" enter onMessage for Whiteboard Data "+message);
	try {

	     audioDataService.saveAudioData(message);

	} catch (Exception e) {
	    log.error("jms exception", e.fillInStackTrace());
	}
    }
}
