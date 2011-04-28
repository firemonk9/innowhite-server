package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.util.Constants;

public class UserImagesList extends HttpServlet {
	private static Logger log = Red5LoggerFactory.getLogger(UserImagesList.class, "whiteboard");
	/**
	 * Constructor of the object.
	 */
	public UserImagesList() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession mysession = request.getSession(false);
		if(mysession != null)
			log.debug(" session id is   :"+mysession.getId());
		
		log.debug(" user Image List entered do get  ");	
		
		
		response.setContentType("application/xml");
		PrintWriter out = response.getWriter();
		
		
		StringBuffer serverInfo = request.getRequestURL();
		
		String s = request.getRequestURI();
		
		String conversionId = request.getParameter("conversionId");
		log.debug(serverInfo.toString()+"  s  "+s+"  getRequestURL "+request.getRequestURL()+"  contextPAth "+request.getContextPath()+"  conversionId "+conversionId);
		
		String userName = request.getParameter("userName");
		
		
		
		//log.debug("test 123 UserImagesList");
		
		String xmlData=null;
		if(conversionId == null)
			 xmlData = com.vireka.service.ImageService.getImageXMLNew(userName,serverInfo.toString(),null);
		else
			 xmlData  = com.vireka.service.ImageService.getSingleXMLNewByConversionId(userName,conversionId,serverInfo.toString());
		
		String contextName = request.getContextPath();
		contextName = contextName.substring(1);
		if(contextName != null && xmlData != null)
		{
			xmlData = xmlData.replaceAll(Constants.APP_NAME, contextName);
			out.write(xmlData);
		}
		else{
			
			
		}
		//String xmlData = ImageDAO.getImageXMLNew(userName,serverInfo.toString());
	//	ProcessConversion.convertDocument("");
		
		//log.debug(xmlData);
		
		
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
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
