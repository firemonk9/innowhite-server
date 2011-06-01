package com.innowhite.whiteboard.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.ServerVO;
import com.innowhite.whiteboard.persistence.dao.ServerDAO;

public class LoadBalancerService {

    private static HashMap<String, List<ServerVO>> serversMap = new HashMap<String, List<ServerVO>>();

    private static final Logger log = LoggerFactory.getLogger(LoadBalancerService.class);
    
    public static String getServerURL(String appName, String orgName) {

	if (serversMap != null && serversMap.get(appName) == null) {
	    populateCache(appName);
	}else{
	    
	}
	return null;
    }

    private static void populateCache(String appName) {
	log.info("populateCache  getting server info frmo database... ");
	List<ServerVO> list = ServerDAO.getServers(appName);
	if(list == null || list.size() ==0)
	{
	    log.warn(" there is no matching server for appName :: "+appName);
	}else{
	    serversMap.put(appName, list);
	}
	
    }

}
