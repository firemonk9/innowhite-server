package com.innowhite.whiteboard.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innowhite.whiteboard.persistence.dao.SessionRecordingDAO;

/**
 * Servlet implementation class SessionRecording
 */
public class SessionRecording extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionRecording() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	ServletInputStream sis = null;
	try {
	    // sis = request.getInputStream();
	    // maybe I should be using a BufferedInputStream
	    // instead of the InputStream directly?
	    String roomId = request.getParameter("roomId");
	    String recordStatus = request.getParameter("RECORD_STATUS");
	    if (recordStatus != null && recordStatus.equals("RECORD_START")) {
		SessionRecordingDAO.startSessionRecording(roomId);
	    } else {
		SessionRecordingDAO.endSessionRecording(roomId);
	    }

	    // String xmlReply = "<Reply><Message>"+msg+"</Message></Reply>";
	    // byte[] data = xmlReply.getBytes("UTF-8");
	    ServletOutputStream sos = response.getOutputStream();
	    // sos.write(data, 0,data.length);
	    sos.flush();
	    sos.close();

	} finally {
	    if (sis != null)
		sis.close();
	}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
