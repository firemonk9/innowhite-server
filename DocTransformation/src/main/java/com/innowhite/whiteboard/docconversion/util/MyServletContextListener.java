/**
 * 
 */
package com.innowhite.whiteboard.docconversion.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author firemonk
 * 
 */
public class MyServletContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	// TODO Auto-generated method stub
	ServletContext servletContext = sce.getServletContext();
	try {
	    // create the timer and timer task objects
	    Timer timer = new Timer();
	    MyTimerTask task = new MyTimerTask();

	    // get a calendar to initialize the start time
	    Calendar calendar = Calendar.getInstance();
	    Date startTime = calendar.getTime();

	    // schedule the task to run hourly
	    timer.scheduleAtFixedRate(task, startTime, 1000 * 60 * 60);

	    // save our timer for later use
	    servletContext.setAttribute("timer", timer);
	} catch (Exception e) {
	    servletContext.log("Problem initializing the task that was to run hourly: " + e.getMessage());
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	ServletContext servletContext = sce.getServletContext();

	// get our timer from the Context
	Timer timer = (Timer) servletContext.getAttribute("timer");

	// cancel all pending tasks in the timers queue
	if (timer != null)
	    timer.cancel();

	// remove the timer from the servlet context
	servletContext.removeAttribute("timer");

    }

}
