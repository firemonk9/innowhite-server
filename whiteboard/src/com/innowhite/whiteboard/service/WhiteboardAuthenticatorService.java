package com.innowhite.whiteboard.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.hashing.WhiteBoardSHA1;
import com.innowhite.whiteboard.persistence.beans.OrganizationVO;
import com.innowhite.whiteboard.persistence.beans.RoomVO;
import com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl;
import com.innowhite.whiteboard.util.WhiteBoardUtil;

public class WhiteboardAuthenticatorService {

	
	private static Logger log = Red5LoggerFactory.getLogger(WhiteboardAuthenticatorService.class, InnowhiteConstants.APP_NAME);
	
	public static String  validateRequest(String queryStringWithoutCheckSum, String parentOrg, String checksum){
		
		
		log.debug(" entering  validateRequest queryStringWithoutCheckSum"+queryStringWithoutCheckSum+"   parentOrg  "+parentOrg+"    checksum  "+checksum);
		OrganizationVO orgVO = null;
		String verifierString = "";
		String checksumFromDB="";
		//Get the details for the organization name.
		WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();
		orgVO = wai.getOrganizationDetails(parentOrg);
			orgVO.getExpiryDate();
		
			if(orgVO!=null){
				verifierString = queryStringWithoutCheckSum + orgVO.getSaltKey();
				log.debug("Verifier String: "+verifierString);
				try {
					checksumFromDB = WhiteBoardSHA1.SHA1(verifierString);
					log.debug("check sum from URL: "+checksum);
					log.debug("Check sum from DB: "+checksumFromDB);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		
			if(checksumFromDB.equalsIgnoreCase(checksum))
			{
				return "SUCCESS";
			}
			else
			{
				return "AUTH_FAILED";
			}
	  }
	
	
		 public static String createRoom(String orgName, String roomName, String record, String course, String inetLessonID, String view){
				long today =Calendar.getInstance().getTimeInMillis();	
				log.debug("Current Time "+today);
				int random = (int)(Math.random() * 8847);
				String tod = today+"";
				tod = tod.substring(6);
				String roomId = tod+random;
				log.debug("room ID "+roomId );		
				RoomVO roomVO = new RoomVO();
				roomVO.setOrgName(orgName);
				roomVO.setRoomActive('Y');
				roomVO.setRoomId(roomId);
				roomVO.setRoomName(roomName);
				roomVO.setInsertedDate(WhiteBoardUtil.getDateTime());
				roomVO.setRecord(record);
				roomVO.setCourse(course);
				
				if(view != null && view.contains("PRIVATE"))
					roomVO.setRoomLiveConnectStatus("FORBID");
				else
					roomVO.setRoomLiveConnectStatus("ALLOW");
				
				roomVO.setLessonPlanID(inetLessonID);
				WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();
				boolean b = wai.createRoom(roomVO);
				log.debug("room created "+ b);
			 if(b)
			 {
				 return roomId;
			 }
			 return "";
		 }
		 
		 
		 public static int startRoom(String roomId){
			 //WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();
			 log.debug(" entered startRoom for :"+roomId);
			 int x = WhiteboardAuthenticationDAOImpl.updateStartDate(roomId);
			 log.debug(" updated room info :"+x);
			 return x;
		 }
		 
		 public static int stopRoom(String roomId){
			 //WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();
			 log.debug(" entered stopRoom for :"+roomId);
			 int x = WhiteboardAuthenticationDAOImpl.updateEndDate(roomId);
			 return x;
		 }
		 
		 
		 public static String checkRoomStatus(String room[]){
			 Map<String, String> hmMap =null;
			 WhiteboardAuthenticationDAOImpl wai = new WhiteboardAuthenticationDAOImpl();
			 hmMap = wai.roomStatus(room);
			 StringBuffer sb = new StringBuffer();
			 sb.append("<?xml version='1.0' encoding='UTF-8'?>");
			 sb.append("<response>");
			 for (int i = 0; i < room.length; i++) {
				 StringBuffer sa = new StringBuffer();
				sa.append(" <room> <returnStatus>");
				sa.append(hmMap.get(room[i]));
				sa.append("</returnStatus><roomId>");
				sa.append(room[i]);
				sa.append("</roomId></room>");
				sb.append(sa);
			}
			 sb.append("</response>");
			 return sb.toString();
		 }
		 
		 
		
}
