package com.innowhite.PlayBackWebApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "organization")
public class OrganizationData implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getRoomCallback() {
        return roomCallback;
    }

    public void setRoomCallback(String roomCallback) {
        this.roomCallback = roomCallback;
    }

    @Column(name = "start_date")
    Date startDate;
    
    
    @Column(name = "end_date")
    Date endDate;
    
    @Column(name = "parentOrg")
    String parentOrg;
    
    @Column(name = "orgName")
    String orgName;
      
    
    @Column(name = "room_callback")
    String roomCallback;
   
    
    
    

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

  

}
