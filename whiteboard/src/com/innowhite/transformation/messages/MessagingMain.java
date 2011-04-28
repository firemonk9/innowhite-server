/**
 * 
 */
package com.innowhite.transformation.messages;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.red5.vo.DocConversionBean;
import com.innowhite.webservice.client.HttpClientTutorial;

/**
 * @author prashanthj
 *
 */
public class MessagingMain {

	private static Logger log = Red5LoggerFactory.getLogger(MessagingMain.class, "whiteboard");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SimpleMessageProducer smp = (SimpleMessageProducer)context.getBean("messageProducer");
		DocConversionBean docBean = new DocConversionBean();
		docBean.setUserID("prashanthj");
		docBean.setFilePath("C:/Documents and Settings/Administrator/Desktop/Presentation1.pptx");
		docBean.setConversionID(234);
		docBean.setServiceType("THUMBNAILS");
		smp.sendMessages(docBean);
		

	}

}
