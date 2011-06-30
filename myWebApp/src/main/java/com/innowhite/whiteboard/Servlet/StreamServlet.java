package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.ServerVO;
import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.service.LoadBalancerService;
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

		
		
		

		String streamType = request.getParameter(InnowhiteConstants.STREAM_TYPE);
		String roomId = request.getParameter(InnowhiteConstants.ROOML_ID);
		
		
		String serverApp= streamType;

		
		log.debug(" entereing doget: StreamServlet streamType"+streamType+"  roomId "+roomId);	
		
		// send the stream is to server.
		Long subRoomId=null;
		
		
		if(request.getParameter(InnowhiteConstants.REFRESH_CACHE) != null && request.getParameter(InnowhiteConstants.REFRESH_CACHE).equals("true")){
		    LoadBalancerService.forceClearCache();
		}
		
		
		
		ServerVO serverVO = LoadBalancerService.getServerURL(serverApp, null);
		
		if(serverVO == null)
		    return;
		
		if(serverApp != null && ( serverApp.equals("VIDEO") || serverApp.equals("DESKTOP") ))
		    subRoomId = WhiteboardAuthenticationDAOImpl.createSubRoomID(roomId,streamType);
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println(" <response> <returnStatus>");
		out.println("SUCCESS");
		out.println("</returnStatus> <roomId>");
		out.println(subRoomId);
		out.println("</roomId><server>"+serverVO.getServerAddr()+"</server>");
		out.println("<port>");
		out.println(serverVO.getServerPort());
		out.println("</port>");
		
		out.println("</response>");
		
		//out.println("</roomId><server>demo.innowhite.com</server></response>");
		//LoadBalancerService.getServerURL(InnowhiteConstants.VIDEO_APP, null);
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
