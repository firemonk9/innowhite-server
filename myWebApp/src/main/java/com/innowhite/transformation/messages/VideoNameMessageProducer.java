package com.innowhite.transformation.messages;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class VideoNameMessageProducer {

	private static final Logger log = LoggerFactory
			.getLogger(VideoNameMessageProducer.class);

	
	protected ActiveMQTopic destination; 
	
	public void setDestination(ActiveMQTopic destination) {
		this.destination = destination;
	}

	protected JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public synchronized void sendMessage(final String msg) {
		// txt = msg;
		MessageCreator creator = new MessageCreator() {
			public Message createMessage(Session session) {
				TextMessage message = null;
				try {
					message = session.createTextMessage();
					//message.setStringProperty("text", msg);
					message.setText(msg);
					log.debug("sending msg to topic .."+msg);
				} catch (JMSException e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
				return message;
			}
		};
		jmsTemplate.send(destination, creator);

	}
}
