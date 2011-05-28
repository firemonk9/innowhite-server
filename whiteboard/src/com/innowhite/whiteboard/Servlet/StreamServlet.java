package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.service.LessonPlanGetDataService;
import com.innowhite.whiteboard.service.WhiteboardAuthenticatorService;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;

/**
 * Servlet implementation class CreateRoomServlet
 */
public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Red5LoggerFactory.getLogger(StreamServlet.class, InnowhiteConstants.APP_NAME);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StreamServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug(" entereing doget: StreamServlet ");
		
		

		String streamType = request.getParameter(InnowhiteConstants.STREAM_TYPE);
		String roomId = request.getParameter(InnowhiteConstants.ROOML_ID);
		
		int subRoomId = WhiteboardAuthenticationDAOImpl.createSubRoomID(roomId,streamType);
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println(" <response> <returnStatus>");
		out.println("SUCCESS");
		out.println("</returnStatus> <roomId>");
		out.println(subRoomId);
		out.println("</roomId></response>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
