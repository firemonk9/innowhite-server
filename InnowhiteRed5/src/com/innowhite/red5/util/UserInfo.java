package com.innowhite.red5.util;

import java.util.HashMap;
import java.util.Map;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class UserInfo {

	private static Logger log = Red5LoggerFactory.getLogger(UserInfo.class, "whiteboard");
	
	private static Map userMap=new HashMap<String, String>();
	
	public static void addUser(String user,String sessionId){
		
		log.debug(" user: "+user+"  sessionId :"+sessionId);
		userMap.put(sessionId, user);
	}
	
	public static String getUser(String sessionId)
	{
		log.debug("  sessionId :"+sessionId);
		return (String)userMap.get(sessionId);	
	}
	
}
