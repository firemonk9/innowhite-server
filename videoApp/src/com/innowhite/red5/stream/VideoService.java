package com.innowhite.red5.stream;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.stream.IClientBroadcastStream;
import org.slf4j.Logger;

import com.innowhite.red5.stream.util.InnowhiteConstants;

public class VideoService extends ApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(VideoService.class, InnowhiteConstants.APP_NAME);

    public void stopScreenShare(String roomId) {
	//String conference = getConfId(roomId);
	
	
	IClientBroadcastStream stream = Application.screenShareStremIdMap.get(roomId);
	
	log.debug("Request has come from client to stop screen share ::"+roomId+"  and streamId :"+stream);
	
	StreamServiceHelper.closeStream(stream);
	// freeSwitchGateway.mute(conference, mute);
    }

}
