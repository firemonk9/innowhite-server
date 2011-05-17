package com.innowhite.transformation.messages;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.webservice.client.HttpClientTutorial;
import com.vo.DocConversionBean;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Red5LoggerFactory.getLogger(TestServlet.class, "whiteboard");
	
    /**
     * Default constructor. 
     */
    public TestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SimpleMessageProducer smp = (SimpleMessageProducer)context.getBean("messageProducer");
		DocConversionBean docBean = new DocConversionBean();
		docBean.setUserID("prashanthj");
		docBean.setFilePath("C:/Documents and Settings/Administrator/Desktop/Presentation1.pptx");
		docBean.setConversionID(234);
		docBean.setServiceType("THUMBNAILS");
		smp.sendMessages(docBean);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
