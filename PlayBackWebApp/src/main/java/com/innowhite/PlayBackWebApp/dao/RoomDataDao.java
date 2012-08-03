package com.innowhite.PlayBackWebApp.dao;

import java.util.Date;

import org.hibernate.Query;
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
			ensureRoomVideoRecordClose(roomData);
			ensureRoomSessionRecordClose(roomData);
		}
	}

	@Transactional
	private void updateRoomEndData(RoomData roomData) {

		log.debug("entered updateRoomEndData");
		Session session = sessionFactory.getCurrentSession();

		String query = "update RoomData o set o.endTime=:endTime  where o.roomName=:roomName";
		int val = session.createQuery(query).setTimestamp("endTime", roomData.getEndTime()).setString("roomName", roomData.getRoomName()).executeUpdate();
		log.debug("retuned updateRoomStartData val " + val);
		// /log.debug("");

	}

	@Transactional
	private void ensureRoomVideoRecordClose(RoomData roomData) {

		log.debug("entered ensureRoomVideoRecordClose");
		Session session = sessionFactory.getCurrentSession();

		String query = "update VideoData o set o.endTime=:endTime , o.status=:status where o.roomName=:roomName and o.endTime is null";
		int val = session.createQuery(query).setTimestamp("endTime", roomData.getEndTime()).setString("roomName", roomData.getRoomName()).setString("status", "Not Closed Properly").executeUpdate();
		log.debug("retuned ensureRoomVideoRecordClose val " + val);
		// /log.debug("");

	}
	
	@Transactional
	private void ensureRoomSessionRecordClose(RoomData roomData) {

		log.debug("entered ensureRoomSessionRecordClose");
		Session session = sessionFactory.getCurrentSession();

		String query = "update SessionRecording o set o.endTime=:endTime , o.status=:status where o.roomName=:roomName and o.endTime is null";
		int val = session.createQuery(query).setTimestamp("endTime", roomData.getEndTime()).setString("roomName", roomData.getRoomName()).setString("status", "Not Closed Properly").executeUpdate();
		log.debug("retuned ensureRoomSessionRecordClose val " + val);
		// /log.debug("");

	}
	
	
	
	@Transactional
	private void updateRoomStartData(RoomData roomData) {

		log.debug("entered updateRoomStartData");
		Session session = sessionFactory.getCurrentSession();

		String query = "update RoomData o set o.startTime=:startTime  where o.roomName=:roomName";
		int val = session.createQuery(query).setTimestamp("startTime", roomData.getStartTime()).setString("roomName", roomData.getRoomName()).executeUpdate();
		log.debug("retuned updateRoomStartData val " + val);
		// /log.debug("");

	}
	
	@Transactional
    public Date getRoomEndDate(String roomId) {
    	Date endDateObj = null;
		try {
				log.debug("Entered getRoomEndDate");
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createSQLQuery("select end_date from room where room_id ='"+roomId+"' ");
				endDateObj = (Date)(query.uniqueResult());
				log.debug("Inside getRoomEndDate with endDateObj :" + endDateObj);
				   
				session.clear();
				session.flush();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return endDateObj;	
		
	 }

}
