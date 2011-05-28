package com.innowhite.whiteboard.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.beans.ConferenceNumbersVO;
import com.innowhite.whiteboard.test.InnowhiteServiceTest;

public class ServletUtil {

	private static Logger log = Red5LoggerFactory.getLogger(ServletUtil.class, InnowhiteConstants.APP_NAME);
	
	public static void setCutomRequestProp(HttpServletRequest request, HttpServletResponse response, boolean prevousSession, ConferenceNumbersVO confNumber) throws ServletException,
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
			
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
