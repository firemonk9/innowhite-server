package com.innowhite.PlaybackApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "video_data")
public class VideoData implements Serializable {

    
    
    public static final Logger log = LoggerFactory.getLogger(VideoData.class);
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
    
    @Column(name = "flv_file_path")
    String filePath;
    
    @Column(name = "room_id")
    String roomName;
    
    @Column(name = "video_type")
    String videoType;
    
    @Column(name = "duration")
    String duration;
    
    int width;
    int height;
      
    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
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

  
    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
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
	//whiteboard
    }
    
    
    public String toString(){
	
	StringBuffer sb = new StringBuffer();
	sb.append(" id: "+id);
	sb.append(" startTime: "+startTime);
	sb.append(" endTime: "+endTime);
	sb.append(" filePath: "+filePath);
	sb.append(" roomName: "+roomName);
	sb.append(" videoType: "+videoType);
	sb.append(" width: "+width);
	sb.append(" height: "+height);
	
	
	return sb.toString();
    }
    

}
