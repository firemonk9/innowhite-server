package com.innowhite.whiteboard.docconversion.vo;

public class LiveDocConversionVO {

	private int conversionID;
	private String thumbnailsCreated;
	private String swfCreated;
	private String userID;
	private String senderEmail;
	
	
	public int getConversionID() {
		return conversionID;
	}
	public void setConversionID(int conversionID) {
		this.conversionID = conversionID;
	}
	public String getThumbnailsCreated() {
		return thumbnailsCreated;
	}
	public void setThumbnailsCreated(String thumbnailsCreated) {
		this.thumbnailsCreated = thumbnailsCreated;
	}
	public String getSwfCreated() {
		return swfCreated;
	}
	public void setSwfCreated(String swfCreated) {
		this.swfCreated = swfCreated;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
	@Override
	public String toString() {
		return "LiveDocConversionVO [conversionID=" + conversionID
				+ ", thumbnailsCreated=" + thumbnailsCreated + ", swfCreated="
				+ swfCreated + ", userID=" + userID + "]";
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	
	
	
	
	
}
