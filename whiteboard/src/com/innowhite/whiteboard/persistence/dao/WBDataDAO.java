package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.red5.whiteboard.Main;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.LessonPlanVO;

public class WBDataDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

	static boolean val = false;
	private static Logger log = Red5LoggerFactory.getLogger(WBDataDAO.class, InnowhiteConstants.APP_NAME);
	public static void main(String[] args) {

		LessonPlanVO lessonPlanVO = new LessonPlanVO();
		lessonPlanVO.setCourseID("123");
		lessonPlanVO.setLessonPlanId("asdasdasdasd");
		lessonPlanVO.setLessonPlanXML("sfkdjhksalfhkljashdfkljsahdfkljhasdklfjhs sdkfjh akslfklasjdf kl");
		//lessonPlanVO.setContentSource("shjdhkasdhflksjd");
//		saveMedia(clientMediaVO);
//		
//		log.debug(getLessonPlanXML("1234"));
	//	WBDataDAO.saveLessonPlanXML(lessonPlanVO);
		
		log.debug(WBDataDAO.getLessonPlanXML("123","asdasdasdasd"));
		
		
	}

	
	/*saves the media content.*/
	
	public static boolean saveWBData(LessonPlanVO lessonPlanVO) {
		boolean roomCreated = false;
		int x = 0;
		try {
			
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("courseId", lessonPlanVO.getCourseId());
			m.put("lessonPlanId", lessonPlanVO.getLessonPlanId());
			
			x = (Integer) sqlMapClient.delete("deleteLessonPlanXML", m);
			
			x = (Integer) sqlMapClient.insert("saveLessonPlanXML", lessonPlanVO);
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

	
	/*Returns the lesson plan XML.*/
	
	public static String getLessonPlanXML(String courseId,String lessonPlanId) {

		log.debug("Entered getLessonPlanXML");
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		

		try {
			
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("courseId", courseId);
			m.put("lessonPlanId", lessonPlanId);
			
			
			String  value = (String)sqlMapClient.queryForObject("getLessonPlanXML", m);
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
