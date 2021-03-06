package com.innowhite.red5.messaging;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.innowhite.red5.util.InnowhiteConstants;

public class SimpleMessageProducer {

    private static Logger log = Red5LoggerFactory.getLogger(
	    SimpleMessageProducer.class, InnowhiteConstants.APP_NAME);


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

    // protected int numberOfMessages = 1;

    // String txt=null;

    public synchronized void sendMessage(final String msg, final String msgType) {
	// txt = msg;
	// log.debug("sending room status msg");
	// MessageCreator creator = new MessageCreator() {
	// public Message createMessage(Session session) {
	// TextMessage message = null;
	// try {
	// message = session.createTextMessage();
	// message.setStringProperty("text", msg);
	// } catch (JMSException e) {
	// e.printStackTrace();
	// }
	// return message;
	// }
	// };
	// jmsTemplate.send(creator);

	MessageCreator creator = new MessageCreator() {
	    public Message createMessage(Session session) {
		TextMessage message = null;
		try {
		    message = session.createTextMessage();
		    message.setStringProperty("MSG_TYPE", msgType);
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
