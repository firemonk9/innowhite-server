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
@Table(name = "room_users")
public class UserRoomData implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "start_time")
    Date startTime;
    
    
    @Column(name = "end_time")
    Date endTime;
    
    @Column(name = "user_Id")
    String userId; 
    
 
    @Column(name = "room_Id")
    String roomName;
    
    @Column(name = "joined_phone_conference")
    String joinedPhoneConference;
    

    @Column(name = "joined_voip_conference")
    String joinedVoipConference;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    public String getJoinedPhoneConference() {
        return joinedPhoneConference;
    }

    public void setJoinedPhoneConference(String joinedPhoneConference) {
        this.joinedPhoneConference = joinedPhoneConference;
    }

    public String getJoinedVoipConference() {
        return joinedVoipConference;
    }

    public void setJoinedVoipConference(String joinedVoipConference) {
        this.joinedVoipConference = joinedVoipConference;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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
