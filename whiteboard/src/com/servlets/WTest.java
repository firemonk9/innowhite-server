package com.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.util.Constants;


public class WTest extends HttpServlet {
	private static Logger log = Red5LoggerFactory.getLogger(WTest.class, "whiteboard");
	/**
	 * Constructor of the object.
	 */
	public WTest() {
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

		log.debug(" Enter do post");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()
		factory.setRepository(new File("/Applications/Red5/tmp"));

		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		upload.setSizeMax(1000000);

		// if image is graph
		log.debug("file name ::::   "+request.getParameter(Constants.FILE_NAME));
		if (request.getParameter(Constants.IMAGE_DATE) != null
				&& request.getParameter(Constants.FILE_NAME) != null) {
			// save base 64 image
			saveGraphImage(request);

		} else {
			List fileItems = null;
			try {
				fileItems = upload.parseRequest(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			log.debug("  size is   " + fileItems.size());

			Iterator i = fileItems.iterator();
			String comment = ((FileItem) i.next()).getString();
			FileItem fi = (FileItem) i.next();
			// filename on the client
			String fileName = fi.getName();

			//ConvertDocument cd = new ConvertDocument();
			
			
			// ...
			// write the file
			try {
				File f = new File("/Applications/Red5/tmp", fileName);
				fi.write(f);
//				UserImages obj = new UserImages("test", f.getAbsolutePath(),
//						"desc");
				String[] str= new String[2];
				str[0]=f.getAbsolutePath();
				String s = f.getAbsolutePath();
				String ss = s.substring(0, s.indexOf('.'));
				str[1] = ss+".pdf";
				//cd.main(str);
				
				// UserImagesDAO dao = new UserImagesDAO();
				//        	
				// Session session = getSessionFactory().openSession();
				// Transaction tx = session.beginTransaction();
				// Message message = new Message("Hello World");
				// session.save(message);
				// tx.commit();
				// session.close();

				// dao.save(obj);
				log.debug(f.getAbsolutePath());

			} catch (Exception e) {

				e.printStackTrace();
			}finally{
				
			}
		}
		PrintWriter out = response.getWriter();
		out.write("");
		out.flush();
	}

	private void saveGraphImage(HttpServletRequest request) {

		String base64str = request.getParameter(Constants.IMAGE_DATE);
		Base64 b64 = new Base64();
		byte[] decoded = b64.decode(base64str.getBytes());
		String fileName = request.getParameter(Constants.FILE_NAME);
		FileOutputStream fop=null;
		try {
			File f = new File("/Applications/Red5/webapps/whiteboard/data", fileName);
			fop = new FileOutputStream(f);
			if (f.exists())
				fop.write(decoded);

			log.debug(f.getAbsolutePath());

		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			if(fop !=null)
				try {
					fop.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
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
