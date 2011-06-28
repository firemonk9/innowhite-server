package com.innowhite.PlaybackApp.service;

public class BasicThreadCreator {

    ThreadExecutor playThreadExecutor;

    public ThreadExecutor getPlayThreadExecutor() {
	return playThreadExecutor;
    }

    public void setPlayThreadExecutor(ThreadExecutor playThreadExecutor) {
	this.playThreadExecutor = playThreadExecutor;
    }

    public boolean startJob(String id) {

	playThreadExecutor.setRoomId(id);
	playThreadExecutor.start();

	return true;
    }
}
