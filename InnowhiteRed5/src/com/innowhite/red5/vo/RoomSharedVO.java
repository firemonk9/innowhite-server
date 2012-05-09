package com.innowhite.red5.vo;

import java.io.Serializable;

public class RoomSharedVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean lock;
	private String presenter;
	private String moderator;
	private int roomStartTime;
	private int roomEndTime;
	private boolean recording;
	private boolean webinar;
	private boolean muteAudio;
	private boolean autoJoin;
	private boolean privateChat;
	
	
	public boolean isPrivateChat() {
		return privateChat;
	}

	public void setPrivateChat(boolean privateChat) {
		this.privateChat = privateChat;
	}

	public boolean isAutoJoin() {
		return autoJoin;
	}

	public void setAutoJoin(boolean autoJoin) {
		this.autoJoin = autoJoin;
	}

	public boolean isWebinar() {
		return webinar;
	}

	public void setWebinar(boolean webinar) {
		this.webinar = webinar;
	}

	public boolean isMuteAudio() {
		return muteAudio;
	}

	public void setMuteAudio(boolean muteAudio) {
		this.muteAudio = muteAudio;
	}

	


	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	public String getPresenter() {
		return presenter;
	}

	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}

	public String getModerator() {
		return moderator;
	}

	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public int getRoomStartTime() {
		return roomStartTime;
	}

	public void setRoomStartTime(int roomStartTime) {
		this.roomStartTime = roomStartTime;
	}

	public int getRoomEndTime() {
		return roomEndTime;
	}

	public void setRoomEndTime(int roomEndTime) {
		this.roomEndTime = roomEndTime;
	}

	
}
