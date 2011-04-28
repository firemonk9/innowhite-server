package com.innowhite.whiteboard.docconversion.messages;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;

import com.innowhite.whiteboard.docconversion.thread.SWFThread;
import com.innowhite.whiteboard.docconversion.thread.ThumbnailThread;
import com.innowhite.whiteboard.docconversion.vo.DocConversionBean;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;

public class ConversionMessageListener implements MessageListener {

	private static final Logger LOG = org.slf4j.LoggerFactory
			.getLogger(ConversionMessageListener.class);

	public static Hashtable<Integer, Boolean> hTable = new Hashtable<Integer, Boolean>();

	public String getThumbsCommand() {
		return thumbsCommand;
	}

	public void setThumbsCommand(String thumbsCommand) {
		LOG.info("setting value thumbsCommand ::"+thumbsCommand);
		this.thumbsCommand = thumbsCommand;
	}

	public String getActualFileCommand() {
		return actualFileCommand;
	}

	public void setActualFileCommand(String actualFileCommand) {
		this.actualFileCommand = actualFileCommand;
	}

	private String thumbsCommand;
	private String actualFileCommand;

	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		FileTransformatioBean fileTransBean = new FileTransformatioBean();
		try {
			DocConversionBean docBean = new DocConversionBean();
			docBean.setUserID(mapMessage.getString("userID"));
			docBean.setConversionID(mapMessage.getInt("conversionID"));
			docBean.setFilePath(mapMessage.getString("filePath"));
			docBean.setServiceType(mapMessage.getString("serviceType"));
			docBean.setServerFilePath(mapMessage.getString("serverFilePath"));

			LOG.info("Consumed message: " + docBean);

			hTable.put(docBean.getConversionID(), false);
			
			fileTransBean.setActualFileCommand(actualFileCommand);
			fileTransBean.setThumbsCommand(thumbsCommand);
			
			
			ThumbnailThread thumbnailThread = new ThumbnailThread(docBean,
					fileTransBean);
			thumbnailThread.start();

			SWFThread thread = new SWFThread(docBean, fileTransBean);
			thread.start();

		} catch (JMSException e) {
			LOG.error("jms exception", e.fillInStackTrace());
		}
	}

}
