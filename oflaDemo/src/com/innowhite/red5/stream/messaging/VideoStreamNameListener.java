package com.innowhite.red5.stream.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoStreamNameListener implements MessageListener {

	
	public static  List<String> videoStreamIds = new ArrayList<String>();

	private static final Logger log = LoggerFactory
			.getLogger(VideoStreamNameListener.class);

	@Override
	public void onMessage(Message message) {

		// System.out
		// .println("entered  onMessage of RoomConfNameMapMessageListener.."
		// + message);
		log.debug("oflademo entered  onMessage of RoomConfNameMapMessageListener..");
		System.err
				.println("oflademo entered  onMessage of RoomConfNameMapMessageListener..");
		if (message instanceof TextMessage) {
			try {
				System.out.println(" the msg is::  "
						+ ((TextMessage) message).getText());
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

		System.out.println("oflademo entered populateCache :: " + msg);
		log.debug("oflademo entered populateCache :: " + msg);
		if (msg != null) {
			if (msg.indexOf("_") > 0) {
				StringTokenizer st = new StringTokenizer(msg, "_");

				String streamId = st.nextToken();
				String  streamType = st.nextToken();

				// roomId + "_" + confNumber+"_"+userId

				System.out.println("oflademo adding addConfRoom  :: streamId" + streamId
						+ "  streamType: " + streamType );
				log.debug("oflademo adding addConfRoom  :: streamId" + streamId
						+ "  streamType: " + streamType);
				
				
				if(!videoStreamIds.contains(streamId)){
					videoStreamIds.add(streamId);
				}
				
//
//				if (UserCacheService.getActualRoom(extension) == null) {
//					UserCacheService.addConfRoom(extension, roomId);
//				}
//				UserCacheService.addInnoUniqueIDUser(confId, userId);

			}
		}

	}

}
