package com.innowhite.whiteboard.Servlet;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.business.DocumentManagement;
import com.innowhite.business.ImageServicess;
import com.innowhite.business.ProcessConversion;
import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;
import com.innowhite.whiteboard.service.ImageService;
import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;
import com.innowhite.whiteboard.util.Utility;

@SuppressWarnings("serial")
public class UploadFileServlet extends HttpServlet {
	 private  final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Constructor of the object.
	 */
	public UploadFileServlet() {
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

		log.debug(" Enter do post UploadFileServlet");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		//factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()

		String urlPath = Constants.UBUNTU_FOLDER_PATH;
		urlPath = (InnowhiteProperties.getPropertyVal(InnowhiteConstants.TEMP_LOCATION));

		boolean isDocument = false;
		factory.setRepository(new File(urlPath));

		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		upload.setSizeMax(10000000);
		File f = null;
		// if image is graph
		String fileNameTe = request.getParameter(Constants.FILE_NAME);
		log.debug("file name ::::   " + fileNameTe);
		String conversionId = null;
		String imageXML = null;

		if (request.getParameter(Constants.IMAGE_DATE) != null
				&& fileNameTe != null) {
			// save base 64 image
			f = saveGraphImage(request);
			log.debug(" inbox ::++++ " + request.getParameter("INBOX"));
			if (request.getParameter("INBOX") != null
					&& request.getParameter("INBOX").equals("true")) {
				UserImagesVO ui = new UserImagesVO();
				ui.setUserName(request.getParameter("USER"));
				ui.setImageFolderSeq(0);
				ui.setImageName(request.getParameter(Constants.FILE_NAME));
				ui.setImageURL(request.getParameter(Constants.FILE_NAME));
				ui.setImageType(1);
				// UserImagesDAO uid = new UserImagesDAO();
				DocumentManagement.saveDocument(ui);
				ProcessConversion.createThumbNail(f, fileNameTe);

			}

		} else {
			List fileItems = null;
			boolean fileUploadException = false;
			try {
				fileItems = upload.parseRequest(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fileUploadException = true;
				log.error("fileUploadException", e);
			}
			
			if(fileUploadException == true){
				
				String errorXml = getFileMaxLimitErrorXML();
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				out.println(errorXml);
				out.close();
				return;
				
			}
			
			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			log.debug("  size is   " + fileItems.size());

			Iterator i = fileItems.iterator();
			// write the file
			String comment = ((FileItem) i.next()).getString();
			FileItem fi = (FileItem) i.next();
			// filename on the client
			String fileName = fi.getName();

			log.debug(" field name:  " + fi.getFieldName());
			String fieldVal = fi.getFieldName();
			String[] userDes = Utility.getUserName(fieldVal);

			String user = userDes[0];
			String desc = userDes[1];
			log.debug("  filename before removing space:  " + fileName);
			fileName = Utility.removeSpace(fileName);
			log.debug("  filename  after removing space:  " + fileName);

			try {

				String fileNameVal = fileName;

				f = new File(
						InnowhiteProperties
								.getPropertyVal(InnowhiteConstants.USER_UPLOADED_FILES),
						fileNameVal);
				fi.write(f);

				// ppt, doc, pdf
				if (Utility.allowedFiles(fileNameVal) == true) {

					isDocument = true;
					log.debug("enter document conversion");

					conversionId = ProcessConversion.convertDocument(
							f.getAbsolutePath(), user, desc, fileNameVal,
							request.getContextPath(), true, fileName, false);
					// response.setContentType("text/xml");
					// PrintWriter out = response.getWriter();
					// out.write(ImageDAO.getImageXML(user,
					// request.getRequestURL().toString()));

				} else // images gif, jpg, png
				{

					log.debug("enter image gif, jpg, png upload part");
					UserImagesVO ui = new UserImagesVO(user,
							f.getAbsolutePath(), 0);
					if (desc != null && desc.length() > 0)
						ui.setImageDescription(desc);
					else
						ui.setImageDescription(fileName);
					ui.setImageName(fileName);
					ui.setImageFolderSeq(0);
					ui.setImageType(1);
					CustomUserImagesDAO dao = new CustomUserImagesDAO();
					ProcessConversion.createThumbNail(f, fileNameVal);
					ImageServicess.resizeImage(f.getAbsolutePath());
					// Transaction tx = dao.getSession().beginTransaction();
					int imageId = dao.save(ui);

					StringBuffer serverInfo = request.getRequestURL();
					String s = request.getRequestURI();

					imageXML = ImageService.getSingleImageXMLbyId(imageId,
							serverInfo.toString());
					// String imageXML =

					// tx.commit();
					// dao.getSession().close();
				}
				// dao.save(obj);
				// log.debug(f.getAbsolutePath());

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		response.setContentType("text/xml");

		StringBuffer sb = new StringBuffer();
		sb.append(" <response> <returnStatus>");

		if (conversionId != null)
			sb.append(InnowhiteConstants.SUCCESS);
		else
			sb.append("");

		sb.append("</returnStatus>  <conversionId>");
		sb.append("" + conversionId);
		sb.append("</conversionId> ");
		sb.append("<isDocument>" + isDocument + "</isDocument>");

		if (imageXML != null) {

			sb.append("<isImage>true</isImage>");
			sb.append(imageXML);
		}

		sb.append(" </response>");

		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='UTF-8'?>");

		log.debug(" the upload XML is " + sb.toString());
		out.println(sb.toString());
		out.close();
	}

	private File saveGraphImage(HttpServletRequest request) {

		String base64str = request.getParameter(Constants.IMAGE_DATE);
		Base64 b64 = new Base64();
		byte[] decoded = b64.decode(base64str.getBytes());
		String fileName = request.getParameter(Constants.FILE_NAME);
		FileOutputStream fop = null;
		File f = null;
		try {

			String urlPath = Constants.UBUNTU_FOLDER_PATH;
			urlPath = urlPath.replaceAll(Constants.APP_NAME, request
					.getContextPath().substring(1));
			f = new File(urlPath, fileName);
			fop = new FileOutputStream(f);
			if (f.exists())
				fop.write(decoded);

			log.debug(f.getAbsolutePath());

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (fop != null)
				try {
					fop.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return f;
	}

	private String getFileMaxLimitErrorXML() {

		// response.setContentType("text/xml");

		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append(" <response> <returnStatus>");
		sb.append("ERROR");
		sb.append("</returnStatus>");
		sb.append("<errorDescription>");
		sb.append("File size is more than 5MB..");
		sb.append("</errorDescription>");
		// PrintWriter out = response.getWriter();

		log.debug(" the error XML is " + sb.toString());
		// out.println(sb.toString());

		return sb.toString();
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
