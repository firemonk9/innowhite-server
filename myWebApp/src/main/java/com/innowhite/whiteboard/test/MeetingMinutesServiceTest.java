package com.innowhite.whiteboard.test;

import java.util.Iterator;
import java.util.List;

import com.innowhite.whiteboard.persistence.beans.MeetingMinutesVo;
import com.innowhite.whiteboard.service.MeetingMinutesService;
import com.innowhite.whiteboard.service.PollingService;

/**
 * @author tanuja
 * @Date 01-23-2012
 */

public class MeetingMinutesServiceTest {

		String roomId= "2345";
		int userid=21;
		String desc="update testing two";
		int meetMinId=3;
		
		public void insertMeetingMinutes(){
			int returnId=MeetingMinutesService.insertMeetingMinutes(roomId,userid,desc);
			System.out.println("============insertion ID========"+returnId);
		}
		
		public void displayMeetingMinutes(){
			List<MeetingMinutesVo> listObj=MeetingMinutesService.getAllMeetingMinutes(roomId);
			System.out.println("======no of rows meeting minutes====="+listObj.size());
			Iterator iterator = listObj.iterator();
	        while(iterator.hasNext()){
	        	MeetingMinutesVo mmObj=(MeetingMinutesVo)iterator.next();
	        	System.out.println("==RoomId=="+mmObj.getRoomId()+"===Descpt==="+mmObj.getDescription());
	         }
		}
		
		public void updateMeetingMinutes(){
			MeetingMinutesService.updateMeetingMinute(meetMinId,roomId,desc);
		}
		
		public void deleteMeetingMinutes(){
			MeetingMinutesService.deleteMeetingMinute(meetMinId,roomId);
		}
		
		public void getPollId(){
			String pollIdXml=PollingService.getPollID();
			System.out.println("======pollIdXml====="+pollIdXml);
		}
		
		public static void main(String[] args) {
			
			MeetingMinutesServiceTest mmObj =new MeetingMinutesServiceTest();
			
			//mmObj.insertMeetingMinutes();
			//mmObj.displayMeetingMinutes();
			//mmObj.updateMeetingMinutes();
			//mmObj.deleteMeetingMinutes();
			
			
			
			mmObj.getPollId();
		
		}

}
