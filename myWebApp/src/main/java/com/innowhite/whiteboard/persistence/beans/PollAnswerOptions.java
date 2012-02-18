package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;
import java.io.Serializable;

/** To implement PollAnswerOptions bean class
 * @author Tanuja
 * @Date Feb 16,2012
 */

public class PollAnswerOptions implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private long id;

    long questId;
    
    int optionId;
    
    String optionDesc;
    
    Date createdDate;
    
    Date updatedDate;
    
    public void setId(long id) {
    	this.id = id;
    }
    public long getId() {
    	return id;
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
    
    public String getOptionDesc() {
        return optionDesc;
    }
    public void setOptionDesc(String optionDesc) {
        this.optionDesc = optionDesc;
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
