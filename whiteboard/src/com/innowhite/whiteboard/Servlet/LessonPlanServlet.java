package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.service.LessonPlanGetDataService;
import com.innowhite.whiteboard.service.WhiteboardAuthenticatorService;
import com.util.Constants;

public class LessonPlanServlet extends HttpServlet {

	
	private static Logger log = Red5LoggerFactory.getLogger(LessonPlanServlet.class, InnowhiteConstants.APP_NAME);
	/**
	 * Constructor of the object.
	 */
	public LessonPlanServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				
		log.debug(" Entered LessonPlanServlet  ");	
		
		
		response.setContentType("application/xml");
		PrintWriter out = response.getWriter();
		
		
		StringBuffer serverInfo = request.getRequestURL();
		
		String s = request.getRequestURI();
		log.debug(request.getRequestURI()+"  s  "+s+"  getRequestURL "+request.getRequestURL()+"  contextPAth "+request.getContextPath());
		
		//String userName = request.getParameter("userName");
		String courseId = request.getParameter("course_id");
		String lessonPlanId = request.getParameter("lesson_plan_id");
		String orgName = request.getParameter("orgName");
		
		
		log.debug(" courseId:  "+courseId+" lessonPlanId:  "+lessonPlanId);	
		
		//log.debug("test 123 UserImagesList");
		String xmlData = LessonPlanGetDataService.getInnowhiteXML(courseId,lessonPlanId,request.getRequestURL().toString(),orgName);
		
		String contextName = request.getContextPath();
		contextName = contextName.substring(1);
		xmlData = xmlData.replaceAll(Constants.APP_NAME, contextName);
		
		//String xmlData = ImageDAO.getImageXMLNew(userName,serverInfo.toString());
	//	ProcessConversion.convertDocument("");
		
		//log.debug(xmlData);
		
		out.write(xmlData);
		//log.debug(""+xmlData);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
