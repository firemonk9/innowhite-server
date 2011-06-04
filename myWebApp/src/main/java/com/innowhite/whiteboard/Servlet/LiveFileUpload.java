package com.innowhite.whiteboard.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.business.ProcessConversion;
import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.UploadImages;
import com.innowhite.whiteboard.util.Utility;

public class LiveFileUpload extends HttpServlet {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * Constructor of the object.
	 */
	public LiveFileUpload() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
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

		log.debug(" Enter do post LiveFileUpload");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()
		
		String urlPath = Constants.UBUNTU_FOLDER_PATH;
		urlPath = urlPath.replaceAll(Constants.APP_NAME, request.getContextPath().substring(1));

		
		factory.setRepository(new File(urlPath));

		//ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		//upload.setSizeMax(-1);
		File f = null;
		// if image is graph
		String fileNameTe= request.getParameter(Constants.FILE_NAME);
		String fileData = request.getParameter(Constants.IMAGE_DATE);
		log.debug("file name ::::   "
				+ fileNameTe);
		
	
		
		String xml=null;
		if (fileData != null
				&& fileNameTe != null) {
			// save base 64 image
			fileNameTe = Utility.removeSpace(fileNameTe);
			f = UploadImages.createUploadedFile(fileData, fileNameTe);
			//ProcessConversion.createThumbNail(f,fileNameTe);	
			
			log.debug("");
			if (Utility.allowedFiles(fileNameTe) == true)
			{
				log.debug("enter document conversion"+request);
				StringBuffer serverInfo = request.getRequestURL();	
				log.debug("enter document conversion"+serverInfo);
				xml = ProcessConversion.convertDocument(f.getAbsolutePath(), "live","",f.getName(),serverInfo.toString(),false,f.getAbsolutePath(),false);
				
			}
			
		}
	
		response.setContentType("application/xml");
		
		PrintWriter out = response.getWriter();
		log.debug("  "+xml);
		out.write(xml);
		out.flush();
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
