package com.innowhite.whiteboard.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.persistence.dao.ClientMediaDAO;
import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;
import com.innowhite.whiteboard.service.ImageService;
import com.innowhite.whiteboard.util.Constants;

public class MediaContent extends HttpServlet {

	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public MediaContent() {
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
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Get the absolute path of the image
		ServletContext sc = getServletContext();

		String fileID = req.getParameter(Constants.FILE_ID);
		String fileSource = null;
		String orgName = null;
		boolean thumbs = false;
		if (fileID != null) {

			String mediaType = req.getParameter(Constants.MEDIA_TYPE);

			if (mediaType != null && mediaType.equals(Constants.MEDIA_THUMBS)) {

				thumbs = true;
			}

			if (fileID.endsWith("_USER")) {

				fileID = fileID.substring(0, fileID.indexOf("_"));
				UserImagesVO uiVO = CustomUserImagesDAO.getUserMediaSource(fileID,
						thumbs);
				if(thumbs == true)
					fileSource = uiVO.getThumbnailURL();
				else
					fileSource = uiVO.getImageURL();
				
				
				if(fileSource == null && thumbs==false)
				{	
					log.debug(" The file source is null so check fileystem ");
					fileSource = ImageService.manipulateImageSource(uiVO, thumbs);
					log.debug("GOT "+fileSource);
				
				}// UserIma.getContentSource(fileID);

			} else {

				orgName = req.getParameter(Constants.ORGNAME);
				fileSource = ClientMediaDAO.getContentSource(fileID, orgName);

			}
		}

		log.debug(" The file id : " + fileID + "  fileSource "
				+ fileSource);

		String fileName = null;
		if (fileSource != null) {
			fileName = fileSource.substring(fileSource.lastIndexOf("/") + 1);

		}

		log.debug(" fileSource  " + fileSource + "  fileName :"
				+ fileName);

		// String mimeType = Utility.getMimiType(file);

		// String filename = sc.getRealPath("image.gif");

		// Get the MIME type of the image
		String mimeType = sc.getMimeType(fileName);
		if (mimeType == null) {
			if (fileName != null) {

				if (fileName.contains("flv"))
					mimeType = "video/x-flv";

			}

		}
		//log.debug(" mimeType:: " + mimeType);

		// Set content type
		resp.setContentType(mimeType);

		boolean fileLoaded=false;
		File file=null;
		// Set content size
		try{
			 file = new File(fileSource);
			 if(file.exists()== true)
				 fileLoaded=true;
		}catch(Exception e){
			fileLoaded=false;
		}

		// if (file.exists() == true) {
		// }

		log.debug(" mimetype :  "+fileLoaded+" file exists "+fileLoaded+"  source "+fileSource);
		
		if (mimeType == null || fileLoaded == false) {
			sc.log("Could not get MIME type of " + fileName);
			sc.log(" could not find the file   "+fileSource);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		resp.setContentLength((int) file.length());

		// Open the file and output streams
		FileInputStream in = new FileInputStream(file);
		OutputStream out = resp.getOutputStream();

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		in.close();
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
