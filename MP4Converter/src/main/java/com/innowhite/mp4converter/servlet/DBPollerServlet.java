/** TO poll the DataBase in regular intervals of time */
package com.innowhite.mp4converter.servlet;

import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.innowhite.mp4converter.service.DBPollerService;
import com.innowhite.mp4converter.util.Constants;

/**
 * @author tanuja
 * Date April 20, 2012
 */
public class DBPollerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	ApplicationContext context = null;
	
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		ServletContext sc = getServletContext();
	
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		System.err.println(" ... " + context);
		
		if (context != null) {
			Timer t = new Timer();
			DBPollerService st = (DBPollerService) context.getBean("dbPollerService");
			t.scheduleAtFixedRate(st, Constants.DEFAULT_POLLING_FREQ, Constants.DEFAULT_POLLING_FREQ);
		}
    }
	

}
