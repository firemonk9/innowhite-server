package com.innowhite.PlaybackApp.service;

public class ThreadExecutor extends Thread {

    private String roomId;

    private PlaybackDataService playbackDataservice;
    
    public PlaybackDataService getPlaybackDataservice() {
        return playbackDataservice;
    }

    public void setPlaybackDataservice(PlaybackDataService playbackDataservice) {
        this.playbackDataservice = playbackDataservice;
    }

    public ThreadExecutor() {
    }

    /**
     * @param roomId
     *            the roomId to set
     */
    public void setRoomId(String roomId) {
	this.roomId = roomId;
    }

    /**
     * @return the roomId
     */
    public String getRoomId() {
	return roomId;
    }

    public void run() {
	playbackDataservice.process(roomId);
    }

}
