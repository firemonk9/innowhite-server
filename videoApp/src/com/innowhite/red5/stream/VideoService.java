package com.innowhite.red5.stream;

import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IClientBroadcastStream;
import org.slf4j.Logger;

import com.innowhite.red5.stream.util.InnowhiteConstants;

public class VideoService extends ApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(VideoService.class, InnowhiteConstants.APP_NAME);

    
    
    /*This function will be called from flex client*/
    public void stopScreenShare(String roomId) {
	//String conference = getConfId(roomId);
	
	IClientBroadcastStream stream = Application.screenShareStremIdMap.get(roomId);
	log.debug("Request has come from client to stop screen share ::"+roomId+"  and streamId :"+stream);
	StreamServiceHelper.closeStream(stream);
	// freeSwitchGateway.mute(conference, mute);
    }
    
	public HashMap<String, Object> checkIfStreamIsPublishing() {
			log.debug("VideoService: Entered checkIfStreamIsPublishing.. ");
			System.out.println("VideoService: Entered checkIfStreamIsPublishing.. ");
			IScope iScope = Red5.getConnectionLocal().getScope();
			String room = iScope.getName();
			log.debug("VideoService: Checking if %s is streaming."+ room);
			System.out.println("VideoService: Checking if %s is streaming."+ room);
			
			boolean publishing = false;
			int width = 500;
			int height = 500;
			IBroadcastStream ibs =null;
	  
//			streamManager !? (3000, IsStreamPublishing(room)) match {
//			  	case None => log.warning("Timeout waiting for reply to IsStreamPublishing for room %s", room)
//			  	case Some(rep) => {
//			  		val reply = rep.asInstanceOf[StreamPublishingReply]
//			  		publishing = reply.publishing
//			  		width = reply.width
//			  		height = reply.height
//			  	}
//			}
			
			ibs = getBroadcastStream(iScope, room); 
		
			
			if(ibs!=null){
					publishing =true;
					System.out.println("printing IBradcastStream: "+ibs);
					log.debug("printing IBradcastStream: "+ibs);
					System.out.println("creation time: "+ibs.getCreationTime());
					log.debug("creation time: "+ibs.getCreationTime());
					System.out.println("stream Name: "+ibs.getName()+", published Name: "+ibs.getPublishedName());
					log.debug("stream Name: "+ibs.getName()+", published Name: "+ibs.getPublishedName());
					System.out.println("MetaData: "+ibs.getMetaData()+", Provider: "+ibs.getProvider());
					log.debug("MetaData: "+ibs.getMetaData()+", Provider: "+ibs.getProvider());
					
			}
			
			
			HashMap<String,Object> stream = new HashMap<String, Object>();
			stream.put("publishing", publishing);
			stream.put("width", width);
			stream.put("height", height);
			return stream;
		}
		
		public void startedToViewStream() {
			log.debug("VideoService: Entered startedToViewStream.");
			System.out.println("VideoService: Entered startedToViewStream.");
			String room = Red5.getConnectionLocal().getScope().getName();
			log.debug("VideoService: Started viewing stream for room "+ room);
			System.out.println("VideoService: Started viewing stream for room "+ room);
//			sessionGateway.sendKeyFrame(room);
		}

}
