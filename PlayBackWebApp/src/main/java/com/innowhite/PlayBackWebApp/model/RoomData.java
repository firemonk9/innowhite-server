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
@Table(name = "room")
public class RoomData implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "start_date")
    Date startTime;
    
    
    @Column(name = "end_date")
    Date endTime;
    
     
    @Column(name = "room_id")
    String roomName;
    
    @Column(name = "org_name")
    String orgName;
    
    
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
      
    public String getRoomName() {
	return roomName;
    }

    public void setRoomName(String roomName) {
	this.roomName = roomName;
    }

   
    public Date getStartTime() {
	return startTime;
    }

    public void setStartTime(Date startTime) {
	this.startTime = startTime;
    }
    
    public Date getEndTime() {
	return endTime;
    }

    public void setEndTime(Date endTime) {
	this.endTime = endTime;
    }

  
    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public static void main(String[] args) {
	//whiteboard
    }

}
