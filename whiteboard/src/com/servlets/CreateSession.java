package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.exception.InnowhiteServiceException;
import com.innowhite.whiteboard.util.ServletUtil;
import com.innowhite.whiteboard.webservice.client.ServiceClientImp;

public class CreateSession extends HttpServlet {

	
	private static Logger log = Red5LoggerFactory.getLogger(CreateSession.class, "whiteboard");
	/**
	 * Constructor of the object.
	 */
	public CreateSession() {
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
	 *             67.205.89.24
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//doPost(request, response);
		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		// out.println("<HTML>");
		// out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		// out.println("  <BODY>");
		// out.print("    This is ");
		// out.print(this.getClass());
		// out.println(", using the GET method");
		// out.println("  </BODY>");
		// out.println("</HTML>");
		// out.flush();
		// out.close();
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

		String userId = request.getParameter("userid");
		String courses = request.getParameter("course_id");
		String teacher = request.getParameter("teacher");
		String wbSession = request.getParameter("session_id");
		String previousSession = request.getParameter("previousSession");
		String orgName = request.getParameter("orgName");
		String time = request.getParameter("time");
		String date = request.getParameter("date");
		
		

		log.debug("userId=" + userId + "    courses=" + courses + "  teacher=" + teacher + "  wbSession=" + wbSession + " previousSession="
				+ previousSession+" date="+date+" time="+time+"  orgName="+orgName);

//		if (wbSession != null && wbSession.length() > 0) {
//			request.setAttribute("joinroom", wbSession);
//		}
		request.setAttribute("clientname", userId);

		if (teacher != null && teacher.length() > 0) {
			request.setAttribute("groupLeader", teacher);
		}

		if (courses != null && courses.length() > 0) {
			// request.setAttribute("groupLeader", teacher);
		}

		if (previousSession != null && previousSession.length() > 0) {
			request.setAttribute("previousSession", previousSession);
			if(previousSession.equals("true"))
				request.setAttribute("groupLeader", "false");
		}
		
		
		
	
		request.setAttribute("clientname", userId);

		try {
				
			String sessionId =null;
			if(wbSession == null || wbSession.trim().length() < 2)
			{
				
				if(date != null)
					sessionId = ServiceClientImp.createFutureSession(userId, orgName,courses,date);
				else
					sessionId = ServiceClientImp.createSession(userId, orgName,courses);
			}
			else
			{
				sessionId =wbSession;
				request.setAttribute("groupLeader", "false");
			}
			request.setAttribute("joinroom", sessionId);
//			if(previousSession != null && previousSession.equals("true"))
//				getServletConfig().getServletContext().getRequestDispatcher("/MyJsp.jsp").forward(request, response);
//			else	
				getServletConfig().getServletContext().getRequestDispatcher("/collab.jsp").forward(request, response);

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InnowhiteServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
