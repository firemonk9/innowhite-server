package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.RoomUsersVO;
import com.innowhite.whiteboard.persistence.beans.RoomVO;

public class RoomUsersDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();
    private static final Logger log = LoggerFactory.getLogger(RoomUsersDAO.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

	String user = "test3";
	// save(user, "123123123", "12345");
	// log.debug(getConfNumber(user, "123123123"));
	// updateUserInRoom(user, "123123123");
	//System.err.println(getUsersForRoom("room192"));
	
	updateRoomDetailXML("7774590964","7774590964");
	//System.err.println(getRoomInfo("room192"));
	
	
    }

    public static void lockRoom(String roomId, String status) {

	log.debug("entered lockRoom  roomId  " + roomId + "  status  " + status);
	try {

	    HashMap<String, String> m = new HashMap<String, String>();
	    m.put("roomId", roomId);
	    m.put("status", status);

	    int returnUpdate = sqlMapClient.update("updateLockStatus", m);

	    log.debug("  update return status :: " + returnUpdate);
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // re.printStackTrace();
	}
	// return id;

    }

    public static String getLockStatus(String roomId) {

	log.debug("entered getLockStatus  roomId  " + roomId);
	String returnUpdate = null;
	try {

	    returnUpdate = (String) sqlMapClient.queryForObject("getLockStatus", roomId);

	    log.debug("  update return status :: " + returnUpdate);
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // re.printStackTrace();
	}
	return returnUpdate;

    }

    public static int save(String userId, String roomId, String confNumber) {
	int id = -1;
	log.debug("entered save  userId" + userId + "  orgName  " + roomId + " confNumber " + confNumber);
	try {

	    HashMap<String, String> m = new HashMap<String, String>();
	    m.put("roomId", roomId);
	    m.put("userId", userId);
	    m.put("confNumber", confNumber);

	    id = (Integer) sqlMapClient.insert("insertUserInRoom", m);
	    log.debug("save id : " + id);
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // re.printStackTrace();
	}
	return id;
    }

    public static String getConfNumber(String userId, String roomId) {

	log.debug("Entered getConfNumber userId:  " + userId + "  roomId  " + roomId);
	// Map<String, String> roomStatusResultsMap = new HashMap<String,
	// String>();
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("userId", userId);
	map.put("roomId", roomId);

	try {
	    String value = (String) sqlMapClient.queryForObject("selectConfNumber", map);
	    if (value != null && value.length() > 0) {
		log.debug(" returning the conf number : " + value);
		return value;
	    }

	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // e.printStackTrace();
	    return null;
	}
	return null;
    }

    
    public static Date getRoomStartTime(String roomId){
	
	log.debug("Entered getRoomStartTime  roomId  " + roomId);
	// Map<String, String> roomStatusResultsMap = new HashMap<String,
	// String>();

	try {
	    Date value = (Date) sqlMapClient.queryForObject("getRoomStartTime", roomId);
//	    if (value != null && value.length() > 0) {
//		log.debug(" returning the conf number : " + value);
//		return value;
//	    }

	    log.debug(" returning :: "+value);
	    return value;
	    
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // e.printStackTrace();
	    return null;
	}
	
	
    }
    
    /*
     * Returns the current conference number
     */
    public static String selectCurrentConfNumber(String roomId) {

	log.debug("Entered getConfNumber  roomId  " + roomId);
	// Map<String, String> roomStatusResultsMap = new HashMap<String,
	// String>();

	try {
	    String value = (String) sqlMapClient.queryForObject("selectCurrentConfNumber", roomId);
	    if (value != null && value.length() > 0) {
		log.debug(" returning the conf number : " + value);
		return value;
	    }

	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // e.printStackTrace();
	    return null;
	}
	return null;
    }

    /*
     * returns list of VideoDataVO.
     */
    public static List<RoomUsersVO> getUsersForRoom(String roomName) {

	log.debug("Entered getUsersForRoom");
	log.debug("roomName  " + roomName);
	List<RoomUsersVO> value = null;
	try {

	    value = (List) sqlMapClient.queryForList("getUsersForRoom", roomName);

	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

    public static void updateUserInRoom(String userId, String roomId) {

	log.debug("Entered updateUserInRoom  userId : " + userId + "  roomId  " + roomId);
	// Map<String, String> roomStatusResultsMap = new HashMap<String,
	// String>();
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("userId", userId);
	map.put("roomId", roomId);
	try {
	    // x = (Integer) sqlMapClient.update("updateDocXML", clientMediaVO);
	    int val = sqlMapClient.update("updateUserInRoom", map);
	    log.debug(" the update status   " + val);

	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // e.printStackTrace();
	}
    }

    public static void updateRoomDetailXML(String roomDetailXML, String roomId) {

	log.debug("Entered updateRoomDetailXML  roomDetailXML : " + roomDetailXML + "  roomId  " + roomId);
	// Map<String, String> roomStatusResultsMap = new HashMap<String,
	// String>();
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("roomDetailXML", roomDetailXML);
	map.put("roomId", roomId);
	try {
	    // x = (Integer) sqlMapClient.update("updateDocXML", clientMediaVO);
	    int val = sqlMapClient.update("updateRoomDetailXML", map);
	    log.debug(" the update status   " + val);

	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    // e.printStackTrace();
	}
    }
    
    //updateRoomDetailXML --roomDetailXML
    
    
    
    public static RoomVO getRoomInfo(String roomId) {

	log.debug("Entered getRoomInfo");
	log.debug("roomName  " + roomId);
	RoomVO value = null;
	try {

	    value = (RoomVO) sqlMapClient.queryForObject("roomInfo", roomId);

	} catch (SQLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

    public static boolean isRoomClosed(String roomId) {
	log.debug("Entered getRoomInfo");
	log.debug("roomName  " + roomId);
	String value = null;
	try {

	    Object obj =  sqlMapClient.queryForObject("isRoomClosed", roomId);
	    if(obj == null)
		return false;
	    else
		return true;
	    
	} catch (SQLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	}
	return false;
    }

}
