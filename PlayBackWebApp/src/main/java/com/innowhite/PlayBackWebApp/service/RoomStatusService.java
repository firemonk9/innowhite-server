package com.innowhite.PlayBackWebApp.service;

public class RoomStatusService {

    public void saveRoomStatus(String m) {
	try {
	    // whiteboardDataDao.saveWhitebordObj(m);
	    System.err.println(" room started and stopped the values  " + m);
	    String arr[] = m.split("_");
	    if(arr != null)
	    {
		
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
