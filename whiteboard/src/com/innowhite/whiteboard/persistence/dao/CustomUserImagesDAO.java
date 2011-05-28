package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.red5.whiteboard.Main;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.UserImagesVO;

public class CustomUserImagesDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

	private static Logger log = Red5LoggerFactory.getLogger(CustomUserImagesDAO.class, InnowhiteConstants.APP_NAME);
	
	public int deleteById(int userimageid) {
		int noOfRowsDeleted = 0;

		try {
			noOfRowsDeleted = sqlMapClient.delete("deleteImageByID",
					userimageid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return noOfRowsDeleted;
	}

	
	
	
	
	
	public int deleteByImageGroup(int id, String name) {
		int noOfRowsDeleted = 0;
		Map hmMap = new HashMap();
		hmMap.put("id", id);
		hmMap.put("name", name);
		try {
			noOfRowsDeleted = sqlMapClient.delete("deleteImageByIDName", hmMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return noOfRowsDeleted;
	}

	public List<UserImagesVO> findByProperty(String userName) {
		List<UserImagesVO> al = new ArrayList<UserImagesVO>();
		if (userName == null || userName.equals("")) {
			return al;
		}
		try {
			al = sqlMapClient.queryForList("findByProperty", userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	public List<UserImagesVO> findByConversionId(String conversionId) {
		List<UserImagesVO> al = new ArrayList<UserImagesVO>();
		if (conversionId == null || conversionId.equals("")) {
			return al;
		}
		try {
			al = sqlMapClient.queryForList("findByConversionId", conversionId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	public List<UserImagesVO> findByUserName(String userName) {
		return findByProperty(userName);
	}

	public UserImagesVO findById(java.lang.Integer id) {
		UserImagesVO userImageVO = null;
		try {
			userImageVO = (UserImagesVO) sqlMapClient.queryForObject(
					"findByID", id);
		} catch (SQLException re) {
			re.printStackTrace();
		}
		return userImageVO;
	}

	public static UserImagesVO getUserMediaSource(String id, boolean thumbs) {

		log.debug(" entered  getUserMediaSource " + id);
		try {

			UserImagesVO value=null;
			
				
				value = (UserImagesVO) sqlMapClient.queryForObject(
						"getUserContentSource", id);
				
		
			return value;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

	}

	public int save(UserImagesVO userImagesVO) {
		int rowId = 0;
		try {
			rowId = (Integer) sqlMapClient.insert("saveImage", userImagesVO);
		} catch (SQLException re) {
			re.printStackTrace();
		}
		return rowId;
	}

	public static void main(String[] args) {
		CustomUserImagesDAO cuidao = new CustomUserImagesDAO();
		// int x = cuidao.deleteById(76);
		// log.debug("Deleted image from the table for 76: "+x);

		/*
		 * int y = cuidao.deleteByImageGroup(40886, "Victor");
		 * log.debug("No of rows affected are: "+y);
		 */
		/*
		 * ArrayList al = (ArrayList) cuidao.findByProperty("Victor"); for
		 * (Iterator iterator = al.iterator(); iterator.hasNext();) {
		 * UserImagesVO userImageVO = (UserImagesVO) iterator.next();
		 * log.debug("userImages VO "+userImageVO.getId() +
		 * " userName: "+userImageVO.getUserName()); }
		 */

		UserImagesVO userImageVO = cuidao.findById(5);
		log.debug("userImageVO: " + userImageVO.getImageName());

		UserImagesVO userImage = new UserImagesVO();
		userImage.setUserName("prashanth");
		userImage.setImageURL("John__12750959250985036.jpg");
		userImage.setImageDescription("test description");
		userImage.setImageFolder("true");
		userImage.setImageFolderSeq(4);
		userImage.setImageGroup(234234);
		userImage.setImageName("test");
		userImage.setImageType(2);

		cuidao.save(userImage);

	}

}
