package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.service.WhiteboardAuthenticatorService;

/**
 * Servlet implementation class RoomStatusServlet
 */
public class RoomStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Red5LoggerFactory.getLogger(RoomStatusServlet.class, InnowhiteConstants.APP_NAME);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug(" entereing doget: RoomStatusServlet ");
		
		String checkSum = "";
		String queryStringWithoutcheckSum="";
		String orgName = "";
		//String validServiceStatus = "AUTH_FAILED";
		String roomId = "";
		String queryString = request.getQueryString();
		queryString = URLDecoder.decode(queryString, "utf-8");
		log.debug("request query String "+ queryString);
		String queryStringArray[] = queryString.split("&checksum=");
		
		if(queryStringArray.length>1){
			queryStringWithoutcheckSum =queryStringArray[0];
		}
		log.debug(queryStringArray[0]);
		orgName = request.getParameter("orgName");
		checkSum = request.getParameter("checksum");
		log.debug("orgName: "+orgName);
		log.debug("checkSum: "+checkSum);
		
		//  validServiceStatus = WhiteboardAuthenticatorService.validateRequest(queryStringWithoutcheckSum, orgName, checkSum);
		//  log.debug("validServiceStatus "+validServiceStatus);
		 
		String roomParam = request.getParameter("rooms");  
		String rooms[] = roomParam.split("_");
		String res =	WhiteboardAuthenticatorService.checkRoomStatus(rooms);
		log.debug(res);
		 response.setContentType("text/xml");
		 PrintWriter out = response.getWriter();
		 out.println(res);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
