package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.MeetingMinutesVo;
import com.innowhite.whiteboard.service.MeetingMinutesService;

/** To implement MeetingMinutesServlet class
 * @author tanuja
 * @Date 01-23-2012
 */

public class MeetingMinutesServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
	    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    	log.info("entered Do Post to create new meetingminutes");
	    	int userId=Integer.parseInt(request.getParameter("user_id"));
	    	String strDesc=request.getParameter("description");		   
	    	String roomId=request.getParameter("room_id");
	    	String reqAction=request.getParameter("reqaction");
	    	int meetMinId=Integer.parseInt(request.getParameter("meetmin_id"));
	    	
	    	if(reqAction!=null && reqAction!="" && reqAction.equals("insert")){
		    	int returnMeetMinId=0;
		    	returnMeetMinId=MeetingMinutesService.insertMeetingMinutes(roomId,userId,strDesc);
		    	
	    	}else if(reqAction!=null && reqAction!="" && reqAction.equals("select")){
	    		List<MeetingMinutesVo> metMinList = null;
	    		metMinList = MeetingMinutesService.getAllMeetingMinutes(roomId);
	    		
	        }else if(reqAction!=null && reqAction!="" && reqAction.equals("delete")){
	        	MeetingMinutesService.deleteMeetingMinute(meetMinId,roomId);
	        		
	        }else if(reqAction!=null && reqAction!="" && reqAction.equals("update")){
	        	MeetingMinutesService.updateMeetingMinute(meetMinId,roomId,strDesc);
	        }
	    }

}

