/**
 * 
 */
package com.innowhite.PlayBackWebApp.model;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** To implement PollAnswerOptions bean class
 * @author Tanuja
 * @Date Feb 06,2012
 */

@Entity
@Table(name = "poll_answer_options")
public class PollAnswerOptions implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "quest_id")
    int questId;
    
    @Column(name = "option_id")
    int optionId;
    
    @Column(name = "option_desc")
    String optionDesc;
    
    @Column(name = "created_date")
    Date createdDate;
    
    @Column(name = "updated_date")
    Date updatedDate;
    
    public void setId(long id) {
    	this.id = id;
    }
    public long getId() {
    	return id;
    }
    
    public int getQuestId() {
    	return questId;
    }
    public void setQuestId(int questId) {
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
