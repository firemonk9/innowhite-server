package com.innowhite.whiteboard.persistence.beans;


public class CallBackURLsVO {

	public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

   
    public String getCloseRoomUrl() {
	return closeRoomUrl;
    }

    public void setCloseRoomUrl(String closeRoomUrl) {
	this.closeRoomUrl = closeRoomUrl;
    }

    public String getPlaybackReadyUrl() {
	return playbackReadyUrl;
    }

    public void setPlaybackReadyUrl(String playbackReadyUrl) {
	this.playbackReadyUrl = playbackReadyUrl;
    }

    public String getLockRoomUrl() {
	return lockRoomUrl;
    }

    public void setLockRoomUrl(String lockRoomUrl) {
	this.lockRoomUrl = lockRoomUrl;
    }
    public String getInviteSendEmailsUrl() {
        return inviteSendEmailsUrl;
    }

    public void setInviteSendEmailsUrl(String inviteSendEmailsUrl) {
        this.inviteSendEmailsUrl = inviteSendEmailsUrl;
    }
    
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

    private int id;
    private String orgName;
    private String closeRoomUrl;
    private String playbackReadyUrl;
    private String lockRoomUrl;
    private String inviteSendEmailsUrl;
    private String source;

   

    @Override
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("orgName : " + orgName);
	sb.append("\n closeRoomUrl : " + closeRoomUrl);
	sb.append("\n playbackReadyUrl: " + playbackReadyUrl);
	sb.append("\n lockRoomUrl: " + lockRoomUrl);
	sb.append("\n inviteSendEmailsUrl: " + inviteSendEmailsUrl);
	sb.append("\n source: " + source);

	return sb.toString();
    }

}
