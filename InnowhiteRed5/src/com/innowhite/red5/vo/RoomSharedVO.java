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
