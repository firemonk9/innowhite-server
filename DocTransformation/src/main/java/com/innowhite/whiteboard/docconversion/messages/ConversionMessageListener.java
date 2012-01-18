package com.innowhite.whiteboard.docconversion.messages;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.thread.PDFThread;
import com.innowhite.whiteboard.docconversion.thread.PDFThumbnailThread;
import com.innowhite.whiteboard.docconversion.thread.SWFThread;
import com.innowhite.whiteboard.docconversion.thread.ThumbnailThread;
import com.innowhite.whiteboard.docconversion.vo.DocConversionBean;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;

public class ConversionMessageListener implements MessageListener {

    private static final Logger log = Logger.getLogger(ConversionMessageListener.class);
    // private static final Logger log =
    // LoggerFactory.getLogger(ConversionMessageListener.class);
    public static Hashtable<Integer, Boolean> hTable = new Hashtable<Integer, Boolean>();

    private String thumbsCommand;
    private String imgActualFileCommand;
    private String swfActualFileCommand;
    private String convertType;

    private String pdfToThumbnail;
    private String pdfToSwf;

    public String getPdfToThumbnail() {
	return pdfToThumbnail;
    }

    public void setPdfToThumbnail(String pdfToThumbnail) {
	this.pdfToThumbnail = pdfToThumbnail;
    }

    public String getPdfToSwf() {
	return pdfToSwf;
    }

    public void setPdfToSwf(String pdfToSwf) {
	this.pdfToSwf = pdfToSwf;
    }

    public String getThumbsCommand() {
	return thumbsCommand;
    }

    public void setThumbsCommand(String thumbsCommand) {
	log.info("setting value thumbsCommand ::" + thumbsCommand);
	this.thumbsCommand = thumbsCommand;
    }

    public String getImgActualFileCommand() {
	return imgActualFileCommand;
    }

    public void setImgActualFileCommand(String imgActualFileCommand) {
	this.imgActualFileCommand = imgActualFileCommand;
    }

    public String getSwfActualFileCommand() {
	return swfActualFileCommand;
    }

    public void setSwfActualFileCommand(String swfActualFileCommand) {
	this.swfActualFileCommand = swfActualFileCommand;
    }

    public String getConvertType() {
	return convertType;
    }

    public void setConvertType(String convertType) {
	this.convertType = convertType;
    }

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

	    log.info("Consumed message: " + docBean);

	    hTable.put(docBean.getConversionID(), false);

	    log.info("convertType: " + convertType);
	    //f (convertType != null && convertType.equals("swf")) {
//		fileTransBean.setActualFileCommand(swfActualFileCommand);
//		fileTransBean.setSwf(true);
//	    } else {
//		fileTransBean.setActualFileCommand(imgActualFileCommand);
//		fileTransBean.setSwf(false);
//	    }
	    fileTransBean.setThumbsCommand(thumbsCommand);

	    ThumbnailThread thumbnailThread = new ThumbnailThread(docBean, fileTransBean);
	    thumbnailThread.start();

//	    SWFThread thread = new SWFThread(docBean, fileTransBean);
//	    thread.start();
	    // }

	} catch (JMSException e) {
	    log.error("jms exception", e.fillInStackTrace());
	} catch (Exception e) {
	    log.error(" exception", e.fillInStackTrace());
	}
    }

}
