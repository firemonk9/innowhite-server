package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.MeetingMinutesVo;


/** To implement MeetingMinutesDAO class
 * @author tanuja
 * @Date 01-23-2012
 */

public class MeetingMinutesDAO {
	
	 private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();
	 private static final Logger log = LoggerFactory.getLogger(MeetingMinutesDAO.class);
  	    
	 	/* To save user entered meeting minutes for specific room
	     * meetMinVo MeetingMinutes object
	    */
	    public static int saveMeetingMinutes(MeetingMinutesVo meetMinVo) {
	    	log.debug("Entered saveMeetingMinutes");
	    	int retunrVal=0;
	    	try {
	    	    Object obj = sqlMapClient.insert("saveMeetingMinutes", meetMinVo);
	    	    log.debug(" returned from saveMeetingMinutes is : " + obj);
	    	    retunrVal=Integer.parseInt(obj.toString());
	    	} catch (SQLException e) {
	    	    log.error(e.getMessage());
	    	}
	    	return retunrVal;
	      }
	    
	    /* To display user entered meeting minutes for specific room
	     * roomId RoomId
	     */
	    public static List<MeetingMinutesVo> getAllMeetingMinutes(String roomId) {
	    	log.debug("Entered getAllMeetingMinutes");
	    	List<MeetingMinutesVo> meetMinList=null;
	    	try {
	    		meetMinList =(List)sqlMapClient.queryForList("meetingMinutesList", roomId);
	    	} catch (SQLException e) {
	    	    	e.printStackTrace();
	    	    	return null;
	    	}
	    	return meetMinList;
	    }
	    
	    /* To update selected meeting minute in a room
		 * meetMinId= Meeting Minute Id
	     * roomId = RoomId
	     * strDesc= Description to update
	     */	
	    public static void updateMeetingMinute(int meetMinId,String roomId,String strDesc) {
	    	log.debug("Entered updateMeetingMinute");
	    	try {
	    	    HashMap<String, Object> idInfo = new HashMap<String, Object>();
	    	    idInfo.put("id",meetMinId);
	    	    idInfo.put("roomId",roomId);
	    	    idInfo.put("description",strDesc);
	    	 
	    	    Object obj = sqlMapClient.update("updateMeetingMinute", idInfo);
	    	    log.debug(" returned from updateMeetingMinute is : " + obj);

	    	} catch (SQLException e) {
	    	    log.error(e.getMessage());
	    	}
        }
	    
	    /* To delete selected Meeting Minute from a room
	     * meetMinId Meeting Minute Id
	     * roomId RoomId
	     */
	    public static void deleteMeetingMinute(int meetMinId,String roomId) {
	    	log.debug("Entered deleteMeetingMinute meetMinId" + meetMinId + " roomId " + roomId);
	    	try {
	    	    HashMap<String, Object> idInfo = new HashMap<String, Object>();
	    	    idInfo.put("id", meetMinId);
	    	    idInfo.put("roomId", roomId);

	    	    Object obj = sqlMapClient.delete("deleteMeetingMinute", idInfo);
	    	    log.debug(" returned from deleteMeetingMinute  : " + obj);

	    	} catch (SQLException e) {
	    	    log.error(e.getMessage());
	    	}
	    }

	    
}
