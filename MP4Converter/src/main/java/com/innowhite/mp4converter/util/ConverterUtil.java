package com.innowhite.mp4converter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tanuja
 * @Date  Feb 27,2012
 */

public class ConverterUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ConverterUtil.class);
	
	public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);

	}

}
