package com.innowhite.whiteboard.service;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.red5.vo.DocConversionBean;
import com.innowhite.transformation.messages.SimpleMessageProducer;
import com.innowhite.whiteboard.persistence.dao.WBDataDAO;

public class BeanService {

	/*
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	SimpleMessageProducer smp = (SimpleMessageProducer)context.getBean("messageProducer");
	DocConversionBean docBean = new DocConversionBean();
	docBean.setUserID("prashanthj");
	docBean.setFilePath("C:/Documents and Settings/Administrator/Desktop/Presentation1.pptx");
	docBean.setConversionID(234);
	docBean.setServiceType("THUMBNAILS");
	smp.sendMessages(docBean);
*/
	
	private static Logger log = Red5LoggerFactory.getLogger(BeanService.class, InnowhiteConstants.APP_NAME);
	
	static ApplicationContext context=null;
	
	public BeanService(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static SimpleMessageProducer getSimpleMessageProducerBean(){
		
		SimpleMessageProducer smp = (SimpleMessageProducer)context.getBean("messageProducer");
		return smp;
		
	}
	

	
	
	
}
