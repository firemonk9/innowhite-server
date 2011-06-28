package com.innowhite.PlaybackApp.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.innowhite.PlaybackApp.service.BasicThreadCreator;

/**
 * Servlet implementation class CreatePlaybackSession
 */
public class CreatePlaybackSession extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ApplicationContext context = null;

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

	super.init(config);
	ServletContext sc = getServletContext();

	context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
	System.err.println(" ... " + context);
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePlaybackSession() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String roomId = request.getParameter("ROOM_ID");
	if (roomId != null && context != null) {

	    BasicThreadCreator basicThreadCreator = (BasicThreadCreator) context.getBean("basicThreadCreator");
	    basicThreadCreator.startJob(roomId);
	}

	// TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
