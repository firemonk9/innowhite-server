package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;

public class AudioDataVO {

    private int id;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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

    private Date startTime;
    private Date endTime;

}
