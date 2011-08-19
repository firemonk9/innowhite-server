package com.innowhite.PlayBackWebApp.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlayBackWebApp.model.RoomData;
import com.innowhite.PlayBackWebApp.model.UserRoomData;
import com.innowhite.PlayBackWebApp.util.Constants;

public class RoomUserDataDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(RoomUserDataDao.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    
    @Transactional
    public void updateUserRoomStatus(String user, String status, String roomName, String time) {

	log.debug("entered updateRoomData");
	UserRoomData roomData = new UserRoomData();

	roomData.setUserId(user);
	roomData.setRoomName(roomName);
	Date curDate = new Date(Long.parseLong(time));

	if (status != null && status.equals(Constants.JOINED)) {
	    roomData.setStartTime(curDate);
	    updateUserRoomStartData(roomData);

	} else if (status != null && status.equals(Constants.LEFT)) {
	    roomData.setEndTime(curDate);
	    updateUserRoomEndData(roomData);
	}
    }
    
    
    
    
    
    private void updateUserRoomEndData(UserRoomData roomData) {
	log.debug("entered updateUserRoomEndData");
	Session session = sessionFactory.getCurrentSession();

	String query = "update UserRoomData o set o.endTime=:endTime  where o.roomName=:roomName and o.userId=:userId";
	int val = session.createQuery(query).setTimestamp("endTime", roomData.getEndTime()).setString("roomName", roomData.getRoomName()).setString("userId", roomData.getUserId())
		.executeUpdate();
	log.debug("retuned updateUserRoomEndData val " + val);
	// /log.debug("");

	
    }


    private void updateUserRoomStartData(UserRoomData roomData) {
	log.debug("entered updateUserRoomStartData");
	Session session = sessionFactory.getCurrentSession();

	String query = "update UserRoomData o set o.startTime=:startTime  where o.roomName=:roomName and o.userId=:userId";
	int val = session.createQuery(query).setTimestamp("startTime", roomData.getStartTime()).setString("roomName", roomData.getRoomName()).setString("userId", roomData.getUserId())
		.executeUpdate();
	log.debug("retuned updateUserRoomStartData val " + val);
	// /log.debug("");

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
