package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.beans.ClientMediaVO;
import com.innowhite.whiteboard.persistence.dao.ClientMediaDAO;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;
import com.vireka.business.ProcessConversion;

public class LessonDocumentXMLServlet extends HttpServlet {

	private static Logger log = Red5LoggerFactory.getLogger(LessonDocumentXMLServlet.class, InnowhiteConstants.APP_NAME);
	/**
	 * Constructor of the object.
	 */
	public LessonDocumentXMLServlet() {
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

		
		String documentId= request.getParameter(Constants.CONTENT_ID);
		String orgName = request.getParameter(Constants.ORGNAME);
		response.setContentType("application/xml");
		PrintWriter out = response.getWriter();
		//StringBuffer serverInfo = request.getRequestURL();
		StringBuffer serverInfo = request.getRequestURL();	
		
		
		String xml=null;
		try {
			
			log.debug(" checking if the  xml is in the cache  ");
			xml = ClientMediaDAO.getDocumentXML(documentId,orgName);
			
			
			if(xml == null )
			{	
				xml = ProcessConversion.lessonDocumentConverter( documentId,serverInfo.toString(),orgName);
				
				if(xml != null ){
					
					String source = ClientMediaDAO.getContentSource(documentId,orgName);
				
					ClientMediaVO clientMediaVO = new ClientMediaVO();
					clientMediaVO.setContentId(documentId);
					clientMediaVO.setContentSource(source);
					clientMediaVO.setContentType(InnowhiteConstants.DOCUMENT_TYPE);
					clientMediaVO.setDocumentXML(xml);
					clientMediaVO.setOrgName(orgName);
					
					ClientMediaDAO.saveMedia(clientMediaVO);
					//ClientMediaDAO.saveMedia(documentId,xml,source);
					
					//ProcessConversion.lessonDocumentConverter( clientMediaVO.getContentId(),null);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		if(xml != null)
			out.write(xml);
		
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
