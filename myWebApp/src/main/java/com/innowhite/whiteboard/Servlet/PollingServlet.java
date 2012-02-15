package com.innowhite.whiteboard.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.service.PollingService;

/**
 * Implementation Class for POllingServlet
 * @author Tanuja
 * @Date Feb 15, 2012
 */

public class PollingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PollingServlet.class);
	
	/* To get the pollid for new poll   */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug(" entereing doget: PollingServlet ");
		
		
		String pollIdXml=PollingService.getPollID();
		
		
	}

}
