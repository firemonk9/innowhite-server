package com.innowhite.whiteboard.Servlet;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.service.WhiteboardToVideoService;

/**
 * Servlet implementation class WBLiveVideo
 */
public class WBLiveVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WBLiveVideo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	DataOutputStream os1 = null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream sis = null;
		try {

			// sis = request.getInputStream();
			// maybe I should be using a BufferedInputStream
			// instead of the InputStream directly?
			String roomId = request.getParameter("roomId");
			String startRecord = request.getParameter("startRecord");
			String counter = request.getParameter("counter");

			roomId = roomId.trim();
			// log.debug(" entered doPost with  roomId :"+roomId+"  ");

			// int available = request.getContentLength();
			// byte[] input = new byte[available];
			String myData = request.getParameter("data");

			BASE64Decoder decoder = new BASE64Decoder();

			if ((request.getParameter("stopRecord")) != null) {

				if ((request.getParameter("stopRecord").equals("true"))) {

					if (myData != null && myData.length() > 0) {

						byte[] input = decoder.decodeBuffer(myData);
						WhiteboardToVideoService.writeToFile(input, roomId, startRecord,counter);

					}

					WhiteboardToVideoService.stopRecording(roomId);
					log.debug("stopping the record service.");
				}

			} else {
				if (myData != null) {
					byte[] input = decoder.decodeBuffer(myData);
					WhiteboardToVideoService.writeToFile(input, roomId, startRecord,counter);
				}
			}
			// String xmlReply = "<Reply><Message>"+msg+"</Message></Reply>";
			// byte[] data = xmlReply.getBytes("UTF-8");
			ServletOutputStream sos = response.getOutputStream();
			// sos.write(data, 0,data.length);
			sos.flush();
			sos.close();

		} finally {
			if (sis != null)
				sis.close();
		}
	}

}
