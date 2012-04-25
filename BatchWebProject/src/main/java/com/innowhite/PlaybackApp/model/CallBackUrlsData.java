package com.innowhite.PlaybackApp.model;

import java.io.Serializable;

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

    @Column(name = "playback_ready_url")
    String playbackReadyUrl;
    
    @Column(name = "source")
    String source;

    public String getPlaybackReadyUrl() {
        return playbackReadyUrl;
    }

    public void setPlaybackReadyUrl(String playbackReadyUrl) {
        this.playbackReadyUrl = playbackReadyUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

}
