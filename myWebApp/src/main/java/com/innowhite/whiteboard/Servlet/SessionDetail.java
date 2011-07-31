package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.service.SessionDetailService;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * Servlet implementation class SessionDetailService
 */
public class SessionDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SessionDetail.class);

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

	log.debug("entered doGet of SessionDetail");
	String xml = null;
	try {
	    String roomId = request.getParameter(InnowhiteConstants.ROOM_ID);
	    log.debug("roomId : "+roomId);
	    if (roomId != null)
		xml = SessionDetailService.getSessionDetail(roomId);

	    response.setContentType("text/xml");
	} catch (Exception e) {
	    log.error(e.getMessage());
	    e.printStackTrace();
	}
	PrintWriter out = response.getWriter();
	out.println(xml);
	out.close();
	// return xml;

    }

}
