package com.innowhite.whiteboard.persistence.beans;


public class RoomVO {

	private int id;
	private String roomId;
	private String roomName;
	private String orgName;
	private char roomActive;
	private int usersCount;
	private int viewCount;
	private String groupLeader;
	private String insertedDate;
	private String startDate;
	private String endDate;
	private String record;
	private String course;
	private String lessonPlanId;
	private String roomLiveConnectStatus;
	private String source;
	
	
	
	
	
	

	public String getRoomLiveConnectStatus() {
		return roomLiveConnectStatus;
	}
	public void setRoomLiveConnectStatus(String roomLiveConnectStatus) {
		this.roomLiveConnectStatus = roomLiveConnectStatus;
	}
	public String getLessonPlanID() {
		return lessonPlanId;
	}
	public void setLessonPlanID(String inetLessonID) {
		this.lessonPlanId = inetLessonID;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public char getRoomActive() {
		return roomActive;
	}
	public void setRoomActive(char roomActive) {
		this.roomActive = roomActive;
	}
	public int getUsersCount() {
		return usersCount;
	}
	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getGroupLeader() {
		return groupLeader;
	}
	public void setGroupLeader(String groupLeader) {
		this.groupLeader = groupLeader;
	}
	public String getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(String insertedDate) {
		this.insertedDate = insertedDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
	
}
