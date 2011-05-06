package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.LiveDocConversion;

public class LiveDocConvDAO {

	private static SqlMapClient sqlMapClient =IbatisManager.getSqlMap();
	private static final Logger log = LoggerFactory.getLogger(LiveDocConvDAO.class);
	public static int save(String userID) {
		int conversionID = -1;
		try {
			
			LiveDocConversion l = new LiveDocConversion();
			l.setUserID(userID);
			
			conversionID = (Integer)sqlMapClient.insert("insertLiveDocConversion", l);
			log.debug(""+conversionID);
		} catch (SQLException re) {
			re.printStackTrace();
		}
		return conversionID;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		save("John");
		
	}

}
