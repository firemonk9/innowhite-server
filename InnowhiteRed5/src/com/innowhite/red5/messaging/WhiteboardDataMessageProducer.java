package com.innowhite.red5.messaging;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.innowhite.red5.vo.DocConversionBean;
import com.innowhite.red5.vo.ShapeEventsVO;

public class WhiteboardDataMessageProducer {

	private static final Logger log = LoggerFactory
			.getLogger(WhiteboardDataMessageProducer.class);

	protected JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	protected int numberOfMessages = 1;

	String txt = null;

	public void sendMessages( final ShapeEventsVO docBean, String roomName)
			throws JmsException {

		// final String payload = "Message [" + i + "] sent at: "+ new Date();

		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
//				message.setString("userID", docBean.getUserID());
//				message.setInt("conversionID", docBean.getConversionID());
//				message.setString("filePath", docBean.getFilePath());
//				message.setString("serviceType", docBean.getServiceType());
				message.setIntProperty("messageCount", 1);
				//message.setString("serverFilePath", docBean.getServerFilePath());

				// LOG.info("Sending message number [" + index + "]");
				return message;
			}
		});

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
