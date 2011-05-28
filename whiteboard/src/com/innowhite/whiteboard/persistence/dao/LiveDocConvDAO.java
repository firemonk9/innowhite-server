package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.red5.whiteboard.Main;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.LiveDocConversion;

public class LiveDocConvDAO {

	private static SqlMapClient sqlMapClient =IbatisManager.getSqlMap();
	private static Logger log = Red5LoggerFactory.getLogger(LiveDocConvDAO.class, InnowhiteConstants.APP_NAME);
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
