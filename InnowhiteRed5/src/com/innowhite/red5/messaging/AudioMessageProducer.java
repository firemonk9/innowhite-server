package com.innowhite.red5.messaging;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class AudioMessageProducer {

	private static final Logger log = LoggerFactory
			.getLogger(AudioMessageProducer.class);

	protected JmsTemplate jmsTemplate;
	
	protected Queue audioDataDestination;
	

	

	public void setAudioDataDestination(Queue audioDataDestination) {
		this.audioDataDestination = audioDataDestination;
	}

	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	protected int numberOfMessages = 1;

	//String txt=null;
	
	public synchronized void  sendMessage(final String msg) {
		//txt = msg;
		MessageCreator creator = new MessageCreator() {
			public Message createMessage(Session session) {
				TextMessage message = null;
				try {
					message = session.createTextMessage();
					message.setStringProperty("MSG_TYPE", "AUDIO");
					message.setStringProperty("CURRENT_TIME", String.valueOf(new Date().getTime()));
					message.setStringProperty("text", msg);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				return message;
			}
		};
		jmsTemplate.send(creator);
		
	}

}
