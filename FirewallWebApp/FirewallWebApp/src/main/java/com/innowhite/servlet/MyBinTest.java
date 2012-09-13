package com.innowhite.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class MyBinTest
 */
public class MyBinTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger("MyBinTest");

	private static HashMap<String, FileOutputStream> files = new HashMap<String, FileOutputStream>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyBinTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("....doGet....123");

		String reqId = request.getParameter("reqId");
		String status = request.getParameter("status");

		logger.debug("status " + status + " reqId " + reqId);

		if (status != null && status.equals("START")) {

			File f = new File(Calendar.getInstance().getTimeInMillis() + ".flv");
			FileOutputStream fos = new FileOutputStream(f);
			files.put(reqId, fos);
			logger.info(" opening  file. " + f.getAbsolutePath());
		} else {

			FileOutputStream fos = files.get(reqId);
			if (fos != null) {
				fos.close();
				logger.info(" Successfully closed the file. ");
			}
			files.remove(reqId);
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("....doPost....");

		String reqId = request.getParameter("reqId");
		String seq = request.getParameter("seq");
		FileOutputStream fos = files.get(reqId);
		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		// // MultipartFile is a copy of file in memory, not in file system
		// MultipartFile multipartFile = multipartRequest.getFile("blockdata");
		// byte[] b = multipartFile.getBytes();

		// logger.debug("seq " + seq + " reqId " + reqId + "  fos " +
		// fos+" bytes size "+b.length);
		// request.get
		//boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		ServletFileUpload upload = new ServletFileUpload();
		// fos.write(b);
		FileItemIterator iter;
		try {
			iter = upload.getItemIterator(request);
			byte[] bytes = null;
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();

				if (!name.equals("blockdata")) {

					String val = Streams.asString(stream);
					logger.debug(" name :" + name + "  val " + val);

					if (name.equals("reqId")) {

						fos = files.get(val);
					}

					// System.out.println("Form field " + name + " with value "
					// + Stream.asString(stream) + " detected.");
				} else {

					bytes = org.apache.commons.io.IOUtils.toByteArray(stream);

				}
			}

			fos.write(bytes);

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// InputStream inputStream = request.getInputStream();
		// try {
		//
		// if (inputStream != null) {
		//
		// byte[] charBuffer = new byte[1000];
		// int bytesRead = -1;
		// while ((bytesRead = inputStream.read(charBuffer)) > 0) {
		// fos.write(charBuffer);
		// charBuffer = new byte[1000];
		//
		// }
		// } else {
		// // stringBuilder.append("");
		// }
		// } catch (IOException ex) {
		// logger.error("", ex);
		// } finally {
		// if (inputStream != null) {
		// try {
		// inputStream.close();
		// } catch (IOException ex) {
		// logger.error("", ex);
		// }
		// }
		// }

	}

}
