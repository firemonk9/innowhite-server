package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.ConferenceNumbersVO;

public class ConferenceMeetingDAO {

	private static SqlMapClient sqlMapClient =IbatisManager.getSqlMap();
	private static final Logger log = LoggerFactory.getLogger(ConferenceMeetingDAO.class);

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String user = "test3";
	
		
		ConferenceNumbersVO o = getConfMeetingNumber();
		log.debug("  phone "+o.getPhoneNumber()+"  meeting "+o.getMeetingNumber());
//		updateUserInRoom(user,"123123123");

	}
	
	
	/*	This function is called when a user joins the room for the first time. This function returns the unique conference 
	 *   meeting Id. This function is synchronized because to make sure every request gets a unique number.
	 *   
	 *   The value returned is always between 1000 and 9999.
	 *   Logic is also in DAO which is not good design but will do  for now.
	 *   
	 * */
	
	public static ConferenceNumbersVO getConfMeetingNumber() {

		log.debug("Entered getConfMeetingNumber userId:  ");
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		
		try {
			ConferenceNumbersVO  obj = (ConferenceNumbersVO)sqlMapClient.queryForObject("selectConferenceMeetingId");
			if(obj == null)
				return null;
			int value = obj.getMeetingNumber();
			if(value == 9999)
				value=1000;
			value = value + 1;
			
			updateConfMeetingNumber(value);
			return obj;
			
		} catch (SQLException e) {
			log.error(" exception  ",e);
			//e.printStackTrace();
			return null;
		}
	
	}
	
	public static void updateConfMeetingNumber(Integer meetingID) {

		log.debug("Entered updateConfMeetingNumber  meetingID : "+ meetingID);
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		try {
			
			//int number = Integer.parseInt(meetingID);
			//x = (Integer) sqlMapClient.update("updateDocXML", clientMediaVO);
			int val = sqlMapClient.update("updateConferenceMeetingId", meetingID);
			log.debug(" the update status   "+val);	
			
		} catch (SQLException e) {
			log.error(" exception  ",e);
			//e.printStackTrace();
		}
		
	}
	
	
	
	public static  ConferenceNumbersVO getConfPhoneNumber() {

		log.debug("Entered getConfMeetingNumber userId:  ");
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		
		try {
			ConferenceNumbersVO  obj = (ConferenceNumbersVO)sqlMapClient.queryForObject("selectConferencePhoneNum");
			return obj;
			
		} catch (SQLException e) {
			log.error(" exception  ",e);
			//e.printStackTrace();
			return null;
		}
	
	}
	

}
