package com.innowhite.PlayBackWebApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** To implement PollQuestion bean class
 * @author Tanuja
 * @Date Feb 06,2012
 */

@Entity
@Table(name = "poll_question")
public class PollQuestion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "quest_type")
    String questType;
    
	@Column(name = "user_id")
    int userId;
    
    @Column(name = "quest_desc")
    String questDesc;
    
    @Column(name = "created_date")
    Date createdDate;
    
    @Column(name = "updated_date", nullable= true)
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
