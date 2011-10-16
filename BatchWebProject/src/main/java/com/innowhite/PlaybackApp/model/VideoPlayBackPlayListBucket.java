package com.innowhite.PlaybackApp.model;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayBackPlayListBucket {

	public PlayBackPlayList playbackPlaylist;
	//public List<VideoData> videoDataList;
	public String videoDimensions;

	public VideoPlayBackPlayListBucket()
	{
		this.playbackPlaylist=new PlayBackPlayList();
		// this.videoDataList=new ArrayList<VideoData>();
		this.videoDimensions=null;
	}
	
	/**
	 * @return the videoDimensions
	 */
	public String getVideoDimensions() {
		return videoDimensions;
	}

	/**
	 * @param videoDimensions the videoDimensions to set
	 */
	public void setVideoDimensions(String videoDimensions) {
		this.videoDimensions = videoDimensions;
	}

	/**
	 * @return the playbackPlaylist
	 */
	public PlayBackPlayList getPlaybackPlaylist() {
		return playbackPlaylist;
	}

	/**
	 * @param playbackPlaylist the playbackPlaylist to set
	 */
	public void setPlaybackPlaylist(PlayBackPlayList playbackPlaylist) {
		this.playbackPlaylist = playbackPlaylist;
	}

	/**
	 * @return the videoDataList
	 */
//	public List<VideoData> getVideoDataList() {
//		return videoDataList;
//	}
//
//	/**
//	 * @param videoDataList the videoDataList to set
//	 */
//	public void setVideoDataList(List<VideoData> videoDataList) {
//		this.videoDataList = videoDataList;
//	}
}
