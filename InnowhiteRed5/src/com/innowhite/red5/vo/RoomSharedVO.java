package com.innowhite.red5.vo;

import java.io.Serializable;

public class RoomSharedVO implements Serializable {

	private boolean lock;

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

	private int roomStartTime;
	private int roomEndTime;

}
