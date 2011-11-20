package com.innowhite.PlaybackApp.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.service.PlaybackDataService;
import com.innowhite.PlaybackApp.service.TaskExecutorExample;

public class PlayBackMsgListener implements MessageListener {

    TaskExecutorExample taskExecutorExample;

    public void setTaskExecutorExample(TaskExecutorExample taskExecutorExample) {
        this.taskExecutorExample = taskExecutorExample;
    }

    private static final Logger log = LoggerFactory.getLogger(PlayBackMsgListener.class);

    public void onMessage(Message message) {
	// MapMessage mapMessage = (MapMessage) message;

	log.debug(" enter onMessage for RoomStatusMsgListener Data " + message);

	if (message instanceof TextMessage) {
	    try {

		String msg = message.getStringProperty("text");
		String msgType = message.getStringProperty("MSG_TYPE");
		String curTime = message.getStringProperty("CURRENT_TIME");

		String arr[] = msg.split("_");
		String roomId = arr[0];
		// roomStatusService.saveRoomStatus("  "+msg);
		taskExecutorExample.fire(roomId,true);
		
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
