package com.innowhite.PlaybackApp.model;

import java.util.ArrayList;
import java.util.List;

public class SessionBucket {

	public List<AudioData> audioDataList;
	public List<VideoData> videoDataList;
	
	
	public SessionBucket()
	{
		this.audioDataList=new ArrayList<AudioData>();
		this.videoDataList=new ArrayList<VideoData>();
	}
	
	/**
	 * @return the audioDataList
	 */
	public List<AudioData> getAudioDataList() {
		return audioDataList;
	}
	/**
	 * @param audioDataList the audioDataList to set
	 */
	public void setAudioDataList(List<AudioData> audioDataList) {
		this.audioDataList = audioDataList;
	}
	/**
	 * @return the videoDataList
	 */
	public List<VideoData> getVideoDataList() {
		return videoDataList;
	}
	/**
	 * @param videoDataList the videoDataList to set
	 */
	public void setVideoDataList(List<VideoData> videoDataList) {
		this.videoDataList = videoDataList;
	}
	
}
