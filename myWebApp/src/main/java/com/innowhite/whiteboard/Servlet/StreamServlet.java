package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * This servlet is responsible for returning the stream id for screen share/ and also video broadcast..
 * 
 * To DO : Need to get the stream id and server info based on server load.
 */
public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
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
		
		Long subRoomId = WhiteboardAuthenticationDAOImpl.createSubRoomID(roomId,streamType);
		
		
		// send the stream is to server.
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println(" <response> <returnStatus>");
		out.println("SUCCESS");
		out.println("</returnStatus> <roomId>");
		out.println(subRoomId);
		out.println("</roomId><server>main.innowhite.com</server></response>");

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
