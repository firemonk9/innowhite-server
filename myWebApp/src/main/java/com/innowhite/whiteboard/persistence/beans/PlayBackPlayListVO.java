package com.innowhite.whiteboard.persistence.beans;

public class PlayBackPlayListVO {

    private String duration;
    private String filePath;
    private String server;
    private String size;
   
    private String width;
    private String height;
    private String youtubeUrl;
    private String status;
    private String mp4Path;
    
    public String getMp4Path() {
		return mp4Path;
	}

	public void setMp4Path(String mp4Path) {
		this.mp4Path = mp4Path;
	}

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    
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

    public String getSize() {
	return size;
    }

    public void setSize(String size) {
	this.size = size;
    }

    public String getServer() {
	return server;
    }

    public void setServer(String server) {
	this.server = server;
    }

	
}
