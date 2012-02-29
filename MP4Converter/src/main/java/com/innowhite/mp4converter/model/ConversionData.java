package com.innowhite.mp4converter.model;

/**
 * @author Tanuja
 * @Date  Feb 27,2012
 */

public class ConversionData {
	
	private String tempLocation;
    private String winFilePath;
    private String fileId;
    private String flvFilePath;
    
	public String getTempLocation() {
		return tempLocation;
	}
	public void setTempLocation(String tempLocation) {
		this.tempLocation = tempLocation;
	}
	
	public String getWinFilePath() {
		return winFilePath;
	}
	public void setWinFilePath(String winFilePath) {
		this.winFilePath = winFilePath;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFlvFilePath() {
		return flvFilePath;
	}
	public void setFlvFilePath(String flvFilePath) {
		this.flvFilePath = flvFilePath;
	}

}
