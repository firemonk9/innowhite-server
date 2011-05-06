package com.innowhite.transformation.messages;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.innowhite.business.ProcessConversion;

public class RoomNameMessageProducer {

	private static final Logger log = LoggerFactory
			.getLogger(ProcessConversion.class);

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
					message.setStringProperty("text", msg);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				return message;
			}
		};

	}
}
