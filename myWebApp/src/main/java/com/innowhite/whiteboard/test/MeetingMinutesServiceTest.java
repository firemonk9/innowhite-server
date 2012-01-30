package com.innowhite.whiteboard.test;

/*
import java.util.Iterator;
import java.util.List;
import com.innowhite.whiteboard.persistence.beans.MeetingMinutesVo;
import com.innowhite.whiteboard.service.MeetingMinutesService;
*/

/**
 * @author tanuja
 * @Date 01-23-2012
 */

public class MeetingMinutesServiceTest {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String roomId= "2345";
		int userid=21;
		String desc="update testing two";
		int meetMinId=3;
		*/
		
		/* Test For Insertion
		int returnId=MeetingMinutesService.insertMeetingMinutes(roomId,userid,desc);
		System.out.println("============insertion ID========"+returnId);
		*/
		
		/* Test For Display Selection
		List<MeetingMinutesVo> listObj=MeetingMinutesService.getAllMeetingMinutes(roomId);
		System.out.println("======no of rows meeting minutes====="+listObj.size());
		Iterator iterator = listObj.iterator();
        while(iterator.hasNext()){
        	MeetingMinutesVo mmObj=(MeetingMinutesVo)iterator.next();
        	System.out.println("==RoomId=="+mmObj.getRoomId()+"===Descpt==="+mmObj.getDescription());
         }
        */
		
		/* Test For update meeting minutes
		MeetingMinutesService.updateMeetingMinute(meetMinId,roomId,desc);
		*/
		
		/* Test For deletion 
        MeetingMinutesService.deleteMeetingMinute(meetMinId,roomId);
        */
        

		
	}

}
