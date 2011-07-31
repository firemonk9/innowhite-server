package com.innowhite.PlayBackWebApp.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlayBackWebApp.model.RoomData;

public class RoomDataDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(RoomDataDao.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void updateRoomData(String status, String roomName, String time) {

	log.debug("entered updateRoomData");
	RoomData roomData = new RoomData();

	
	roomData.setRoomName(roomName);
	Date curDate = new Date(Long.parseLong(time));

	if (status != null && status.equals("STARTED")) {
	    roomData.setStartTime(curDate);
	    updateRoomStartData(roomData);

	} else if (status != null && status.equals("STOPPED")) {
	    roomData.setEndTime(curDate);
	    updateRoomEndData(roomData);
	}
    }

    @Transactional
    private void updateRoomEndData(RoomData roomData) {

	log.debug("entered updateRoomEndData");
	Session session = sessionFactory.getCurrentSession();

	String query = "update RoomData o set o.endTime=:endTime  where o.roomName=:roomName";
	int val = session.createQuery(query).setTimestamp("endTime", roomData.getEndTime()).setString("roomName", roomData.getRoomName())
		.executeUpdate();
	log.debug("retuned updateRoomStartData val " + val);
	// /log.debug("");

    }
    
    @Transactional
    private void updateRoomStartData(RoomData roomData) {

	log.debug("entered updateRoomStartData");
	Session session = sessionFactory.getCurrentSession();

	String query = "update RoomData o set o.startTime=:startTime  where o.roomName=:roomName";
	int val = session.createQuery(query).setTimestamp("startTime", roomData.getStartTime()).setString("roomName", roomData.getRoomName())
		.executeUpdate();
	log.debug("retuned updateRoomStartData val " + val);
	// /log.debug("");

    }
    

   
}
