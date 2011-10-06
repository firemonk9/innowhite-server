package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.OrganizationVO;

public class OrganizationDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(OrganizationDAO.class);

    public static void main(String[] args) {
	//

	OrganizationVO ovo = new OrganizationVO();
	ovo.setExpiryDate(new java.sql.Date(new Date().getTime()));
	ovo.setStartDate(new java.sql.Date(new Date().getTime()));
	ovo.setOrgName("junk");
	ovo.setParentOrg("parentJunk");
	ovo.setSaltKey("sdfasdfdfsds");
	ovo.setExpiryDate(ovo.getExpiryDate());

	ovo.setOrgType("LEVEL_1");

	 saveOrganization(ovo);
	updateOrganization(ovo);
	deleteOrganization("junk","parentJunk");
	
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
