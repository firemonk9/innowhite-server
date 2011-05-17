package com.innowhite.whiteboard.service;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.red5.whiteboard.UserCacheService;
import com.innowhite.whiteboard.persistence.beans.ConferenceNumbersVO;
import com.innowhite.whiteboard.persistence.dao.ConferenceMeetingDAO;
import com.innowhite.whiteboard.persistence.dao.RoomUsersDAO;



/*	When a user joins a room, the set up and work is done by this class.
 * 
 * ex : get Conference number.
 * 
 * */
public class JoinRoomService {

	/**
	 * @param args
	 */
	private static Logger log = Red5LoggerFactory.getLogger(JoinRoomService.class, "whiteboard");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ConferenceNumbersVO vo = null;
		vo = setupJoinRoom("123123128","testklkjk99",false);
		
		log.debug(" The .. meetingId: "+vo.getMeetingNumber()+" phone :: "+vo.getPhoneNumber());
		
	}

	
	/*  Returns the conference number and phone number. 
	 * Unique : true if every user should get a unique number
	 * Unique : false if every user should get a same number
	 *  Conference number should be 6 digit number. The first two digits uniquely represent a user.
	 *  
	 * */
	public static synchronized ConferenceNumbersVO setupJoinRoom(String roomId, String userId ,boolean unique) {
		
		log.debug("Entered setupJoinRoom roomId"+roomId+" userId "+userId+"  unique "+unique);
		
		ConferenceNumbersVO obj = getConferenceNumberVO( roomId,  userId , unique);
		if(obj == null)
		{
			log.warn(" Conference object is null ...  ");
		}
		
		String confNumber = ""+obj.getMeetingNumber();
		
		UserCacheService.addInnoUniqueIDUser(confNumber,userId);
		
		// get the last 4 digits from the conf number
		confNumber = confNumber.substring(2);
		// set cache for red5. this will be replaced with messaging call.
		UserCacheService.addConfRoom(confNumber, roomId);
	//	UserCacheService.addparticipantIDUser(participantId, innoUniqueId);
		return obj;
		
	}
	
	
	private static ConferenceNumbersVO getConferenceNumberVO(String roomId, String userId ,boolean unique){
		
		log.debug("Entered getConferenceNumberVO roomId"+roomId+" userId "+userId+"  unique "+unique);
		// check if there are any users in the conference room.
		String val = RoomUsersDAO.selectCurrentConfNumber(roomId);
		log.debug(" current Conference number for roomId  "+val);
		// there is no body else in the conference so set up a new conference number
		if(val == null){
			
			ConferenceNumbersVO confVO = ConferenceMeetingDAO.getConfMeetingNumber();
			if(confVO != null){
					
				int meetingId = Integer.parseInt("10"+confVO.getMeetingNumber());
				confVO.setMeetingNumber(meetingId);
				RoomUsersDAO.save(userId, roomId, String.valueOf(meetingId));
				log.debug(" Setting the conference number for the first time meetingId: "+confVO.getMeetingNumber()+" phone :: "+confVO.getPhoneNumber());
				return confVO;
			}
		}
		else{
			// if there are users in the conf room already.
			
			// get phone number
			ConferenceNumbersVO confVO = ConferenceMeetingDAO.getConfPhoneNumber();
			
			// check if this user already exisits
			String conf = RoomUsersDAO.getConfNumber(userId, roomId);
			if(conf!= null)
			{
				//  if the user exists is true than send the same number
				int meeting= Integer.parseInt(conf);
				confVO.setMeetingNumber(meeting);
				log.debug(" The user already exists .. meetingId: "+confVO.getMeetingNumber()+" phone :: "+confVO.getPhoneNumber());
				return confVO;
			}
			else{	
				// if the user does nto exist already, check for unique.
				if(unique == true){
					
					// if unique is true, the below code sends a unique number starting from 101000, 111000,121000 and so on.
					String firstTwoDigits =val.substring(0, 2);
					
					int counter =Integer.parseInt(firstTwoDigits);
					counter = counter+1;
					String meetingNumber = counter+val.substring(2);
					
					log.debug(" val= "+val+" fir "+firstTwoDigits+"  meetingNumber "+meetingNumber);
					
					int meeting= Integer.parseInt(meetingNumber);
					confVO.setMeetingNumber(meeting);
					RoomUsersDAO.updateUserInRoom(userId, roomId);
					log.debug(" The user does not exists and unique is true.. meetingId: "+confVO.getMeetingNumber()+" phone :: "+confVO.getPhoneNumber());
					
					
				}else{
					int meeting= Integer.parseInt(val);
					confVO.setMeetingNumber(meeting);
					
					log.debug(" The user does not exists and unique is false.. meetingId: "+confVO.getMeetingNumber()+" phone :: "+confVO.getPhoneNumber());
					
					
				}
				RoomUsersDAO.save(userId, roomId, String.valueOf(confVO.getMeetingNumber()));
				return confVO;
			}
			
		}
		return null;
		
	}

}
