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
//    public static void main(String[] args) {
//	//whiteboard
//    }

}
