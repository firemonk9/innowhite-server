package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;


public class LessonPlanVO {

	private int id;
	private String courseId;
	private String lessonPlanId;
	private String lessonPlanXML;
	private Date insertedDate;
	private String orgName;
	
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	//private char roomActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseID(String courseId) {
		this.courseId = courseId;
	}
	public String getLessonPlanId() {
		return lessonPlanId;
	}
	public void setLessonPlanId(String lessonPlanId) {
		this.lessonPlanId = lessonPlanId;
	}
	public String getLessonPlanXML() {
		return lessonPlanXML;
	}
	public void setLessonPlanXML(String lessonPlanXML) {
		this.lessonPlanXML = lessonPlanXML;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	
	
	
}
