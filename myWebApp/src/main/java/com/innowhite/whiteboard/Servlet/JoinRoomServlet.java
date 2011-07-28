package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.ConferenceNumbersVO;
import com.innowhite.whiteboard.persistence.beans.ServerVO;
import com.innowhite.whiteboard.service.JoinRoomService;
import com.innowhite.whiteboard.service.LoadBalancerService;
import com.innowhite.whiteboard.service.RoomService;
import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.ServletUtil;

/**
 * Servlet implementation class ViewRoomServlet
 */
public class JoinRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinRoomServlet() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.debug(" entereing doget: JoinRoomServlet ");
	String checkSum = "";
	// String queryStringWithoutcheckSum="";
	String parentOrg = "";
	// String roomLeader = "";
	// String roomId = "";

	// if(queryStringArray.length>1){
	// queryStringWithoutcheckSum =queryStringArray[0];
	// }

	parentOrg = request.getParameter(InnowhiteConstants.ORG_NAME);

	String hostURL = null;
	// if (parentOrg.indexOf(Constants.USER_DELIMITER) > 0) {
	// StringTokenizer st = new StringTokenizer(parentOrg,
	// Constants.USER_DELIMITER);
	//
	// parentOrg = st.nextToken();
	// hostURL= st.nextToken();
	// }

	log.debug("JoinRoomServlet: " + checkSum);

	request.setAttribute("previousSession", "false");

	String userId = request.getParameter(InnowhiteConstants.USER);
	String roomId = request.getParameter(InnowhiteConstants.ROOML_ID);
	String appName = InnowhiteConstants.WHITEBOARD_SERVER_DATA;

	
	// if the room is locked for new users to join. 
	// redirect the user to locked room page.
	if (RoomService.isRoomLocked(roomId) == true) {
	    response.sendRedirect("http://innowhite.com/room_locked");
	    return;	
	}
	
	// if the room is already closed 
	// redirect the user to room closed page
	if (RoomService.isRoomLocked(roomId) == true) {
	    response.sendRedirect("http://innowhite.com/session_expired");
	    return;	
	}

	ServerVO serverObj = LoadBalancerService.getServerURL(appName, null);

	ConferenceNumbersVO confNumber = null;
	if (parentOrg != Constants.INET) {
	    confNumber = JoinRoomService.setupJoinRoom(roomId, userId, true);
	}

	ServletUtil.setCutomRequestProp(request, response, false, confNumber, serverObj);

	getServletConfig().getServletContext().getRequestDispatcher("/MyJsp.jsp").forward(request, response);

	// Forward to whiteboard.
	// }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
    }

}
