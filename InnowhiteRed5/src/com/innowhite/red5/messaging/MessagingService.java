/**
 * 
 */
package com.innowhite.red5.messaging;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.red5.util.InnowhiteConstants;
import com.innowhite.red5.vo.ShapeEventsVO;

/**
 * @author prashanthj
 * 
 */
public class MessagingService {

    private static Logger log = Red5LoggerFactory.getLogger(MessagingService.class, InnowhiteConstants.APP_NAME);

    /**
     * @param args
     */

    WhiteboardDataMessageProducer whiteboardDataMessageService;
    PlayBackMessageProducer playBackMessageProducer;

    public PlayBackMessageProducer getPlayBackMessageProducer() {
	return playBackMessageProducer;
    }

    public void setPlayBackMessageProducer(PlayBackMessageProducer playBackMessageProducer) {
	this.playBackMessageProducer = playBackMessageProducer;
    }

    public void setWhiteboardDataMessageService(WhiteboardDataMessageProducer whiteboardDataMessageService) {
	this.whiteboardDataMessageService = whiteboardDataMessageService;
    }

    SimpleMessageProducer roomMessageService;

    public void setRoomMessageService(SimpleMessageProducer roomMessageService) {
	this.roomMessageService = roomMessageService;
    }

    public static void main(String[] args) {
	ApplicationContext context = new ClassPathXmlApplicationContext("messagingContext.xml");
	SimpleMessageProducer smp = (SimpleMessageProducer) context.getBean("roomMessageService");

	smp.sendMessage("TEXT", "ROOM");

    }

    /* To send any message about room(room start, room end) Queue. */
    public void sendRoomMessage(String msg) {
	log.debug("entered sendRoomMessage msg:" + msg);
	roomMessageService.sendMessage(msg, "ROOM");
	if (msg != null && msg.indexOf("_STOPPED_") > 0) {
	    playBackMessageProducer.sendMessage(msg);
	}
	// playBackMessageProducer
    }

    /*
     * To send any message about users of a room(user join room , user left room
     * ) Queue.
     */
    public void sendUserRoomMessage(String msg) {
	log.debug("entered sendRoomMessage msg:" + msg);
	roomMessageService.sendMessage(msg, "USER");
	if (msg != null && msg.indexOf("_STOPPED_") > 0) {
	    playBackMessageProducer.sendMessage(msg);
	}
	// playBackMessageProducer
    }

    /* To send any message about room(room start, room end) Queue. */
    public void sendWhiteboardData(ShapeEventsVO msg, String roomName, long l) {
	log.debug("entered sendWhiteboardData msg:" + msg);
	whiteboardDataMessageService.sendMessages(msg, roomName, l);
    }

}
