package com.innowhite.whiteboard.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.ServletUtil;
import com.innowhite.whiteboard.util.Utility;

/**
 * Servlet implementation class ViewRoomServlet
 */
public class ViewRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug(" entereing doget: ViewRoomServlet ");
		
		String checkSum = "";
		String queryStringWithoutcheckSum="";
		String orgName = "";
		String validServiceStatus = InnowhiteConstants.AUTH_FAILED;
		String roomId = request.getParameter("roomId");
		String queryString = request.getQueryString();
		log.debug("request query String "+ queryString);
		String queryStringArray[] = queryString.split("&checksum=");
		
		if(queryStringArray.length>1){
			queryStringWithoutcheckSum =queryStringArray[0];
		}
		
		//com.innowhite.whiteboard.Servlet.ServletUtil.setParameters(request);
		request.setAttribute("previousSession", "true");
		
		
		log.debug(queryStringArray[0]);
		orgName = request.getParameter(InnowhiteConstants.PARTNER_ORG);
		checkSum = request.getParameter(InnowhiteConstants.CHECKSUM);
		log.debug("orgName: "+orgName);
		log.debug("checkSum: "+checkSum);
		
		 // validServiceStatus = WhiteboardAuthenticatorService.validateRequest(queryStringWithoutcheckSum, orgName, checkSum);
		  log.debug("validServiceStatus "+validServiceStatus);
		  
		//  if(validServiceStatus.equals(InnowhiteConstants.SUCCESS)){
			  Utility.usersInPlayBackMap.put(roomId, roomId);
			  WhiteboardAuthenticationDAOImpl.updatePlayBackActive(roomId);
			  ServletUtil.setCutomRequestProp(request, response,true,null,null);
			  getServletConfig().getServletContext().getRequestDispatcher("/collab.jsp").forward(request, response);

		 // }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
