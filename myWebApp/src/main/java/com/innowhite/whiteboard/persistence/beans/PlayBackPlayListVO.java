package com.innowhite.whiteboard.persistence.beans;

public class PlayBackPlayListVO {

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public void setDuration(String duration) {
	this.duration = duration;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
	return duration;
    }

    private String duration;
    private String filePath;
    private String server;

    public String getServer() {
	return server;
    }

    public void setServer(String server) {
	this.server = server;
    }

}
