package com.innowhite.whiteboard;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innowhite.whiteboard.docconversion.thread.SWFThread;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	//private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	 private static final Logger log = Logger.getLogger(SWFThread.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		log.info("Welcome home!");
		return "home";
	}
	
}

