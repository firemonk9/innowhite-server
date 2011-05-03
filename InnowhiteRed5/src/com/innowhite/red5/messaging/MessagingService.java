/**
 * 
 */
package com.innowhite.red5.messaging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import com.innowhite.red5.vo.DocConversionBean;
import com.innowhite.red5.vo.ShapeEventsVO;

/**
 * @author prashanthj
 *
 */
public class MessagingService {

	private static final Logger log = LoggerFactory.getLogger(MessagingService.class);
	/**
	 * @param args
	 */
	
	WhiteboardDataMessageProducer whiteboardDataMessageService;
	
	
	public void setWhiteboardDataMessageService(
			WhiteboardDataMessageProducer whiteboardDataMessageService) {
		this.whiteboardDataMessageService = whiteboardDataMessageService;
	}

	SimpleMessageProducer roomMessageService;
	
	
	public void setRoomMessageService(SimpleMessageProducer roomMessageService) {
		this.roomMessageService = roomMessageService;
	}


	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("messagingContext.xml");
		SimpleMessageProducer smp = (SimpleMessageProducer)context.getBean("roomMessageService");
		 
		smp.sendMessage("");
		

	}
	
	/* To send any message about room(room start, room end) Queue.*/
	public void sendRoomMessage(String msg){
		log.debug("entered sendRoomMessage msg:"+msg);
		roomMessageService.sendMessage(msg);
	}
	
	
	
	/* To send any message about room(room start, room end) Queue.*/
	public void sendWhiteboardData(ShapeEventsVO msg, String roomName, long l){
		log.debug("entered sendRoomMessage msg:"+msg);
		whiteboardDataMessageService.sendMessages(msg,roomName,l);
	}
	

}
