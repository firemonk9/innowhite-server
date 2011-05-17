package com.vo;

public class VideoDisplayVO {

	String username;
	String flvpath;
	boolean audio;
	boolean video;
	int seq;
	
	int videoHeight;
	String streamType; // DESKTOP or VIDEO
	String shareStatus; //START_DESKTOP_SHARE or STOP_DESKTOP_SHARE
	int videoWidth;
	
	
	
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	
	public int getVideoWidth() {
		return videoWidth;
	}

	public void setVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
	}

	public int getVideoHeight() {
		return videoHeight;
	}

	public void setVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
	}

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAudio() {
		return audio;
	}

	public void setAudio(boolean audio) {
		this.audio = audio;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public boolean getVideo() {
		return video;
	}

	
	public String getFlvpath() {
		return flvpath;
	}

	public void setFlvpath(String flvpath) {
		this.flvpath = flvpath;
	}
	
}
