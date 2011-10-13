package com.innowhite.whiteboard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.dao.RoomUsersDAO;
import com.innowhite.whiteboard.util.Utility;

public class RoomService {

    private static HashMap<String, String> roomDetail = new HashMap<String, String>();
    private static final Logger log = LoggerFactory.getLogger(RoomService.class);
    public static void main(String args[]) {

	// lockRoom("80219633417", "false");
	System.err.println(getRoomDetail("84461721822"));
    }

    /*
     * RoomId = which has to be locked / unlocked Status = true / false true =
     * lock the room false = unlock the room.
     */
    public static void lockRoom(String roomId, String status) {

	if (status != null && (status.equals("true") || status.equals("false"))) {
	    RoomUsersDAO.lockRoom(roomId, status);
	}
    }

    /*
     * RoomId = which has to be locked / unlocked Status = true / false true =
     * lock the room false = unlock the room.
     */
    public static boolean isRoomLocked(String roomId) {

	String status = RoomUsersDAO.getLockStatus(roomId);
	if (status != null) {
	    if (status.equals("true"))
		return true;
	    else
		return false;
	}
	// if(status != null && (status.equals("true") || status.equals("false")
	// )){
	// RoomUsersDAO.lockRoom(roomId, status);
	// }

	return false;

    }

    /*
     * RoomId = which has to be locked / unlocked Status = true / false true =
     * lock the room false = unlock the room.
     */
    public static boolean isRoomClosed(String roomId) {

	// do not lock test rooms:
	if (roomId != null && roomId.startsWith("room")) {
	    return false;
	}

	boolean status = RoomUsersDAO.isRoomClosed(roomId);

	return status;

    }

    /*
     * This function returns the xml for the room. Which includes various
     * information like room name etc.
     */
    public static String getRoomDetail(String roomID) {

	try {
	    log.debug("Getting getRoomDetail for Room "+roomID);
	    
	    String xml = null;
	    if (roomDetail.get(roomID) != null) {
		xml = roomDetail.get(roomID);
	    } else {

		xml = RoomHttpservice.getRoomDetailService(roomID);
		RoomUsersDAO.updateRoomDetailXML(xml, roomID);
		roomDetail.put(roomID, xml);
	    }

	    String[] arr = xml.split("\\r?\\n");
	    List<String> wordList = Arrays.asList(arr);

	    ArrayList<String> al = new ArrayList<String>(wordList);

	    String timeTag = TimeService.getElapsedTimeStr(roomID);
	    al.add(5, timeTag);
	    StringBuffer sb = new StringBuffer();
	    for (String s : al) {
		if (s == null || s.length() == 0)
		    continue;
		
		sb.append(s + "\n");
	    }

	    return sb.toString();
	} catch (Exception e) {
	    log.error(e.getMessage(),e);
	    
	}
	return Utility.getErrorXML();
    }

}
