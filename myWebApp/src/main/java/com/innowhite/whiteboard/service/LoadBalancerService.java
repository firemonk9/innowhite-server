package com.innowhite.whiteboard.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.ServerVO;
import com.innowhite.whiteboard.persistence.dao.ServerDAO;

public class LoadBalancerService {

    // stores appName vs list of servers
    private static HashMap<String, List<ServerVO>> serversMap = new HashMap<String, List<ServerVO>>();

    // stores appName vs current counter
    private static HashMap<String, Integer> serversCounter = new HashMap<String, Integer>();

    private static final Logger log = LoggerFactory.getLogger(LoadBalancerService.class);

    public static void forceClearCache() {
	log.info("clearing server_data cache ...");
	serversMap = new HashMap<String, List<ServerVO>>();
	serversCounter = new HashMap<String, Integer>();
    }

    /*
     * this is a simple round robin algorithem. It returns next server addr to
     * the request
     */
    public static synchronized ServerVO getServerURL(String appName, String orgName) {

	if (serversMap != null && serversMap.get(appName) == null) {
	    populateCache(appName);
	}

	List<ServerVO> serverList = serversMap.get(appName);
	if (serverList != null && serverList.size() > 0) {

	    int val = serversCounter.get(appName);

	    log.debug("appName:  " + appName + "  val " + val + " serverList.size " + serverList.size());
	    ServerVO obj = serverList.get(val);
	    if (obj != null) {
		if ((val + 1) == serverList.size())
		    val = 0;
		else
		    val++;
		serversCounter.put(appName, val);

		return obj;
	    }
	}

	log.warn(" did not find any server for appName:: "+appName);
	
	return null;
    }

    /* populates the cache */
    private static void populateCache(String appName) {
	log.info("populateCache  getting server info frmo database... ");
	List<ServerVO> list = ServerDAO.getServers(appName);
	if (list == null || list.size() == 0) {
	    log.warn(" there is no matching server for appName :: " + appName);
	} else {
	    serversMap.put(appName, list);
	    serversCounter.put(appName, 0);
	}

    }

}
