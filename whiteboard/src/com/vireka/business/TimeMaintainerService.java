package com.vireka.business;

import java.util.Calendar;
import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

/*this class maintains a map of room--vs--sessionTime
 * 	
 * 		normal user can ask for session time.
 * 		Moderator will set the time for the first time.
 * 	
 * */
public class TimeMaintainerService {

	private static Logger log = Red5LoggerFactory.getLogger(TimeMaintainerService.class, "whiteboard");
	
	public static HashMap<String, Long> timeMap=new HashMap<String, Long>();

	public static boolean ifTimeSet(String room, boolean moderator) {
		

		log.debug("entering ifTimeSet");
		
		log.debug("values room "+room+" moderator  "+moderator);

		
		if (timeMap.containsKey(room))
			return true;
		else
			return false;
	}

	public static int getTimeSession(String room, boolean moderator) {

		log.debug("entering getTimeSession");
		
		log.debug("values room "+room+" moderator  "+moderator);
		
		long moderatorTime = 0;
		// the moderator has logged out and trying to log back in or the normal
		// user is asking for time
		if (timeMap.containsKey(room)) {
			moderatorTime = timeMap.get(room);
		} else if (moderator == true)// moderator is logging for the first time
		{
			moderatorTime = Calendar.getInstance().getTimeInMillis();
			timeMap.put(room, moderatorTime);
		} else {
			return 0;
		}

		return (int) (Calendar.getInstance().getTimeInMillis() - moderatorTime);
	}

	public static void removeRoom(String name) {
		
		timeMap.remove(name);
		
		
	}

}
