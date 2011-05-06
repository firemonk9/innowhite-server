/**
 * 
 */
package com.innowhite.transformation.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.vo.DocConversionBean;

/**
 * @author prashanthj
 *
 */
public class MessagingMain {

	private static final Logger log = LoggerFactory.getLogger(MessagingMain.class);
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
	//	smp.sendMessages(docBean);
		

	}

}
