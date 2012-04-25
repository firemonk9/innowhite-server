package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.CallBackURLsVO;
import com.innowhite.whiteboard.persistence.beans.OrganizationVO;

public class CallBackUrlsDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(CallBackUrlsDAO.class);

    public static void main(String[] args) {
	//

	// CallBackURLsVO ovo = new CallBackURLsVO();
	//
	// ovo.setHostName("room192");

	System.err.println(getCallBackVO("room2"));

    }

    public static CallBackURLsVO getCallBackVO(String roomId) {

	log.debug("Entered getCallBackVO");
	log.debug("host  " + roomId);
	CallBackURLsVO value = null;
	try {

	    value = (CallBackURLsVO) sqlMapClient.queryForObject("getCallBackUrls", roomId);

	    return value;
	} catch (SQLException e) {
	    log.error(e.getMessage(), e);
	    return null;
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    return null;
	}

    }

    /*
     * returns list of VideoDataVO.
     */
    public static void saveOrganization(OrganizationVO orgVo) {

	log.debug("Entered saveOrganization");

	try {

	    Object obj = sqlMapClient.insert("saveOrganization", orgVo);
	    log.debug(" returned from saveOrganization is : " + obj);

	} catch (SQLException e) {
	    log.error(e.getMessage());

	}

    }

    /*
     * returns list of VideoDataVO.
     */
    public static void updateOrganization(OrganizationVO orgVo) {

	log.debug("Update Organization");

	try {

	    HashMap<String, Object> m = new HashMap<String, Object>();
	    m.put("orgName", orgVo.getOrgName());
	    m.put("parentOrg", orgVo.getParentOrg());
	    m.put("orgType", orgVo.getOrgType());
	    m.put("saltKey", orgVo.getSaltKey());
	    m.put("roomCallback", orgVo.getRoomCallback());
	    m.put("expiryDate", orgVo.getExpiryDate());

	    Object obj = sqlMapClient.update("updateOrganization", m);
	    log.debug(" returned from updateOrganization is : " + obj);

	} catch (SQLException e) {
	    log.error(e.getMessage());

	}

    }

    /*
     * returns list of VideoDataVO.
     */
    public static void deleteOrganization(String orgName, String parentOrg) {

	log.debug("Entered deleteOrganization");

	try {

	    HashMap<String, Object> m = new HashMap<String, Object>();
	    m.put("orgName", orgName);
	    m.put("parentOrg", parentOrg);

	    Object obj = sqlMapClient.delete("deleteOrganization", m);
	    log.debug(" returned from deleteOrganization  : " + obj);

	} catch (SQLException e) {
	    log.error(e.getMessage());

	}

    }

}
