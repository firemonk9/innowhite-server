package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.ServerVO;

public class ServerDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(ServerDAO.class);

    public static void main(String[] args) {
	//

	System.err.println(getServers("VideoApp"));

	
    }

    public static List<ServerVO> getServers(String appName) {

	log.debug("Entered getServers");
	log.debug("appName  " + appName);
	List<ServerVO> value = null;
	try {

	    value = (List) sqlMapClient.queryForList("getServerQuery", appName);

	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

}
