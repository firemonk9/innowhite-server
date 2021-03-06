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
@Table(name = "playback_playlist")
public class PlayBackPlayList implements Serializable {

    /**
	 * 
	 */
    // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "inserted_date")
    Date insertedDate;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "room_id")
    String roomName;

    @Column(name = "duration")
    String duration;

    @Column(name = "server")
    String server;

    @Column(name = "size")
    long size;

    @Column(name = "width")
    int width;

    @Column(name = "height")
    int height;

    @Column(name = "youtube_url")
    String youtubeUrl;
    
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public long getSize() {
	return size;
    }

    public void setSize(long size) {
	this.size = size;
    }

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

    public String getServer() {
	return server;
    }

    public void setServer(String server) {
	this.server = server;
    }

    public String getDuration() {
	return duration;
    }

    public void setDuration(String duration) {
	this.duration = duration;
    }

    public String getRoomName() {
	return roomName;
    }

    public void setRoomName(String roomName) {
	this.roomName = roomName;
    }

    public Date getInsertedDate() {
	return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
	this.insertedDate = insertedDate;
    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }
    //
    // public static void main(String[] args) {
    // //whiteboard
    // }
    
    public String toString(){
    	
	StringBuffer sb = new StringBuffer();
	sb.append(" filePath: "+filePath);
	sb.append(" width: "+width);
	sb.append(" height: "+height);
	sb.append(" duration: "+duration);
	sb.append(" size: "+size);
	sb.append(" id: "+id);
	sb.append(" roomName: "+roomName);
	return sb.toString();
    }
}
