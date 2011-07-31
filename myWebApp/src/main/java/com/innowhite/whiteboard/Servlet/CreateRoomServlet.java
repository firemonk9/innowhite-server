package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.service.LessonPlanGetDataService;
import com.innowhite.whiteboard.service.WhiteboardAuthenticatorService;
import com.innowhite.whiteboard.test.InnowhiteServiceTest;
import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * Servlet implementation class CreateRoomServlet
 */
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(InnowhiteServiceTest.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateRoomServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug(" entereing doget: CreateRoomServlet ");

		
		
		
		String checkSum = "";
		// String queryStringWithoutcheckSum = null;
		String parentOrg = "";
		String validServiceStatus = InnowhiteConstants.AUTH_FAILED;
		String roomId = "";
		String roomName = "";
		String inetLessonID = "";
		String queryString = request.getQueryString();
		log.debug("request query String " + queryString);
		String queryStringArray[] = queryString.split("&checksum=");

		log.debug(queryStringArray[0]);

		parentOrg = request.getParameter(InnowhiteConstants.PARENT_ORG);
		
		
		String hostURL =null;
//		if (parentOrg.indexOf(Constants.WEB_DELIMITER) > 0) {
//			StringTokenizer st = new StringTokenizer(parentOrg,
//					Constants.WEB_DELIMITER);
//
//			parentOrg = st.nextToken();
//			hostURL= st.nextToken();
//			
//			if(parentOrg != null )
//				parentOrg = parentOrg.trim();
//			
//			if(hostURL != null )
//				hostURL = hostURL.trim();
//			
//		}

		checkSum = request.getParameter(InnowhiteConstants.CHECKSUM);
		roomName = request.getParameter("roomName");
		String record = request.getParameter("record");
		String courseId = request.getParameter("course_id");
		inetLessonID = request.getParameter("lesson_plan_id");
		String view = request.getParameter("view");
	//	String hostName = request.getServerName();

		log.debug("orgName: " + parentOrg);
		log.debug("clientURL: " + hostURL);
		
		log.debug("checkSum: " + checkSum);
		log.debug("roomName: " + roomName);
		log.debug("record: " + record);
		log.debug("course: " + courseId);
		log.debug("inetLessonID: " + inetLessonID);
		log.debug("view: " + view);
	//	log.debug("sb: " + hostName.toString());

		// validServiceStatus =
		// WhiteboardAuthenticatorService.validateRequest(queryStringWithoutcheckSum,
		// orgName, checkSum);

		// if(validServiceStatus.equals(InnowhiteConstants.SUCCESS))
		// {
		roomId = WhiteboardAuthenticatorService.createRoom(parentOrg, roomName,
				record, courseId, inetLessonID, view);
		// }
		if (roomId != null && roomId.length() > 0) {
			validServiceStatus = InnowhiteConstants.SUCCESS;
		}

		if (validServiceStatus == InnowhiteConstants.SUCCESS) {
			
			// Below code is only for lesson plan integration ex : inet
			if(inetLessonID != null){
				LessonPlanGetDataService.cacheAllData(courseId, inetLessonID,
						hostURL);
			}
		}
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println(" <response> <returnStatus>");
		out.println(validServiceStatus);
		out.println("</returnStatus> <roomId>");
		out.println(roomId);
		out.println("</roomId><roomName>");
		out.println(roomName);
		out.println("</roomName></response>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
