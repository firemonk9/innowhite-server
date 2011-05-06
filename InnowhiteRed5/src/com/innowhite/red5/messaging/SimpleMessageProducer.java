package com.innowhite.red5.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SimpleMessageProducer {

	private static final Logger log = LoggerFactory
			.getLogger(SimpleMessageProducer.class);

	protected JmsTemplate jmsTemplate;
	protected Queue destination;
	protected Queue whiteboardDataDestination;
	

	public void setWhiteboardDataDestination(Queue whiteboardDataDestination) {
		this.whiteboardDataDestination = whiteboardDataDestination;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	//protected int numberOfMessages = 1;

	//String txt=null;
	
	public synchronized void  sendMessage(final String msg) {
		//txt = msg;
		MessageCreator creator = new MessageCreator() {
			public Message createMessage(Session session) {
				TextMessage message = null;
				try {
					message = session.createTextMessage();
					message.setStringProperty("text", msg);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				return message;
			}
		};

		
	}

	// public void sendMessages(final DocConversionBean docBean)
	// throws JmsException {
	//
	// for (int i = 0; i < numberOfMessages; ++i) {
	// final int index = i;
	//
	// final String payload = "Message [" + i + "] sent at: " + new Date();
	//
	// jmsTemplate.send(new MessageCreator() {
	// public Message createMessage(Session session)
	// throws JmsException {
	// MapMessage message=null;
	// try {
	// message = session.createMapMessage();
	//
	// message.setString("userID", docBean.getUserID());
	// message.setInt("conversionID",
	// docBean.getConversionID());
	// message.setString("filePath", docBean.getFilePath());
	// message.setString("serviceType",
	// docBean.getServiceType());
	// message.setIntProperty("messageCount", index);
	// message.setString("serverFilePath",
	// docBean.getServerFilePath());
	// } catch (JMSException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // LOG.info("Sending message number [" + index + "]");
	// return message;
	// }
	// });
	// }
	// }
}