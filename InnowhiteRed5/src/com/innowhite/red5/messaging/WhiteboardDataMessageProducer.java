package com.innowhite.red5.messaging;

import java.util.ArrayList;

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
import com.innowhite.red5.vo.PointVO;
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

	public void sendMessages(final ShapeEventsVO docBean, final String roomName)
			throws JmsException {

		// final String payload = "Message [" + i + "] sent at: "+ new Date();

		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();

				message.setString("roomName", roomName);
				message.setString("actionType", docBean.getActionType());
				message.setString("objName", docBean.getObjName());
				message.setString("objType", docBean.getObjType());
				message.setFloat("xpos", docBean.getXpos());
				message.setFloat("ypos", docBean.getYpos());
				message.setFloat("x1", docBean.getX1());
				message.setFloat("y1", docBean.getY1());
				message.setFloat("x2", docBean.getX2());
				message.setFloat("y2", docBean.getY2());
				message.setFloat("shpHeight", docBean.getShpHeight());
				message.setFloat("shpWidth", docBean.getShpWidth());

				message.setFloat("mainscalex", docBean.getMainscalex());
				message.setFloat("mainscaley", docBean.getMainscaley());
				message.setString("bordercolor", docBean.getBordercolor());
				message.setString("fillcolor", docBean.getFillcolor());
				message.setString("secondName", docBean.getSecondName());

				message.setInt("rotation", docBean.getRotation());
				message.setLong("objDate", docBean.getObjDate());

				message.setInt("penthickness", docBean.getPenthickness());
				message.setInt("versionNumber", docBean.getVersionNumber());

				message.setString("elems", getString(docBean.getElems()));

				message.setString("sprText", docBean.getSprText());
				message.setString("txtType", docBean.getTxtType());
				message.setString("txtFont", docBean.getTxtFont());
				message.setString("imageURL", docBean.getImageURL());

				message.setInt("seq", docBean.getSeq());
				message.setInt("secondSeq", docBean.getSecondSeq());
				message.setInt("wbNumber", docBean.getWbNumber());

				message.setString("userId", docBean.getUserId());

				//
				// message.setString("userID", docBean.getUserID());
				// message.setInt("conversionID", docBean.getConversionID());
				// message.setString("filePath", docBean.getFilePath());
				// message.setString("serviceType", docBean.getServiceType());
				message.setIntProperty("messageCount", 1);
				// message.setString("serverFilePath",
				// docBean.getServerFilePath());

				// LOG.info("Sending message number [" + index + "]");
				return message;
			}

			private String getString(ArrayList elems) {

				String COMMA = ",";
				String DELIMIT = "#";
				ArrayList<PointVO> arr = elems;
				if (arr != null && arr.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (PointVO a : arr) {

						sb.append(a.getX()+COMMA+a.getY()+DELIMIT);
					}
					return sb.toString();
				} else
					return "";
			}
		});

	}

}
