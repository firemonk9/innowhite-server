package com.innowhite.whiteboard.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class WBDeployer
 */
public class WBDeployer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	

	private static final String TMP_DIR_PATH = "/tmp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH =".";
	//private File destinationDir;
 
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if(!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
		File f1 = new File("/");
		System.err.println("  abs path is :: "+f1.getAbsolutePath());
		String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		//destinationDir = new File(DESTINATION_DIR_PATH);
//		if(!destinationDir.isDirectory()) {
//			throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
//		}
 
	}
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	    response.setContentType("text/plain");
	    out.println("<h1>Servlet File Upload Example using Commons File Upload</h1>");
	    out.println();
	    String userName = null;
 
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		/*
		 *Set the size threshold, above which content will be stored on disk.
		 */
		fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		/*
		 * Set the temporary directory to store the uploaded files of size above threshold.
		 */
		fileItemFactory.setRepository(tmpDir);
 
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			/*
			 * Parse the request
			 */
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				/*
				 * Handle Form Fields.
				 */
				if(item.isFormField()) {
					out.println("File Name = "+item.getFieldName()+", Value = "+item.getString());
					userName = item.getString();
					System.out.println(" if block File Name = "+item.getFieldName()+", Value = "+item.getString());
				} else {
					//Handle Uploaded files.
					out.println("Field Name = "+item.getFieldName()+
						", File Name = "+item.getName()+
						", Content type = "+item.getContentType()+
						", File Size = "+item.getSize());
					/*
					 * Write file to the ultimate location.
					 */
					
					ServletContext servletContext = getServletContext();
					String contextPath = servletContext.getRealPath(File.separator);
					
					File file = new File(contextPath,"temp"+item.getName());
					item.write(file);
					
					
					
					file.renameTo(new File(contextPath, userName+".swf"));
					System.err.println(" the dest path :: "+file.getAbsolutePath());
					
				}
				
			
			
				
				out.close();
			}
		}catch(FileUploadException ex) {
			log("Error encountered while parsing the request",ex);
		} catch(Exception ex) {
			log("Error encountered while uploading file",ex);
		}
	}
}
