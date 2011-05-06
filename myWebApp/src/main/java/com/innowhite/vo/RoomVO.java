package com.innowhite.vo;

import java.util.Date;

public class RoomVO {

	
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public long getMediaTimeNum() {
		return mediaTimeNum;
	}
	public void setMediaTimeNum(long mediaTime) {
		this.mediaTimeNum = mediaTime;
	}
	private int seqNum;
	private long mediaTimeNum;
	
	private boolean previousSession=false;
	
	public boolean isPreviousSession() {
		return previousSession;
	}
	public void setPreviousSession(boolean previousSession) {
		this.previousSession = previousSession;
	}
	private Date mediaDate=null;
	public Date getMediaDate() {
		return mediaDate;
	}
	public void setMediaDate(Date mediaDate) {
		this.mediaDate = mediaDate;
	}
	
	
}
