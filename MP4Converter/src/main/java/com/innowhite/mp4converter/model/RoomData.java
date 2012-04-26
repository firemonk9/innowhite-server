package com.innowhite.mp4converter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class RoomData implements Serializable {


	/**
	 * 
	 */
   // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_id")
    String roomName;
   

    @Column(name = "room_detail_xml")
    String roomDetailXml;

    @Column(name = "org_name")
    String orgName;
    
    @Column(name = "source")
    String source;
    
    
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    
    public String getRoomDetailXml() {
        return roomDetailXml;
    }

    public void setRoomDetailXml(String roomDetailXml) {
        this.roomDetailXml = roomDetailXml;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

    public static void main(String[] args) {
	//whiteboard
    }

    
 public String toString(){
	
	StringBuffer sb = new StringBuffer();
	sb.append(" id: "+id);
	
	sb.append(" roomName: "+roomName);
	
	
	
	return sb.toString();
    }
    
}
