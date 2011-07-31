package com.innowhite.PlayBackWebApp.service;

import com.innowhite.PlayBackWebApp.dao.RoomDataDao;

public class RoomStatusService {

    
    private RoomDataDao roomDataDao;
    
    public void saveRoomStatus(String m) {
	try {
	    // whiteboardDataDao.saveWhitebordObj(m);
	    System.err.println(" room started and stopped the values  " + m);

	    // update start and stop time for the room.
	    String arr[] = m.split("_");
	    String roomId = arr[0];
	    String status = arr[1];
	    String time = arr[2];

	    roomDataDao.updateRoomData(arr[1],  roomId, time);

	    // check if the room is stopped, invoke stop service.
	    if (m != null && m.contains("_STOPPED_")) {

		// invoke remote http service to notify the room close.
		InvokeRemoteHttpService.roomCloseService(roomId);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * @param roomDataDao the roomDataDao to set
     */
    public void setRoomDataDao(RoomDataDao roomDataDao) {
	this.roomDataDao = roomDataDao;
    }



}
