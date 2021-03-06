package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;

public class RoomUsersDAO {

	private static SqlMapClient sqlMapClient =IbatisManager.getSqlMap();
	private static Logger log = Red5LoggerFactory.getLogger(RoomUsersDAO.class, "whiteboard");

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String user = "test3";
		save(user,"123123123","12345");
		log.debug(getConfNumber(user,"123123123"));
		updateUserInRoom(user,"123123123");

	}
	
	public static int save(String userId,String roomId, String confNumber) {
		int id = -1;
		log.debug("entered save  userId"+ userId+"  orgName  "+roomId+" confNumber "+confNumber);
		try {
			
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("roomId", roomId);
			m.put("userId", userId);
			m.put("confNumber", confNumber);
			
			
			id = (Integer)sqlMapClient.insert("insertUserInRoom", m);
			log.debug("save id : "+id);
		} catch (SQLException re) {
			log.error(" exception  ",re);
			//re.printStackTrace();
		}
		return id;
	}
	
	
	public static String getConfNumber(String userId,String roomId) {

		log.debug("Entered getConfNumber userId:  "+ userId+"  roomId  "+roomId);
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("roomId", roomId);
	
		try {
			String  value = (String)sqlMapClient.queryForObject("selectConfNumber", map);
			if(value != null && value.length() > 0){
				log.debug(" returning the conf number : "+value);
				return value;
			}	
			
		} catch (SQLException e) {
			log.error(" exception  ",e);
			//e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/*Returns the current conference number 
	 * */
	public static String selectCurrentConfNumber(String roomId) {

		log.debug("Entered getConfNumber  roomId  "+roomId);
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
	
		try {
			String  value = (String)sqlMapClient.queryForObject("selectCurrentConfNumber", roomId);
			if(value != null && value.length() > 0){
				log.debug(" returning the conf number : "+value);
				return value;
			}	
			
		} catch (SQLException e) {
			log.error(" exception  ",e);
			//e.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	public static void updateUserInRoom(String userId,String roomId) {

		log.debug("Entered updateUserInRoom  userId : "+ userId+"  roomId  "+roomId);
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("roomId", roomId);
	
		try {
			
			//x = (Integer) sqlMapClient.update("updateDocXML", clientMediaVO);
			int val = sqlMapClient.update("updateUserInRoom", map);
			log.debug(" the update status   "+val);	
			
		} catch (SQLException e) {
			log.error(" exception  ",e);
			//e.printStackTrace();
		}
		
	}

}
