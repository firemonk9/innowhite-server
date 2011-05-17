package com.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;
import com.util.Utility;
import com.vireka.business.DocumentManagement;

public class SaveAsImageServlet extends HttpServlet {

	
	private static Logger log = Red5LoggerFactory.getLogger(SaveAsImageServlet.class, "whiteboard");
	/**
	 * Constructor of the object.
	 */
	public SaveAsImageServlet() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
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
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("  Entered do post of  SaveAsImageServlet: "
				+ request.getParameter("TYPE"));
		InputStream inputStream = null;
		boolean png = false;
		boolean inbox = false;
		if (request.getParameter("TYPE") != null
				&& request.getParameter("TYPE").equals("PNG")) {
			png = true;
		}

		log.debug(" request inbox "+request.getParameter("INBOX"));
		if (request.getParameter("INBOX") != null
				&& request.getParameter("INBOX").equals("true")) {
			inbox = true;
			writeImageFile(request);
			response.setContentType("text/html");
			PrintWriter out1 = response.getWriter();
			out1.write("");
			out1.flush();
			out1.close();
			return;

		}

		if (png == false)
			response.setContentType("image/jpeg");
		else
			response.setContentType("image/png");

		inputStream = (request.getInputStream());
		OutputStream out = response.getOutputStream();

		byte[] buffer = new byte[1024];
		// FileOutputStream fop = fop = new FileOutputStream("test.jpg");
		// FileInputStream fis = new FileInputStream("test.jpeg");

//		if (png == false)
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ "My_Whiteboard.jpeg");
//		else
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ "My_Whiteboard.png");

		while (true) {
			int len = inputStream.read(buffer);
			// log.debug(len);
			if (len < 0) {
				break;
			} else {
				out.write(buffer, 0, len);
				// fop.write(buffer,0,len);
			}
		}
		log.debug("done writing to out put stream");
		// fop.close();

		// request.getParameter("HTTP_RAW_POST_DATA");
		out.flush();
		out.close();
	}

	private void writeImageFile(HttpServletRequest request) {
		InputStream inputStream = null;
		byte[] buffer = new byte[1024];

		String ftype = "jpg";
		if (request.getParameter("TYPE") != null
				&& request.getParameter("TYPE").equals("PNG")) {
			ftype = "png";
		}
		
		
		
		String uname = request.getParameter("USER");
		FileOutputStream f = null;
		
		log.debug(" uname  "+uname+" ftype  ");
		
		try {
			
			String fileName = uname+"__"+Utility.getUnique()+"__myPage."+ftype;
			
			String urlPath = Constants.UBUNTU_FOLDER_PATH+fileName;
			urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);
			
			f = new FileOutputStream(new File(urlPath));
			log.debug(" uname  "+uname+" ftype  "+"  filName "+fileName);
			inputStream = (request.getInputStream());
			while (true) {
				int len = inputStream.read(buffer);
				// log.debug(len);
				if (len < 0) {
					break;
				} else {
					f.write(buffer, 0, len);
					// fop.write(buffer,0,len);
				}
			}
			
			UserImagesVO ui = new UserImagesVO();
			ui.setUserName(uname);
			ui.setImageFolderSeq(0);
			ui.setImageName(fileName);
			ui.setImageType(1);
			ui.setImageURL(fileName);
			//UserImagesDAO uid = new UserImagesDAO();
			DocumentManagement.saveDocument(ui);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
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
