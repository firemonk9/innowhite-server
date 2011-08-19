package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;

public class RoomUsersVO {

    private int id;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    
    public String getJoinedConference() {
        return joinedConference;
    }

    public void setJoinedConference(String joinedConference) {
        this.joinedConference = joinedConference;
    }

    public boolean isJoinedPhoneConference() {
        return joinedPhoneConference;
    }

    public void setJoinedPhoneConference(boolean joinedPhoneConference) {
        this.joinedPhoneConference = joinedPhoneConference;
    }

    public boolean isJoinedVoipConference() {
        return joinedVoipConference;
    }

    public void setJoinedVoipConference(boolean joinedVoipConference) {
        this.joinedVoipConference = joinedVoipConference;
    }


    
    private String joinedConference;
    private String roomName;
    private String userId;
    private Date startTime;
    private Date endTime;
    
    private Boolean joinedPhoneConference;
    private Boolean joinedVoipConference;
}
