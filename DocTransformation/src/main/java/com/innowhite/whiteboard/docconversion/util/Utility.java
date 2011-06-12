/**
 * 
 */
package com.innowhite.whiteboard.docconversion.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author firemonk
 * 
 */
public class Utility {
	private static final Logger log = LoggerFactory.getLogger(Utility.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//getServerURLWithContext("http://192.168.1.100:5080/whiteboard/servlet/UserImagesList",InnowhiteConstants.APP_NAME);
		log.debug(removeSpace("qw eqw   e.doc"));

	}

	public static Map<String, String> usersInPlayBackMap = new HashMap<String, String>();

	public static String getServerURL(String url, String contextPath) {

		contextPath = contextPath.substring(1);

		log.debug("$$####!@@"+url);

		String s = url.substring(0, url.indexOf(contextPath, 0) - 1);

		// s = s+ Constants.APP_NAME +Constants.UPLOAD_FILES_URL;
		log.debug(s);
		return s;
	}

	
	public static String getServerURLWithContext(String url, String contextPath) {

		contextPath = contextPath.substring(1);

		log.debug("$$####!@@"+url);

		String s = url.substring(0, url.indexOf(contextPath, 0) + contextPath.length()+1);

		// s = s+ Constants.APP_NAME +Constants.UPLOAD_FILES_URL;
		log.debug(s);
		return s;
	}
	
	public static String removeSpace(String fileName) {

		if (fileName != null && fileName.contains(Constants.SPACE)) {
			fileName = fileName.replace(Constants.SPACE, Constants.URL_USER_DELIMITER);
		}
		return fileName;
	}

	
	public static String putBackSpace(String fileName) {

		if (fileName != null && fileName.contains(Constants.URL_USER_DELIMITER)) {
			fileName = fileName.replace(Constants.URL_USER_DELIMITER,Constants.SPACE);
		}
		return fileName;
	}

	
	public static String getUnique() {

		String r = "" + Math.round((Math.random() * 100)) + Calendar.getInstance().getTimeInMillis();
		return r;
	}

	public static void printMap(Map map) {

		Set entries = map.entrySet();
		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			log.debug(entry.getKey() + " : " + entry.getValue());
		}
		//log.debug();
	}

	public static boolean userFirstTime(String userName, Map<String, String> map) {

		for (String obj : map.values()) {
			log.debug("userName :" + userName);
			if (obj != null && obj.equals(userName))
				return true;
		}
		return false;

	}

	public static boolean allowedFiles(String fileName) {
		if (fileName != null) {
			String arr[] = fileName.split("\\.");
			if (arr != null && arr.length > 0) {
				String extension = arr[arr.length - 1];
				if (Constants.ALLOWED_INPUT_FILES.indexOf(extension) >= 0)
					return true;
			}
		}
		return false;
	}

	
	public static boolean allowedPptx(String fileName) {
		if (fileName != null) {
			String arr[] = fileName.split("\\.");
			if (arr != null && arr.length > 0) {
				String extension = arr[arr.length - 1];
				if (Constants.ALLOWED_PPTX_INPUT_FILES.indexOf(extension) >= 0)
					return true;
			}
		}
		return false;
	}
	
	public static String stripExtension(String filePath) {

		if (filePath != null) {
			String arr[] = filePath.split("\\.");
			if (arr != null && arr.length > 0) {
				// String extension = arr[arr.length-1];
				return arr[0];

			}

		}

		return null;
	}

	public static boolean isPdf(String fileName) {

		if (fileName != null) {
			String arr[] = fileName.split("\\.");
			if (arr != null && arr.length > 0) {
				String extension = arr[arr.length - 1];
				if (extension.equals("pdf"))
					return true;
			}
		}
		return false;
	}

	public static String[] getUserName(String user) {

		String arr[] = new String[2];
		int val = user.indexOf(Constants.USER_DELIMITER);
		arr[0] = user.substring(0, val);
		arr[1] = user.substring(val + 5, user.length());
		return arr;
	}

	/*
	 * this returns file name from URL URL =
	 * http://75.111.200.188:5080/whiteboard
	 * /UserImages/pooja__DSC01690_43982-0.JPG return string = pooja__DSC01690
	 * 
	 * and if URL =
	 * http://75.111.200.188:5080/whiteboard/UserImages/pooja__DSC01690.JPG
	 * returns pooja__DSC01690
	 */
	public static String getFileName(String imageUrl) {

		if (imageUrl != null) {
			int dotIndex = imageUrl.lastIndexOf('/');

			// this is a presentation
			if (dotIndex > 0) {

				return imageUrl.substring(imageUrl.lastIndexOf('/') + 1, imageUrl.lastIndexOf('-'));
			} else {
				return imageUrl.substring(0, imageUrl.lastIndexOf('.'));

			}

		}

		return null;
	}

	public static String getActualImageName(String fileName) {

		if (fileName != null && fileName.contains(Constants.USER_DELIMITER)) {
			fileName = fileName.replace(Constants.USER_DELIMITER, Constants.SPACE);
		}
		return fileName;

	}

	

	public static String getlongUniqVal() {

		String s = "" + Calendar.getInstance().getTimeInMillis();
		s = s.substring(5, s.length());
		return s + "" + Math.round(Math.random() * 1000);
		// return null;
	}
	
	public static String get4DigitRandom() {

		
		return "" + Math.round(Math.random() * 1000);
		// return null;
	}

	
	
	public static List<String> getCourseList(String courses) {

		String[] arr = courses.split(Constants.API_DELIMITER);
		List<String> wordList = Arrays.asList(arr);
		log.debug(" The course list : "+wordList);
		return wordList;
	}


	public static String getMimiType(String file) {
		// TODO Auto-generated method stub
		return null;
	}

}
