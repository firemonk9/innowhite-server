package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.service.WhiteboardAuthenticatorService;
import com.innowhite.whiteboard.util.InnowhiteConstants;

public class DeleteSession extends HttpServlet {

	
	private static Logger log = Red5LoggerFactory.getLogger(DeleteSession.class, "whiteboard");
	/**
	 * Constructor of the object.
	 */
	public DeleteSession() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug(" entereing doget: DeleteSession ");
		String checkSum = "";
		String queryStringWithoutcheckSum = "";
		String orgName = "";
		String validServiceStatus = InnowhiteConstants.AUTH_FAILED;
		String roomId = "";
		String roomName = "";
		String inetLessonID = "";
		String queryString = request.getQueryString();
		log.debug("request query String " + queryString);
		String queryStringArray[] = queryString.split("&checksum=");

		if (queryStringArray.length > 1) {
			queryStringWithoutcheckSum = queryStringArray[0];
		}
		log.debug(queryStringArray[0]);
		orgName = request.getParameter(InnowhiteConstants.ORG_NAME);
		checkSum = request.getParameter(InnowhiteConstants.CHECKSUM);
		roomName = request.getParameter("roomName");
		String record = request.getParameter("record");
		String clientName = request.getParameter("client_name");
		String course = request.getParameter("course_id");
		inetLessonID = request.getParameter("lesson_plan_id");
		log.debug("orgName: " + orgName);
		log.debug("checkSum: " + checkSum);
		log.debug("roomName: " + roomName);
		log.debug("record: " + record);
		log.debug("course: " + course);
		log.debug("inetLessonID: " + inetLessonID);

		// validServiceStatus =
		// WhiteboardAuthenticatorService.validateRequest(queryStringWithoutcheckSum,
		// orgName, checkSum);

		// if(validServiceStatus.equals(InnowhiteConstants.SUCCESS))
		// {
		int number = 0;
		WhiteboardAuthenticationDAOImpl wadao = new WhiteboardAuthenticationDAOImpl();
		number = wadao.deleteRooms(orgName, clientName, course, inetLessonID);// .(orgName,
																				// roomName,
																				// record,
																				// course,
																				// inetLessonID);
		// }
		if (number > 0) {
			validServiceStatus = InnowhiteConstants.SUCCESS;
		}
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println(" <response> <returnStatus>");
		out.println(validServiceStatus);
		out.println("</returnStatus> <rooms_deleted>");
		out.println(number);
		out.println("</rooms_deleted></response>");

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
