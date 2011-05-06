package com.innowhite.whiteboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.transformation.messages.SimpleMessageProducer;

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
	
	private static final Logger log = LoggerFactory.getLogger(BeanService.class);
	
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
