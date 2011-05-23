package com.innowhite.PlaybackApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "session_recording")
public class SessionRecordings implements Serializable {

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

    @Column(name = "room_id")
    String roomName;

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

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public static void main(String[] args) {
	// whiteboard
    }

}
