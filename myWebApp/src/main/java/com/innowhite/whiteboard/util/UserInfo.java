package com.innowhite.whiteboard.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInfo {

	private  static final Logger log = LoggerFactory.getLogger(UserInfo.class);
	
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
