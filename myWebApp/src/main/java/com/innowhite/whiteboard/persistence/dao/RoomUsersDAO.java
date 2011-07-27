package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.RoomUsersVO;
import com.innowhite.whiteboard.persistence.beans.RoomVO;
import com.innowhite.whiteboard.persistence.beans.VideoDataVO;

public class RoomUsersDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();
    private static final Logger log = LoggerFactory.getLogger(RoomUsersDAO.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

	String user = "test3";
//	save(user, "123123123", "12345");
//	log.debug(getConfNumber(user, "123123123"));
//	updateUserInRoom(user, "123123123");
	getUsersForRoom("77985972582");

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
	} catch (SQLException re) {
	    log.error(" exception  ", re);
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
	    log.error(" exception  ", e);
	    // e.printStackTrace();
	    return null;
	}
	return null;
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
	    log.error(" exception  ", e);
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
	    log.error(" exception  ", e);
	    // e.printStackTrace();
	}
    }

    public static RoomVO getRoomInfo(String roomId){
	
	log.debug("Entered getRoomInfo");
	log.debug("roomName  " + roomId);
	RoomVO value = null;
	try {

	    value = (RoomVO) sqlMapClient.queryForObject("roomInfo", roomId);

	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return value;
	
    }
    
    
}
