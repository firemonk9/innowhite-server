package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;
import java.io.Serializable;

/** To implement PollUserAnswers bean class
 * @author Tanuja
 * @Date Feb 06,2012
 */

public class PollUserAnswers implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private long id;

    int userId;
    
    long questId;
    
    int optionId;
    
    Date createdDate;
    
    Date updatedDate;
    
    String roomId;
    
    public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public void setId(long id) {
    	this.id = id;
    }
    public long getId() {
    	return id;
    }
    
    public void setUserId(int userId) {
    	this.userId = userId;
    }
    public int getUserId() {
    	return userId;
    } 
    
    public long getQuestId() {
    	return questId;
    }
    public void setQuestId(long questId) {
    	this.questId = questId;
    }
    
    public void setOptionId(int optionId) {
    	this.optionId = optionId;
    }
    public int getOptionId() {
    	return optionId;
    } 
    
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }
    public Date getCreatedDate() {
    	return createdDate;
    }
    
    public void setUpdatedDate(Date updatedDate) {
    	this.updatedDate = updatedDate;
    }
    public Date getUpdatedDate() {
    	return updatedDate;
    }

      
    
}
