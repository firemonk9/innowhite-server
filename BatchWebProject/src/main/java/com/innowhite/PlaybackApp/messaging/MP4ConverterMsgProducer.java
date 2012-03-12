package com.innowhite.PlaybackApp.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.innowhite.PlaybackApp.service.PlaybackDataService;

public class MP4ConverterMsgProducer {
	
	private static final Logger log = LoggerFactory.getLogger(MP4ConverterMsgProducer.class);
	
		protected JmsTemplate jmsTemplate;
		
		protected Queue mp4converterDataDestination;
	
		public void setMp4converterDataDestination(Queue mp4converterDataDestination) {
			this.mp4converterDataDestination = mp4converterDataDestination;
		}
		
		public void setJmsTemplate(JmsTemplate jmsTemplate) {
			this.jmsTemplate = jmsTemplate;
		}

		
		public synchronized void  sendMessage(final String msg) {
			log.debug("Enter sendMessage------msg :: " + msg);
			MessageCreator creator = new MessageCreator() {
				public Message createMessage(Session session) {
					TextMessage message = null;
					try {
						message = session.createTextMessage();
						message.setStringProperty("MSG_TYPE", "TEXT");
						message.setStringProperty("text", msg);
						log.debug("====Message constructed===========");
						
					} catch (JMSException e) {
						e.printStackTrace();
					}
					return message;
				}
			};
			log.debug("=====Before sending msg=== ");
			jmsTemplate.send(creator);
			
		}

		

}
