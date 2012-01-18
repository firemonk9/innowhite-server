package com.innowhite.red5.messaging;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innowhite.red5.util.InnowhiteConstants;


public class AudioRecordMessageListener implements MessageListener {

	// WhiteboardDataService whiteboardDataService;
	//
	//
	//
	//
	// public void setWhiteboardDataService(WhiteboardDataService
	// whiteboardDataService) {
	// this.whiteboardDataService = whiteboardDataService;
	// }


    private static Logger log = Red5LoggerFactory.getLogger(
	    AudioRecordMessageListener.class, InnowhiteConstants.APP_NAME);

    
	@Override
	public void onMessage(Message message) {

		// System.out
		// .println("entered  onMessage of RoomConfNameMapMessageListener.."
		// + message);
		log.debug("Innowhitered5 entered  onMessage of AudioRecordMessageListener..");
		System.err.println("Innowhitered5 entered  onMessage of AudioRecordMessageListener..");
		if (message instanceof TextMessage) {
			try {
			    
			    	log.debug(" the msg is::  "+((TextMessage) message).getText());
				System.out.println(" the msg is::  "+((TextMessage) message).getText());
				String msg = ((TextMessage) message).getText();

				audioRecordStatus(msg);

			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			// throw new
			// IllegalArgumentException("Message must be of type TextMessage");
			System.out.println("the message is :: " + message);
			log.debug("the message is :: " + message);

		}
	}

	private void audioRecordStatus(String msg) {

		System.out.println("entered populateCache :: " + msg);
		log.debug("entered populateCache :: " + msg);
		if (msg != null) {
		    
		    Gson gsond = new Gson();

			Map map = gsond.fromJson(msg, new TypeToken<Map<String, String>>() {
			}.getType());
		    
			String room = (String)map.get("room");
			String record = (String)map.get("record");
			
			
//			if (msg.indexOf("_") > 0) {
//				StringTokenizer st = new StringTokenizer(msg,
//						"_");
//
//				String roomId = st.nextToken();
//				String confId = st.nextToken();
//				String userId = st.nextToken();
//				
//				
//				//roomId + "_" + confNumber+"_"+userId
//				
//				System.out.println("adding addConfRoom  :: roomId" + roomId+"  confId: "+confId+"  userId::"+userId);
//				log.debug("adding addConfRoom  :: roomId" + roomId+"  confId: "+confId+"  userId::"+userId);
//				String extension = confId.substring(2);
//				
//				if(UserCacheService.getActualRoom(extension) == null){	
//					UserCacheService.addConfRoom(extension, roomId);
//				}
//				
//				
//				UserCacheService.addInnoUniqueIDUser(confId, userId);
//				
//				
//				
//			}
		}

	
	}

	// private

}
