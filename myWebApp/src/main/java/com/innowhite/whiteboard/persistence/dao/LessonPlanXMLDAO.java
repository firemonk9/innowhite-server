package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.LessonPlanVO;


public class LessonPlanXMLDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

//	/private static Logger log = Red5LoggerFactor.getLogger(LessonPlanXMLDAO.class,"whiteboard");
	private static final Logger log = LoggerFactory.getLogger(LessonPlanXMLDAO.class);
	static boolean val = false;

	public static void main(String[] args) {

		LessonPlanVO lessonPlanVO = new LessonPlanVO();
		lessonPlanVO.setCourseID("123");
		lessonPlanVO.setLessonPlanId("asdasdasdasd");
		lessonPlanVO.setLessonPlanXML("sfkdjhksalfhkljashdfkljsahdfkljhasdklfjhs sdkfjh akslfklasjdf kl");
		
		//lessonPlanVO.setContentSource("shjdhkasdhflksjd");
//		saveMedia(clientMediaVO);
//		
//		log.debug(getLessonPlanXML("1234"));
		LessonPlanXMLDAO.saveLessonPlanXML(lessonPlanVO);
		
		log.debug(LessonPlanXMLDAO.getLessonPlanXML("123","asdasdasdasd",""));
		
		
	}

	
	/*saves the media content.*/
	
	public static boolean saveLessonPlanXML(LessonPlanVO lessonPlanVO) {
		boolean roomCreated = false;
		int x = 0;
		try {
			
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("courseId", lessonPlanVO.getCourseId());
			m.put("lessonPlanId", lessonPlanVO.getLessonPlanId());
			m.put("orgName", lessonPlanVO.getOrgName());
			
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
	
	public static String getLessonPlanXML(String courseId,String lessonPlanId, String orgName) {

		log.debug("Entered getLessonPlanXML ");
		log.debug(" courseId "+courseId+"  lessonPlanId "+lessonPlanId+" orgName "+orgName);
		// Map<String, String> roomStatusResultsMap = new HashMap<String,
		// String>();
		

		try {
			
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("courseId", courseId);
			m.put("lessonPlanId", lessonPlanId);
			m.put("orgName", orgName);
			//m.put("orgName", lessonPlanVO.getOrgName());
			
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
