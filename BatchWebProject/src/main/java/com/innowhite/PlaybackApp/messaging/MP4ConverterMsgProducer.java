package com.innowhite.PlaybackApp.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MP4ConverterMsgProducer {
	
	
		protected JmsTemplate jmsTemplate;
		
		protected Queue mp4converterDataDestination;
	
		public void setMp4converterDataDestination(Queue mp4converterDataDestination) {
			this.mp4converterDataDestination = mp4converterDataDestination;
		}
		
		public void setJmsTemplate(JmsTemplate jmsTemplate) {
			this.jmsTemplate = jmsTemplate;
		}

		
		public synchronized void  sendMessage(final String msg) {
			
			MessageCreator creator = new MessageCreator() {
				public Message createMessage(Session session) {
					TextMessage message = null;
					try {
						message = session.createTextMessage();
						message.setStringProperty("MSG_TYPE", "TEXT");
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
