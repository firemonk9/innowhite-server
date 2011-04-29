package com.innowhite.red5.audio;



import org.springframework.integration.annotation.Gateway;

import com.innowhite.red5.audio.events.ConferenceEvent;

public interface AudioEventListener {

	
	@Gateway(requestChannel="audioEventChannel")
	public void handleConferenceEvent(ConferenceEvent event);
}

