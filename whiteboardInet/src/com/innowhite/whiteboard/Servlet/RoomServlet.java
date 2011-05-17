package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.beans.RoomVO;
import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.service.WhiteboardAuthenticatorService;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;
import com.util.Utility;

/**
 * Servlet implementation class RoomStatusServlet
 */
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Red5LoggerFactory.getLogger(RoomServlet.class, "whiteboard");
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoomServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * String checkSum = ""; String queryStringWithoutcheckSum=""; String
		 * orgName = ""; String validServiceStatus = "AUTH_FAILED"; String
		 * roomId = ""; String queryString = request.getQueryString();
		 * queryString = URLDecoder.decode(queryString, "utf-8");
		 * log.debug("request query String "+ queryString); String
		 * queryStringArray[] = queryString.split("&checksum=");
		 * 
		 * if(queryStringArray.length>1){ queryStringWithoutcheckSum
		 * =queryStringArray[0]; } log.debug(queryStringArray[0]);
		 * orgName = request.getParameter("orgName"); checkSum =
		 * request.getParameter("checksum");
		 * log.debug("orgName: "+orgName);
		 * log.debug("checkSum: "+checkSum);
		 * 
		 * validServiceStatus =
		 * WhiteboardAuthenticatorService.validateRequest(queryStringWithoutcheckSum
		 * , orgName, checkSum);
		 * log.debug("validServiceStatus "+validServiceStatus);
		 * 
		 * String roomParam = request.getParameter("rooms"); String rooms[] =
		 * roomParam.split("_"); String res =
		 * WhiteboardAuthenticatorService.checkRoomStatus(rooms);
		 * log.debug(res); response.setContentType("text/xml");
		 * PrintWriter out = response.getWriter(); out.println(res);
		 */

		log.debug(" entereing doget: RoomServlet ");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("methodName");
		String userName = request.getParameter("userName");
		String orgName = request.getParameter("orgName");
		

		String hostURL =null;
		if (orgName.indexOf(Constants.WEB_DELIMITER) > 0) {
			StringTokenizer st = new StringTokenizer(orgName,
					Constants.WEB_DELIMITER);

			orgName = st.nextToken();
			hostURL= st.nextToken();
		}
		
		String course = request.getParameter("course_id");
		String inetID = request.getParameter("lesson_plan_id");
		String roomActive = "ACTIVE";
		String resp = "";
		if (methodName.equals("pastRooms")) {
			roomActive = "INACTIVE";
		}

		if (methodName.equals("activeRooms") || methodName.equals("pastRooms")) {
			WhiteboardAuthenticationDAOImpl wadao = new WhiteboardAuthenticationDAOImpl();
			List<RoomVO> al = null;

			List<String> courseList = Utility.getCourseList(course);

			if (methodName.equals("activeRooms"))
				al = wadao.roomListStatus(roomActive, orgName, courseList);
			else
				al = wadao.roomsRecordedList(roomActive, orgName, courseList);

			StringBuilder sb = new StringBuilder();

			if (al == null) {
				
				sb.append(" <response> <returnStatus>");
				sb.append(InnowhiteConstants.AUTH_FAILED);
				sb.append("</returnStatus> ");
			} else {

				

				sb.append(" <response> <returnStatus>");
				sb.append(InnowhiteConstants.SUCCESS);
				sb.append("</returnStatus> ");
				sb.append("<rooms>");
				for (Iterator iterator = al.iterator(); iterator.hasNext();) {
					RoomVO roomVO = (RoomVO) iterator.next();
					sb.append("<room>");
					sb.append("<roomId>");
					sb.append(roomVO.getRoomId());
					sb.append("</roomId><roomName>");
					sb.append(roomVO.getRoomName());
					sb.append("</roomName><lessonPlanId>");
					sb.append(roomVO.getLessonPlanID());
					sb.append("</lessonPlanId><startData>");
					sb.append(roomVO.getStartDate());
					sb.append("</startData><course_id>");
					sb.append(roomVO.getCourse());
					sb.append("</course_id></room>");

				}
				sb.append("</rooms>");
				sb.append("</response>");
			}
			resp = sb.toString();
			log.debug(resp);
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			out.println("<?xml version='1.0' encoding='UTF-8'?>");
			out.println(resp);
		}

	}

}
