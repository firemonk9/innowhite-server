package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;


public class VideoDataVO {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFlvFilePath() {
		return flvFilePath;
	}
	public void setFlvFilePath(String flvFilePath) {
		this.flvFilePath = flvFilePath;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getVideoType() {
		return videoType;
	}
	public void setVideoType(String videoType) {
		this.videoType = videoType;
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
	private String flvFilePath;
	private String roomName;
	private String videoType;
	private Date startTime;
	private Date endTime;
		
	
}
