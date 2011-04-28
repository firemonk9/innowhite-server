package com.innowhite.whiteboard.persistence.beans;

public class ConferenceNumbersVO {

	
	private String phoneNumber;
	private int meetingNumber;
	private String roomId;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public void setMeetingNumber(int meetingNumber) {
		this.meetingNumber = meetingNumber;
	}
	public int getMeetingNumber() {
		return meetingNumber;
	}

}
