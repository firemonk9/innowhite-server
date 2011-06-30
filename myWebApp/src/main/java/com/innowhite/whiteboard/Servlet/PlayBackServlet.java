package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.service.PlayBackPlayListService;

/**
 * Servlet implementation class PlayBackServlet
 */
public class PlayBackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayBackServlet() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.debug(" entereing doget: JoinRoomServlet ");
	String roomId = request.getParameter("room_id");
	String s = PlayBackPlayListService.getPlayListXML(roomId);
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	out.println("<?xml version='1.0' encoding='UTF-8'?>");
	out.println(s);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
