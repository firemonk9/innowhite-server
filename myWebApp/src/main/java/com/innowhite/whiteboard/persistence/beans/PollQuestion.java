package com.innowhite.whiteboard.persistence.beans;

import java.io.Serializable;
import java.util.Date;

/** To implement PollQuestion bean class
 * @author Tanuja
 * @Date Feb 16,2012
 */

public class PollQuestion implements Serializable {
	
	private static final long serialVersionUID = 1L;


    private long id;

    String questType;
    
    int userId;
    
    String questDesc;
    
    Date createdDate;
    
    Date updatedDate;
    
    public void setId(long id) {
    	this.id = id;
    }
    public long getId() {
    	return id;
    }
    
    public String getQuestType() {
		return questType;
	}
	public void setQuestType(String questType) {
		this.questType = questType;
	}
    
    public void setUserId(int userId) {
    	this.userId = userId;
    }
    public int getUserId() {
    	return userId;
    } 
    
    public String getQuestDesc() {
        return questDesc;
    }
    public void setQuestDesc(String questDesc) {
        this.questDesc = questDesc;
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
