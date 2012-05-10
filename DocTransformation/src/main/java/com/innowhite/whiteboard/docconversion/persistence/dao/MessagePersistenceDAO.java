package com.innowhite.whiteboard.docconversion.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.docconversion.vo.LiveDocConversionVO;
import com.innowhite.whiteboard.docconversion.vo.UserImagesVO;
import com.innowhite.whiteboard.persistence.IbatisManager;

public class MessagePersistenceDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();
	private static final Logger log = LoggerFactory
			.getLogger(MessagePersistenceDAO.class);

	public UserImagesVO getUserImageVOByID(int id) throws SQLException {
		return (UserImagesVO) sqlMapClient.queryForObject("findByID", id);
	}

	public int saveImage(UserImagesVO userImagesVO) {
		int noOfRowsUpdated = 0;
		try {
			noOfRowsUpdated = (Integer) sqlMapClient.insert("saveImage",
					userImagesVO);
		} catch (SQLException re) {
			re.printStackTrace();
		}
		return noOfRowsUpdated;
	}

	public int updateImageURL(int conversionID, String mediaURL, int sequence) {
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("mediaURL", mediaURL);
		hMap.put("conversionID", conversionID);
		hMap.put("sequence", sequence);
		int noOfRowsUpdated = 0;
		try {
			noOfRowsUpdated = sqlMapClient.update(
					"updateUserMediaByConversionID", hMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noOfRowsUpdated;
	}

	public int updateLDCSWFURL(int conversionID, String created) {
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("swfCreated", created);
		hMap.put("conversionID", conversionID);
		int noOfRowsUpdated = 0;
		try {
			noOfRowsUpdated = sqlMapClient.update("updateLDCForSWFByConvID",
					hMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noOfRowsUpdated;
	}

	public int updateLDCThumbnailURL(int conversionID, String created) {
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("thumbNailCreated", created);
		hMap.put("conversionID", conversionID);
		int noOfRowsUpdated = 0;
		try {
			noOfRowsUpdated = sqlMapClient.update("updateLDCForThumbByConvID",
					hMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return noOfRowsUpdated;
	}

	public static void selectTestSQL() {
		try {
			sqlMapClient.queryForObject("findTestSQL");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int saveLDC(String userId, String fromEmail) {
		log.info("Entering saveLDC....userId "+userId+ "....fromEmail:::"+fromEmail);
		int conversionID = -1;
		try {

			LiveDocConversionVO ldcObj = new LiveDocConversionVO();
			ldcObj.setUserID(userId);
			ldcObj.setSenderEmail(fromEmail);
			conversionID = (Integer) sqlMapClient.insert(
					"insertLiveDocConversion", ldcObj);
			log.debug(" In saveLDC ==== conversionID ::::" + conversionID);
		} catch (SQLException re) {
			re.printStackTrace();
		}
		log.info("Exiting saveLDC....conversionID:::"+conversionID);
		return conversionID;
	}

	public static void main(String[] args) {
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("root-context.xml");
		// MessagePersistenceDAO mdao =
		// (MessagePersistenceDAO)context.getBean("messagePersistenceDAO");
		// MessagePersistenceDAO mdao = new MessagePersistenceDAO();
		// MessagePersistenceDAO mdao = new MessagePersistenceDAO();
		// UserImagesVO userImageVO = new UserImagesVO();
		// try {
		// userImageVO = mdao.getUserImageVOByID(580);
		// System.out.println(" UserImage VO: " + userImageVO);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		selectTestSQL();

	}

}
