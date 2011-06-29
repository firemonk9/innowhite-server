package com.innowhite.PlaybackApp.util;

public class PlaybackVO {

    
    public String getWinFFmpegPath() {
	return winFFmpegPath;
    }

    public void setWinFFmpegPath(String winFFmpegPath) {
	this.winFFmpegPath = winFFmpegPath;
    }

    public String getMacFFmpegPath() {
	return macFFmpegPath;
    }

    public void setMacFFmpegPath(String macFFmpegPath) {
	this.macFFmpegPath = macFFmpegPath;
    }

    public String getUbuntuFFmpegPath() {
	return ubuntuFFmpegPath;
    }

    public void setUbuntuFFmpegPath(String ubuntuFFmpegPath) {
	this.ubuntuFFmpegPath = ubuntuFFmpegPath;
    }

    public String getWinTempLocation() {
	return winTempLocation;
    }

    public void setWinTempLocation(String winTempLocation) {
	this.winTempLocation = winTempLocation;
    }

    public String getMacTempLocation() {
	return macTempLocation;
    }

    public void setMacTempLocation(String macTempLocation) {
	this.macTempLocation = macTempLocation;
    }

    public String getUbuntuTempLocation() {
	return ubuntuTempLocation;
    }

    public void setUbuntuTempLocation(String ubuntuTempLocation) {
	this.ubuntuTempLocation = ubuntuTempLocation;
    }

    
    public String getFfmpegPath() {
        return ffmpegPath;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public String getTempLocation() {
        return tempLocation;
    }

    public void setTempLocation(String tempLocation) {
        this.tempLocation = tempLocation;
    }


    public String getWinAudioPath() {
        return winAudioPath;
    }

    public void setWinAudioPath(String winAudioPath) {
        this.winAudioPath = winAudioPath;
    }

    public String getWinVideoPath() {
        return winVideoPath;
    }

    public void setWinVideoPath(String winVideoPath) {
        this.winVideoPath = winVideoPath;
    }


    private String winAudioPath;
    private String winVideoPath;
    
    private String ffmpegPath;
    private String tempLocation;
    
    private String winFFmpegPath;
    private String macFFmpegPath;
    private String ubuntuFFmpegPath;

    private String winTempLocation;
    private String macTempLocation;
    private String ubuntuTempLocation;

}
