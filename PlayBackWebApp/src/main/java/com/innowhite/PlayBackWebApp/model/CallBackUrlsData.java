package com.innowhite.PlayBackWebApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "call_back_urls")
public class CallBackUrlsData implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "org_name")
    String orgName;

    @Column(name = "close_room_url")
    String close_room_url;
    
    @Column(name = "source")
    String source;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getClose_room_url() {
        return close_room_url;
    }

    public void setClose_room_url(String close_room_url) {
        this.close_room_url = close_room_url;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public static void main(String[] args) {
	//whiteboard
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
