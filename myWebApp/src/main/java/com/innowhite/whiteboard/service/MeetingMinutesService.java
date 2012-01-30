package com.innowhite.whiteboard.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.MeetingMinutesVo;
import com.innowhite.whiteboard.persistence.dao.MeetingMinutesDAO;

/** To implement MeetingMinutesService class
 * @author tanuja
 * @Date 01-23-2012
 */

public class MeetingMinutesService {
	
	private static final Logger log = LoggerFactory.getLogger(MeetingMinutesService.class);
	/* To save user entered meeting minutes for specific room
     * roomId = RoomId.
     * description = summary of meeting (meeting minutes)
     * userId = UserId.
     */
	public static int insertMeetingMinutes(String roomId, int userId, String description) {
		log.debug("Entered insertMeetingMinutes roomId" + roomId + " userId " + userId + "  description " + description);
		
		int retMeetMinId=0;
		MeetingMinutesVo meetMinVo = new MeetingMinutesVo();
    	meetMinVo.setRoomId(roomId);
    	meetMinVo.setUserId(userId);
    	meetMinVo.setDescription(description);
   	    	
    	log.debug(" the values are : " + meetMinVo);
    	retMeetMinId=MeetingMinutesDAO.saveMeetingMinutes(meetMinVo);
		return retMeetMinId;
	}
	
	/* To get user entered meeting minutes for specific room
     * roomId = RoomId.
     */	
	public static List<MeetingMinutesVo> getAllMeetingMinutes(String roomId) {
		log.debug("Entered getAllMeetingMinutes roomId" + roomId);
		
		List<MeetingMinutesVo> meetMinList=null;
		meetMinList=MeetingMinutesDAO.getAllMeetingMinutes(roomId);
		return meetMinList;
	}
	
	/* To update selected meeting minute in a room
	 * meetMinId= Meeting Minute Id
     * roomId = RoomId
     * strDesc= Description to update
     */		
	public static void updateMeetingMinute(int meetMinId,String roomId,String strDesc) {
		log.debug("Entered updateMeetingMinute meetMinId"+ meetMinId +"roomId"+ roomId +"strDesc"+strDesc);
		 MeetingMinutesDAO.updateMeetingMinute(meetMinId,roomId,strDesc);
	}
	
	/* To delete selected meeting minute from a room
	 * meetMinId= Meeting Minute Id
     * roomId = RoomId
     */		
	public static void deleteMeetingMinute(int meetMinId,String roomId) {
		log.debug("Entered deleteMeetingMinute meetMinId" + meetMinId + " roomId " + roomId);
		 MeetingMinutesDAO.deleteMeetingMinute(meetMinId,roomId);
	}
	
}
