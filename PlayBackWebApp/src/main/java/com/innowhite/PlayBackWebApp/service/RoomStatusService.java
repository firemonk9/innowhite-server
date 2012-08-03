package com.innowhite.PlayBackWebApp.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.dao.CallBackUrlsDao;
import com.innowhite.PlayBackWebApp.dao.RoomDataDao;
import com.innowhite.PlayBackWebApp.dao.RoomUserDataDao;

public class RoomStatusService {

    private RoomDataDao roomDataDao;

    private RoomUserDataDao roomUserDataDao;

    private CallBackUrlsDao callBackUrlsDao;

    private static final Logger log = LoggerFactory.getLogger(RoomStatusService.class);
    /*
     * This is a temporary fix. The saltkey and the url needs to be read from
     * database based on orgname.
     */

    public void setCallBackUrlsDao(CallBackUrlsDao callBackUrlsDao) {
	this.callBackUrlsDao = callBackUrlsDao;
    }

    public void setRoomUserDataDao(RoomUserDataDao roomUserDataDao) {
	this.roomUserDataDao = roomUserDataDao;
    }
    
    public static List<String> closedRoomIdsList = new ArrayList<String>();

    public void saveRoomStatus(String m) {
	try {
	    // whiteboardDataDao.saveWhitebordObj(m);
	    System.err.println(" room started and stopped the values  " + m);

	    // update start and stop time for the room.
	    String arr[] = m.split("_");
	    String roomId = arr[0];
	    String status = arr[1];
	    String time = arr[2];

	    roomDataDao.updateRoomData(arr[1], roomId, time);

	    // check if the room is stopped, invoke stop service.
	    if (m != null && m.contains("_STOPPED_")) {
	    	closedRoomIdsList.add(roomId);	    
			/*CallBackUrlsData callBackUrlsData = callBackUrlsDao.getURLData(roomId);
	
			
			String url = null;
			if(callBackUrlsData != null){
			    url=callBackUrlsData.getClose_room_url();
			}else{
			    
			    log.warn("Not able to get URL from the data base for room ::  "+roomId);
			}
			// invoke remote http service to notify the room close.
			InvokeRemoteHttpService.roomCloseService(roomId,url);*/

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void updateUserRoomLeftStatus(String m) {
	try {
	    // whiteboardDataDao.saveWhitebordObj(m);
	    System.err.println(" user joined and left service called with the values  " + m);

	    // update start and stop time for the room.
	    String arr[] = m.split("_");
	    String userId = arr[0];
	    String roomId = arr[1];
	    String status = arr[2];
	    String time = arr[3];
	    
	    roomUserDataDao.updateUserRoomStatus(userId, status, roomId, time);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * @param roomDataDao
     *            the roomDataDao to set
     */
    public void setRoomDataDao(RoomDataDao roomDataDao) {
	this.roomDataDao = roomDataDao;
    }

}
