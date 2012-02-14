package com.innowhite.PlayBackWebApp.model;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** To implement PollUserAnswers bean class
 * @author Tanuja
 * @Date Feb 06,2012
 */

@Entity
@Table(name = "poll_user_answers")
public class PollUserAnswers implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    int userId;
    
    @Column(name = "quest_id")
    int questId;
    
    @Column(name = "option_id")
    int optionId;
    
    @Column(name = "created_date")
    Date createdDate;
    
    @Column(name = "updated_date")
    Date updatedDate;
    
    @Column(name = "room_id")
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
