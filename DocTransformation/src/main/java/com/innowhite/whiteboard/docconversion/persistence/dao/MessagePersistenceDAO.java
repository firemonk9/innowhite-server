package com.innowhite.whiteboard.docconversion.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.docconversion.vo.UserImagesVO;
import com.innowhite.whiteboard.persistence.IbatisManager;

public class MessagePersistenceDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    public UserImagesVO getUserImageVOByID(int id) throws SQLException {
	return (UserImagesVO) sqlMapClient.queryForObject("findByID", id);
    }

    public int saveImage(UserImagesVO userImagesVO) {
	int noOfRowsUpdated = 0;
	try {
	    noOfRowsUpdated = (Integer) sqlMapClient.insert("saveImage", userImagesVO);
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
	    noOfRowsUpdated = sqlMapClient.update("updateUserMediaByConversionID", hMap);
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
	    noOfRowsUpdated = sqlMapClient.update("updateLDCForSWFByConvID", hMap);
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
	    noOfRowsUpdated = sqlMapClient.update("updateLDCForThumbByConvID", hMap);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return noOfRowsUpdated;
    }

    public static void selectTestSQL() {
	try {
	    sqlMapClient.queryForObject("findTestSQL");
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	// ApplicationContext context = new
	// ClassPathXmlApplicationContext("root-context.xml");
	// MessagePersistenceDAO mdao =
	// (MessagePersistenceDAO)context.getBean("messagePersistenceDAO");
	// MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//	MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//	UserImagesVO userImageVO = new UserImagesVO();
//	try {
//	    userImageVO = mdao.getUserImageVOByID(580);
//	    System.out.println(" UserImage VO: " + userImageVO);
//	} catch (SQLException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
	
	
	selectTestSQL();
	
    }

}
