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

import com.innowhite.whiteboard.util.Constants;

/**
 * Servlet implementation class StreamVideoServlet
 */
public class StreamVideoServlet extends HttpServlet {
    // private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the object.
     */
    public StreamVideoServlet() {
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	// Get the absolute path of the image
	ServletContext sc = getServletContext();

	String fileID = req.getParameter(Constants.FILE_ID);
	String fileSource = null;
	String orgName = null;
	boolean thumbs = false;
	if (fileID != null) {
	    fileSource = "/opt/InnowhiteData/videos/"+fileID+".flv";
	}

	log.debug(" The file id : " + fileID + "  fileSource " + fileSource);

	String fileName = null;
	if (fileSource != null) {
	    fileName = fileSource.substring(fileSource.lastIndexOf("/") + 1);

	}

	log.debug(" fileSource  " + fileSource + "  fileName :" + fileName);

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
	// log.debug(" mimeType:: " + mimeType);

	// Set content type
	resp.setContentType(mimeType);

	boolean fileLoaded = false;
	File file = null;
	// Set content size
	try {
	    file = new File(fileSource);
	    if (file.exists() == true)
		fileLoaded = true;
	} catch (Exception e) {
	    fileLoaded = false;
	}

	// if (file.exists() == true) {
	// }

	log.debug(" mimetype :  " + fileLoaded + " file exists " + fileLoaded + "  source " + fileSource);

	if (mimeType == null || fileLoaded == false) {
	    sc.log("Could not get MIME type of " + fileName);
	    sc.log(" could not find the file   " + fileSource);
	    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	}

	resp.setContentLength((int) file.length());
	 resp.setHeader( "Content-Disposition", "attachment; filename=\"" + fileName + "\"" );

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
     * @throws ServletException
     *             if an error occurs
     */
    public void init() throws ServletException {
	// Put your code here
    }

}
