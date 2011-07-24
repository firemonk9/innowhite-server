package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innowhite.whiteboard.service.SessionDetailService;
import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * Servlet implementation class SessionDetailService
 */
public class SessionDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionDetail() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String roomId = request.getParameter(InnowhiteConstants.ROOM_ID);
	String xml=null;
	if(roomId != null)
	    xml=SessionDetailService.getSessionDetail(roomId);
	
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	out.println(xml);
	
	//return xml;
	
    }

}
