package com.innowhite.test;


import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.transformation.messages.MessagingMain;
import com.util.Constants;

public class APITest {

	
	private static Logger log = Red5LoggerFactory.getLogger(APITest.class, "whiteboard");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		WhiteboardAuthenticatorService was = new WhiteboardAuthenticatorService();
//		int startRoomCount = was.startRoom("99916505024");
//		int stopRoomCount = was.stopRoom("99916505024");
//		
//		log.debug("startRoomCount "+startRoomCount);
//		log.debug("stopRoomCount "+stopRoomCount);
		
//		
//		WhiteboardAuthenticationDAOImpl wadao = new WhiteboardAuthenticationDAOImpl();
//		
//		ArrayList<String> a = new ArrayList<String>();
//		a.add("mymath1");
//		a.add("mymath12");
//		a.add("mymath123");
//		
//		wadao.deleteRooms("INET", "", "jhk","");
		
//		Properties p = System.getProperties();
//		Enumeration keys = p.keys();
//		while (keys.hasMoreElements()) {
//		 String key = (String)keys.nextElement();
//		 String value = (String)p.get(key);
//		 log.debug(key + ": " + value);
//		}
//		
		String orgName = "as";
		
		StringTokenizer st = new StringTokenizer(orgName, Constants.USER_DELIMITER);

		orgName = st.nextToken();
		String clientURL = st.nextToken();
		
		log.debug(" orgName  "+orgName+"   "+clientURL);
		
		
	//	log.debug(al);
		//WhiteboardAuthenticationDAOImpl
		

	}

}
