package com.innowhite.PlayBackWebApp.service;

public class RoomStatusService {

    public void saveRoomStatus(String m) {
	try {
	    // whiteboardDataDao.saveWhitebordObj(m);
	    System.err.println(" room started and stopped the values  " + m);

	    
	    // check if the room is stopped, invoke stop service.
	    if (m != null && m.contains("_STOPPED_")) {
		
		String arr[] = m.split("_");
		String roomId = arr[0];
		
		// invoke remote http service to notify the room close.
		InvokeRemoteHttpService.roomCloseService(roomId);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
