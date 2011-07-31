package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innowhite.whiteboard.service.RoomService;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * Servlet implementation class LockRoomServlet
 */
public class LockRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LockRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomName = request.getParameter(InnowhiteConstants.ROOM_ID);
		String lockRoom = request.getParameter(InnowhiteConstants.LOCK_ROOM);
		if(roomName != null && lockRoom != null){
		    
		    RoomService.lockRoom(roomName, lockRoom);
		    
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response)
	}

}
