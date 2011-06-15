package com.innowhite.whiteboard.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.ConferenceNumbersVO;
import com.innowhite.whiteboard.persistence.beans.ServerVO;
import com.innowhite.whiteboard.webservice.client.ServiceClientImp;

public class ServletUtil {

	private static final Logger log = LoggerFactory.getLogger(ServiceClientImp.class);
	
	public static void setCutomRequestProp(HttpServletRequest request, HttpServletResponse response, boolean prevousSession, ConferenceNumbersVO confNumber, ServerVO serverObj) throws ServletException,
			IOException {

		try {

			String userId = request.getParameter(InnowhiteConstants.USER);
			String courses = request.getParameter(InnowhiteConstants.COURSES);
			String teacher = request.getParameter(InnowhiteConstants.ROOMLEADER);
			String wbSession = request.getParameter(InnowhiteConstants.ROOML_ID);
			String previousSession = "" + prevousSession;
			String orgName = request.getParameter(InnowhiteConstants.PARTNER_ORG);
			String time = request.getParameter("time");
			String date = request.getParameter("date");
			String phoneNum =null;
			int meetingNum=0;
			
			if(confNumber != null){
				phoneNum = confNumber.getPhoneNumber();
				meetingNum = confNumber.getMeetingNumber();
				request.setAttribute(InnowhiteConstants.PHONE_NUM, phoneNum);
				request.setAttribute(InnowhiteConstants.MEETING_NUM, String.valueOf(meetingNum));
			}

			log.debug("userId=" + userId + "    courses=" + courses + "  teacher=" + teacher + "  wbSession=" + wbSession
					+ " previousSession=" + previousSession + " date=" + date + " time=" + time + "  orgName=" + orgName);

			request.setAttribute(InnowhiteConstants.CLIENT_NAME, userId);

			if (teacher != null && teacher.length() > 0) {
				request.setAttribute(InnowhiteConstants.GROUP_EADER, teacher);
			}

			if (courses != null && courses.length() > 0) {
				// request.setAttribute("groupLeader", teacher);
			}

			if (previousSession != null && previousSession.length() > 0) {
				request.setAttribute(InnowhiteConstants.PREVIOUS_SESSION, previousSession);
				if (previousSession.equals("true"))
					request.setAttribute(InnowhiteConstants.GROUP_EADER, "false");
			}

			request.setAttribute(InnowhiteConstants.CLIENT_NAME, userId);
			//request.setAttribute(InnowhiteConstants.GROUP_EADER, "false");
			request.setAttribute(InnowhiteConstants.JOIN_ROOM, wbSession);
			request.setAttribute(InnowhiteConstants.WHITEBOARD_SERVER, serverObj.getServerAddr());
			request.setAttribute(InnowhiteConstants.WHITEBOARD_SERVER_PORT, serverObj.getServerPort());
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
		    	log.error(e.getMessage());
			e.printStackTrace();
		}

	}
}
