package com.innowhite.whiteboard.persistence.beans;

import java.sql.Date;

public class OrganizationVO {

    private int id;
    private String orgId;
    private String parentOrg;
    private String orgName;
    private Date startDate;
    private Date expiryDate;
    private String saltKey;
    private Date insertedDate;
    private Date updatedDate;
    private String orgType;
    private String roomCallback;

   
  

    public String getRoomCallback() {
	return roomCallback;
    }

    public void setRoomCallback(String roomCallback) {
	this.roomCallback = roomCallback;
    }

    public String getOrgType() {
	return orgType;
    }

    public void setOrgType(String orgType) {
	this.orgType = orgType;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getOrgId() {
	return orgId;
    }

    public void setOrgId(String orgId) {
	this.orgId = orgId;
    }

    public String getParentOrg() {
	return parentOrg;
    }

    public void setParentOrg(String parentOrg) {
	this.parentOrg = parentOrg;
    }

    public String getOrgName() {
	return orgName;
    }

    public void setOrgName(String orgName) {
	this.orgName = orgName;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getExpiryDate() {
	return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
	this.expiryDate = expiryDate;
    }

    public String getSaltKey() {
	return saltKey;
    }

    public void setSaltKey(String saltKey) {
	this.saltKey = saltKey;
    }

    public Date getInsertedDate() {
	return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
	this.insertedDate = insertedDate;
    }

    public Date getUpdatedDate() {
	return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("Org Id: " + orgId);
	sb.append("parentOrg : " + parentOrg);
	sb.append("org name: " + orgName);
	sb.append("start date: " + startDate);
	sb.append("expiry date: " + expiryDate);
	sb.append("saltkey: " + saltKey);
	sb.append("insertedDate: " + insertedDate);
	sb.append("updatedDate: " + updatedDate);
	sb.append("roomCallback: " + roomCallback);
	sb.append("orgType: " + orgType);
	
	return sb.toString();
    }

}
