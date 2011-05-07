package com.innowhite.red5.messaging;

import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.red5.UserCacheService;


public class RoomConfNameMapMessageListener implements MessageListener {

	// WhiteboardDataService whiteboardDataService;
	//
	//
	//
	//
	// public void setWhiteboardDataService(WhiteboardDataService
	// whiteboardDataService) {
	// this.whiteboardDataService = whiteboardDataService;
	// }

	private static final Logger log = LoggerFactory
			.getLogger(RoomConfNameMapMessageListener.class);

	@Override
	public void onMessage(Message message) {

		// System.out
		// .println("entered  onMessage of RoomConfNameMapMessageListener.."
		// + message);
		log.debug("entered  onMessage of RoomConfNameMapMessageListener..");
		System.err.println("entered  onMessage of RoomConfNameMapMessageListener..");
		if (message instanceof TextMessage) {
			try {
				System.out.println(" the msg is::  "+((TextMessage) message).getText());
				String msg = ((TextMessage) message).getText();

				populateCache(msg);

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

	private void populateCache(String msg) {

		System.out.println("entered populateCache :: " + msg);
		log.debug("entered populateCache :: " + msg);
		if (msg != null) {
			if (msg.indexOf("_") > 0) {
				StringTokenizer st = new StringTokenizer(msg,
						"_");

				String roomId = st.nextToken();
				String confId = st.nextToken();
				String userId = st.nextToken();
				
				
				//roomId + "_" + confNumber+"_"+userId
				
				System.out.println("adding addConfRoom  :: roomId" + roomId+"  confId: "+confId+"  userId::"+userId);
				log.debug("adding addConfRoom  :: roomId" + roomId+"  confId: "+confId+"  userId::"+userId);
				String extension = confId.substring(2);
				
				if(UserCacheService.getActualRoom(extension) == null){	
					UserCacheService.addConfRoom(extension, roomId);
				}
				
				
				UserCacheService.addInnoUniqueIDUser(confId, userId);
				
				
				
			}
		}

	
	}

	// private

}
