package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;

/** To implement PollingDAO class
 * @author Tanuja
 * @Date Feb 15, 2012
 */
public class PollingDAO {
	 private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();
	 private static final Logger log = LoggerFactory.getLogger(PollingDAO.class);
	
	 /* To get the pollid for new poll   */
	public static int getpollId() {
    	log.debug("Entered getpollId");
    	int retunrVal=0; int maxId=0;
    	try {
    		maxId=(Integer)sqlMapClient.queryForObject("pollQuestMaxId");
    	    log.debug(" returned from getpollId is : " + maxId);
    	    retunrVal=maxId+1;
    	} catch (SQLException e) {
    	    log.error(e.getMessage());
    	}
    	return retunrVal;
      }
    

}
