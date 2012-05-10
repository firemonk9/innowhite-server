package com.innowhite.whiteboard.docconversion.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.MessagingException;

import com.innowhite.whiteboard.docconversion.persistence.dao.MessagePersistenceDAO;
import com.innowhite.whiteboard.docconversion.thread.ThumbnailThread;
import com.innowhite.whiteboard.docconversion.vo.DocConversionBean;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;

/**
 * @author tanuja
 * @Date May 02, 2012
 */
public class EmailReceiverService {

	private static final Logger log = LoggerFactory
			.getLogger(EmailReceiverService.class);
	static String separator = System.getProperty("file.separator");

	MessagePersistenceDAO msgPersistenceDao;
	FileTransformatioBean fileTransformBean;
	DocConversionBean docConversionBean;

	public MessagePersistenceDAO getMsgPersistenceDao() {
		return msgPersistenceDao;
	}

	public void setMsgPersistenceDao(MessagePersistenceDAO msgPersistenceDao) {
		this.msgPersistenceDao = msgPersistenceDao;
	}

	public FileTransformatioBean getFileTransformBean() {
		return fileTransformBean;
	}

	public void setFileTransformBean(FileTransformatioBean fileTransformBean) {
		this.fileTransformBean = fileTransformBean;
	}

	public DocConversionBean getDocConversionBean() {
		return docConversionBean;
	}

	public void setDocConversionBean(DocConversionBean docConversionBean) {
		this.docConversionBean = docConversionBean;
	}

	public void receive(MimeMessage mimeMessage) throws MessagingException {
		log.info("Entered receive method...");
		boolean isAttachment = false;
		try {

			Address[] strAdd = mimeMessage.getFrom();
			InternetAddress fromAddress = null;
			String fromEmail = null;
			for (int i = 0; i < strAdd.length; i++) {
				if (strAdd[i] instanceof InternetAddress) {

					fromAddress = (InternetAddress) strAdd[i];
					fromEmail = fromAddress.getAddress();
					break;
				}
			}
			log.debug("fromEmail :::" + fromEmail);

			docConversionBean.setSenderEmail(fromEmail);
			docConversionBean.setUserID("innowhite");  //Get userid dynamically.

			log.debug("mimeMessage.getContentType() :::"
					+ mimeMessage.getContentType());

			if (mimeMessage.getContentType().equalsIgnoreCase("text/plain")) {
				String content = (String) mimeMessage.getContent();
			} else {

				MimeMultipart multipart = (MimeMultipart) mimeMessage
						.getContent();
				isAttachment = handleMultipart(multipart);
				log.debug(" Inside receive....isAttachment :::" + isAttachment);
				if (isAttachment) {
					processRecivedAttachment();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Exiting receive method...");
	}

	private boolean handleMultipart(MimeMultipart mp) {
		log.info(" Entering handleMultipart...");
		int conversionId = -1;
		boolean isAttachment = false;
		try {
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				MimeBodyPart bp = (MimeBodyPart) mp.getBodyPart(i);
				Object content = bp.getContent();
				if (content instanceof String) {
					log.debug("content String:::" + content);
				} else if (content instanceof InputStream) {
					log.debug("content InputStream...fileName:::"
							+ bp.getFileName());

					conversionId = saveLDCToGetConversionId(
							docConversionBean.getUserID(),
							docConversionBean.getSenderEmail());
					File dir = new File(docConversionBean.getDocSharedLoc()
							+ conversionId);
					boolean dirCreated = dir.mkdir();

					String filePath = dir + separator + bp.getFileName();
					FileOutputStream out = new FileOutputStream(new File(
							filePath));
					InputStream in = bp.getInputStream();
					int k;
					while ((k = in.read()) != -1) {
						out.write(k);
					}
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.flush();
						out.close();
					}
					isAttachment = true;
					docConversionBean.setFilePath(filePath);
					docConversionBean.setConversionID(conversionId);

				} else if (content instanceof MimeMessage) {
					MimeMessage message = (MimeMessage) content;
					log.debug("content MimeMessage:::::::" + content);
				} else if (content instanceof MimeMultipart) {
					MimeMultipart mp2 = (MimeMultipart) content;
					log.debug("content MimeMultipart:::::::" + content);
					handleMultipart(mp2);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.info("Exiting handleMultipart method...isAttachment:::"
				+ isAttachment);
		return isAttachment;
	}

	private int saveLDCToGetConversionId(String userID, String fromEmail) {
		log.info("Entering saveLDCToGetConversionId.... ");
		int conversionId = -1;
		try {
			conversionId = msgPersistenceDao.saveLDC(userID, fromEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Exiting saveLDCToGetConversionId method...conversionId:::"
				+ conversionId);
		return conversionId;
	}

	private void processRecivedAttachment() {
		log.info("Entering processRecivedAttachment.... ");
		try {
			ThumbnailThread thumbnailThread = new ThumbnailThread(
					docConversionBean, fileTransformBean);
			thumbnailThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Exiting processRecivedAttachment... ");
	}

}
