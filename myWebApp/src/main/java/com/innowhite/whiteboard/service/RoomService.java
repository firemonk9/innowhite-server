package com.innowhite.whiteboard.service;

import com.innowhite.whiteboard.persistence.dao.RoomUsersDAO;

public class RoomService {

    public static void main(String args[]) {

	lockRoom("80219633417", "false");

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
	if(status != null )
	{
	    if(status.equals("true"))
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

	boolean status = RoomUsersDAO.isRoomClosed(roomId);
	
	return status;

    }
    
    

}
