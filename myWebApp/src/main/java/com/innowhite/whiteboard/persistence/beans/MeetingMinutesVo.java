
package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;

/**
 * @author tanuja
 * @Date 01-23-2012
 */

public class MeetingMinutesVo {
	
	private int id;
	private String roomId;
	private int userId;
	private String description;
	private Date createdTime;

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
        
     public int getUserId() {
        return userId;
     }
     public void setUserId(int userId) {
        this.userId = userId;
     }
            
     public String getDescription() {
           return description;
     }
     public void setDescription(String description) {
          this.description = description;
     }

    public Date getCreatedTime() {
	return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
	this.createdTime = createdTime;
    }

   
   
}
