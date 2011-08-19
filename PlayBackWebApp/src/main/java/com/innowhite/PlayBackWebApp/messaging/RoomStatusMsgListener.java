package com.innowhite.PlayBackWebApp.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.service.RoomStatusService;

public class RoomStatusMsgListener implements MessageListener {

    RoomStatusService roomStatusService;

    public void setRoomStatusService(RoomStatusService roomStatusService) {
	this.roomStatusService = roomStatusService;
    }

    private static final Logger log = LoggerFactory.getLogger(RoomStatusMsgListener.class);

    public void onMessage(Message message) {
	// MapMessage mapMessage = (MapMessage) message;

	log.debug(" enter onMessage for RoomStatusMsgListener Data " + message);

	if (message instanceof TextMessage) {
	    try {

		String msg = message.getStringProperty("text");
		String msgType = message.getStringProperty("MSG_TYPE");
		String curTime = message.getStringProperty("CURRENT_TIME");

		if (msgType != null && msgType.equals("ROOM"))
		    roomStatusService.saveRoomStatus(msg);
		else if (msgType != null && msgType.equals("USER"))
		    roomStatusService.updateUserRoomLeftStatus(msg);

	    } catch (JMSException ex) {
		log.error(ex.getMessage(), ex);
	    }
	} else {
	    // throw new
	    // IllegalArgumentException("Message must be of type TextMessage");

	    log.warn("the message is :: " + message);

	}

	// try {
	//
	// roomStatusService.saveRoomStatus("  "+message);
	//
	// } catch (Exception e) {
	// log.error("jms exception", e.fillInStackTrace());
	// }
    }
}
