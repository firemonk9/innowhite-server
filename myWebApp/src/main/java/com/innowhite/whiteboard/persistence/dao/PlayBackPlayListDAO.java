package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.PlayBackPlayListVO;

public class PlayBackPlayListDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(PlayBackPlayListDAO.class);

    public static void main(String[] args) {
	//

	System.err.println(getPlayList("VideoApp"));

	
    }

    public static List<PlayBackPlayListVO> getPlayList(String roomName) {

	log.debug("Entered getServers");
	log.debug("appName  " + roomName);
	List<PlayBackPlayListVO> value = null;
	try {

	    value = (List) sqlMapClient.queryForList("getPlayListQuery", roomName);

	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

}
