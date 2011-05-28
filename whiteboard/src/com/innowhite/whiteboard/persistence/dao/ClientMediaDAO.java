package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.red5.whiteboard.Main;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.ClientMediaVO;

public class ClientMediaDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

	static boolean val = false;

	private static Logger log = Red5LoggerFactory.getLogger(ClientMediaDAO.class, InnowhiteConstants.APP_NAME);
	
	public static void main(String[] args) {
//
//		ClientMediaVO clientMediaVO = new ClientMediaVO();
//		clientMediaVO.setContentId("123");
//		clientMediaVO.setContentType("IMAGE");
//		clientMediaVO.setContentSource("shjdhkasdhflksjd");
//		saveMedia(clientMediaVO);
		
		log.debug(checkMediaExists("1234","tre"));
	}

	
	/*saves the media content.*/
	
	public static boolean saveMedia(ClientMediaVO clientMediaVO) {
		boolean roomCreated = false;
		int x = 0;
		try {
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("contentId", clientMediaVO.getContentId());
			map.put("orgName", clientMediaVO.getOrgName());
			
			x = (Integer) sqlMapClient.delete("deleteMedia", map);
			
			x = (Integer) sqlMapClient.insert("saveMedia", clientMediaVO);
			log.debug(" saveMedia returned " + x);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (x > 0) {
			return true;
		} else {
			return roomCreated;
		}
	}


//	/*saves the media content.*/
//	
//	public static boolean updateDocumentXML(String documentId, String documentXML) {
//		boolean roomCreated = false;
//		int x = 0;
//		try {
//			
//			log.debug("Entered update XML documentId ::"+documentId+"    documentXML::   "+documentXML);
//			
//			ClientMediaVO clientMediaVO= new  ClientMediaVO();
//			clientMediaVO.setContentId(documentId);
//			clientMediaVO.setDocumentXML(documentXML);
//			
//			x = (Integer) sqlMapClient.update("updateDocXML", clientMediaVO);
//			log.debug(" saveMedia returned " + x);
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//		if (x > 0) {
//			return true;
//		} else {
//			return roomCreated;
//		}
//	}


	
	/*Returns the source of the media content.*/
	
	public static String checkMediaExists(String contentId, String orgName) {

		log.debug("Entered roomListStatus  contentId"+contentId+"  orgName "+orgName);
		//ClientMediaVO clientMediaVO= new ClientMediaVO();
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();

		try {
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("contentId", contentId);
			map.put("orgName", orgName);
			
		
			
			String  value = (String)sqlMapClient.queryForObject("checkMediaExists", map);
			
			
			
			if(value != null && value.length() > 0){
				return value;
			}	
			
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

		return null;

	}
	
	
/*Returns the source of the media content.*/
	
	public static String getContentSource(String contentId, String orgName) {

		log.debug("Entered roomListStatus");
		log.debug("contentId  "+contentId+" orgName "+orgName);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("contentId", contentId);
		map.put("orgName", orgName);
		
	
			
		try {
			String  value = (String)sqlMapClient.queryForObject("getContentSource", map);
			if(value != null && value.length() > 0){
				return value;
			}	
			
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

		return null;

	}
	
	public static String getDocumentXML(String contentId, String orgName) {

		log.debug("Entered getDocumentXML"+ contentId+"  orgName  "+orgName);
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("contentId", contentId);
		map.put("orgName", orgName);
	
		try {
			String  value = (String)sqlMapClient.queryForObject("getDocumentXML", map);
			if(value != null && value.length() > 0){
				return value;
			}	
			
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

		return null;

	}
	
	

}
