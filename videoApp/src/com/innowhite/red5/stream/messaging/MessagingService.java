/**
 * 
 */
package com.innowhite.red5.stream.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author firemonk
 * 
 */
public class MessagingService {

    private static final Logger log = LoggerFactory.getLogger(MessagingService.class);
    /**
     * @param args
     */

    VideoStreamStatusMsgProducer videoMessageService;

    public void setVideoMessageService(VideoStreamStatusMsgProducer videoMessageService) {
	this.videoMessageService = videoMessageService;
    }

    public static void main(String[] args) {
	// ApplicationContext context = new
	// ClassPathXmlApplicationContext("messagingContext.xml");
	// SimpleMessageProducer smp =
	// (SimpleMessageProducer)context.getBean("roomMessageService");
	//
	// smp.sendMessage("");

    }

    /* To send any message about room(room start, room end) Queue. */
    public void sendStreamMessage(String msg) {
	log.debug("entered sendRoomMessage msg:" + msg);
	videoMessageService.sendMessage(msg);
    }

}
