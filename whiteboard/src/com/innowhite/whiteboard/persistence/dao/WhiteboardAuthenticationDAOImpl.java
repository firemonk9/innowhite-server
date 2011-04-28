package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.red5.whiteboard.Main;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.OrganizationVO;
import com.innowhite.whiteboard.persistence.beans.RoomVO;

public class WhiteboardAuthenticationDAOImpl implements IWhiteboardAuthenticationDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

	static boolean val = false;
	private static Logger log = Red5LoggerFactory.getLogger(WhiteboardAuthenticationDAOImpl.class, "whiteboard");
	public OrganizationVO getOrganizationDetails(String parentOrg) {
		
		OrganizationVO orgBean = null;
		try {
			
			log.debug(" entering  getOrganizationDetails parentOrg"+parentOrg);
			orgBean = (OrganizationVO) sqlMapClient.queryForObject("getOrganizationBean", parentOrg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orgBean;
	}

	public boolean createRoom(RoomVO roomVO) {
		boolean roomCreated = false;
		int x = 0;
		try {
			x = (Integer) sqlMapClient.insert("createRoom", roomVO);
			log.debug(" createRoom returned " + x);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (x > 0) {
			return true;
		} else {
			return roomCreated;
		}
	}
	
	
	public static int createSubRoomID(String roomId, String roomType){
		int x = 0;
		try {
			Map<String, String> hmMap = new HashMap<String, String>();
			hmMap.put("roomId", roomId);
			hmMap.put("roomType", roomType);
			x = (Integer) sqlMapClient.insert("createSubRoomID", hmMap);
			log.debug(" createSubRoomID returned " + x);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (x > 0) {
			return x;
		} else {
			return x;
		}
	}

	public static int updateStartDate(String roomId) {
		int x = 0;
		try {
			x = sqlMapClient.update("updateStartDate", roomId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return x;
	}

	public static int updateEndDate(String roomId) {
		int x = 0;
		try {
			x = sqlMapClient.update("updateEndDate", roomId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return x;
	}

	public static int updatePlayBackActive(String roomId) {
		int x = 0;
		try {
			x = sqlMapClient.update("updatePlaybackActive", roomId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return x;
	}

	public static int updatePlayBackInActive(String roomId) {
		int x = 0;
		try {
			x = sqlMapClient.update("updatePlaybackInActive", roomId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return x;
	}

	public Map<String, String> roomStatus(String rooms[]) {
		Map<String, String> roomStatusResultsMap = new HashMap<String, String>();
		for (int i = 0; i < rooms.length; i++) {
			try {
				String roomId = rooms[i];
				String status = (String) sqlMapClient.queryForObject("getRoomStatus", roomId);
				log.debug(roomId + " " + status);
				roomStatusResultsMap.put(roomId, status);
			} catch (SQLException e) {

				e.printStackTrace();
				return null;
			}
		}
		return roomStatusResultsMap;
	}

	public List roomListStatus(String status, String orgName, String course,String clientName) {
		//Map<String, String> roomStatusResultsMap = new HashMap<String, String>();
		Map<String,String> hmMap = new HashMap<String,String>();
		hmMap.put("status", status);
		hmMap.put("orgName", orgName);
		hmMap.put("course_id", course);
		
		if (clientName != null && clientName.length() > 0)
			hmMap.put("client_name", clientName);
		
		List al = new ArrayList();
		try {
			al = sqlMapClient.queryForList("roomActiveList", hmMap);
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
		return al;

	}

	public int deleteRooms(String orgName, String clientName, String course, String lesson_plan_id) {
		//Map<String, String> roomStatusResultsMap = new HashMap<String, String>();
		Map<String,String> hmMap = new HashMap<String,String>();

		//if (orgName != null && orgName.length() > 0)
			hmMap.put("orgName", orgName);
		// hmMap.put("clientName", clientName);

		if (course != null && course.length() > 0)
			hmMap.put("course_id", course);
		
		if (clientName != null && clientName.length() > 0)
			hmMap.put("clientName", clientName);
		

		if (lesson_plan_id != null && lesson_plan_id.length() > 0)
			hmMap.put("lesson_plan_id", lesson_plan_id);

		int al = 0;
		try {
			al = sqlMapClient.update("deleteSession", hmMap);
			log.debug(" in deleteRooms -- " + al + " rooms deleted");
			return al;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	// deleteSession

//	public List roomsRecordedList(String status, String orgName, String course,String clientName) {
//		//Map<String, String> roomStatusResultsMap = new HashMap<String, String>();
//		Map<String, String> hmMap = new HashMap<String, String>();
//		hmMap.put("status", status);
//		hmMap.put("orgName", orgName);
//		hmMap.put("course_id", course);
//		
//		if (clientName != null && clientName.length() > 0)
//			hmMap.put("client_name", clientName);
//		
//		List al = new ArrayList();
//		try {
//			al = sqlMapClient.queryForList("roomsRecordedList", hmMap);
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//			return null;
//		}
//		return al;
//
//	}

	public static void main(String[] args) {
		WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();
		int x = wai.createSubRoomID("343432423","VIDEOID");
		log.debug("x "+x);
		if (val == false) {
			//WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();

//			List al = wai.roomListStatus("ACTIVE", "TestInnowhite", "a");
//			for (Iterator iterator = al.iterator(); iterator.hasNext();) {
//				RoomVO roomVo = (RoomVO) iterator.next();
//				log.debug(roomVo.getRoomId());
//				log.debug(roomVo.getRoomName());
//			}
		}
		val = true;
	}

	public List<RoomVO> roomListStatus(String status, String orgName, List<String> course) {

		log.debug("Entered roomListStatus");
		Map<String, String> roomStatusResultsMap = new HashMap<String, String>();
		Map<String, String> hmMap = new HashMap();

		hmMap.put("status", status);
		hmMap.put("orgName", orgName);
		List<RoomVO> al = new ArrayList();
		for (String a : course) {
			hmMap.put("course_id", a);

			try {
				List mylist = sqlMapClient.queryForList("roomActiveList", hmMap);
				al.addAll(mylist);
				// log.debug("course "+a +"  "+al);
			} catch (SQLException e) {

				e.printStackTrace();
				return null;
			}
		}

		return al;
	}

	public List<RoomVO> roomsRecordedList(String status, String orgName, List<String> course) {

		log.debug("Entered roomListStatus");
		//Map<String, String> roomStatusResultsMap = new HashMap<String, String>();
		Map<String,String> hmMap = new HashMap<String,String>();

		hmMap.put("status", status);
		hmMap.put("orgName", orgName);
		List al = new ArrayList();
		for (String a : course) {
			hmMap.put("course_id", a);

			try {
				List mylist = sqlMapClient.queryForList("roomsRecordedList", hmMap);
				al.addAll(mylist);
				// log.debug("course "+a +"  "+al);
			} catch (SQLException e) {

				e.printStackTrace();
				return null;
			}
		}

		return al;
	}

}
