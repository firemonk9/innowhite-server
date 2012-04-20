/** TO poll the DataBase in regular intervals of time */
package com.innowhite.PlaybackApp.service;

import java.util.TimerTask;

import com.innowhite.PlaybackApp.dao.RoomDao;
/**
 * @author tanuja
 * Date April 20, 2012
 */
public class DBPollerService  extends TimerTask {
	
	private RoomDao roomDao;
	public RoomDao getRoomDao() {
		return roomDao;
	}
	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	@Override
	public void run() {
		
		roomDao.getRoomMaxID();
	
	}

}