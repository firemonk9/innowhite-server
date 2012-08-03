package com.innowhite.PlayBackWebApp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.dao.CallBackUrlsDao;
import com.innowhite.PlayBackWebApp.dao.RoomDataDao;
import com.innowhite.PlayBackWebApp.model.CallBackUrlsData;
import com.innowhite.PlayBackWebApp.util.Constants;

public class MonitorRoomCloseService extends TimerTask {

	private RoomDataDao roomDataDao;
	private CallBackUrlsDao callBackUrlsDao;

	String url = null;
	String closedRoomId = null;
	
	private static final Logger log = LoggerFactory.getLogger(MonitorRoomCloseService.class);

	public MonitorRoomCloseService() {

		Timer t = new Timer();
		t.scheduleAtFixedRate(this, Constants.MONITOR_ROOM_CLOSE_FREQ, Constants.MONITOR_ROOM_CLOSE_FREQ);
	}

	public void setCallBackUrlsDao(CallBackUrlsDao callBackUrlsDao) {
		this.callBackUrlsDao = callBackUrlsDao;
	}

	public void setRoomDataDao(RoomDataDao roomDataDao) {
		this.roomDataDao = roomDataDao;
	}

	@Override
	public void run() {

		log.info("In MonitorRoomCloseService run");

		if (RoomStatusService.closedRoomIdsList != null) {

			for (int i = 0; i < RoomStatusService.closedRoomIdsList.size(); i++) {

				closedRoomId = RoomStatusService.closedRoomIdsList.get(i);
				log.debug("RoomId from List closedRoomId:"+ closedRoomId);

				if (verifyRoomClosedTime(closedRoomId)) {
					
					log.info("Room has been closed more than 20 mins back(cutofftime)");
					CallBackUrlsData callBackUrlsData = callBackUrlsDao
							.getURLData(closedRoomId);
					if (callBackUrlsData != null) {
						url = callBackUrlsData.getClose_room_url();
					} else {
						log.warn("Not able to get URL from the data base for room ::  "
								+ closedRoomId);
					}
					// invoke remote http service to notify the room close.
					InvokeRemoteHttpService.roomCloseService(closedRoomId, url);
					RoomStatusService.closedRoomIdsList.remove(i);
				}

			}
		}

	}

	private boolean verifyRoomClosedTime(String _closedRoomId) {
		log.debug("Entered verifyRoomClosedTime :: _closedRoomId:"+ _closedRoomId);
		Date endDateObj = roomDataDao.getRoomEndDate(_closedRoomId);
		if(endDateObj!= null){
			if (minsDiffBetween(endDateObj, new Date()) > 20) {
				log.info("Leaving verifyRoomClosedTime");
				return true;
			}
		}
		log.info("Leaving verifyRoomClosedTime");
		return false;
	}

	private long minsDiffBetween(Date d1, Date d2) {
		log.info("Entered minsDiffBetween");
		  Calendar calendar1 = Calendar.getInstance();
		  Calendar calendar2 = Calendar.getInstance();
		  calendar1.setTime(d1); calendar2.setTime(d2);
		  long diffMinutes = (calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / (60 * 1000);
		  log.info("Leaving minsDiffBetween");
		  return diffMinutes;
	}

}
