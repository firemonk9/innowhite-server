package com.innowhite.PlaybackApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;

public class VideoImageMagick {

	private List<VideoData> formatSessionVideoPlaylist(List<VideoData> paddedSessionVideoDatalist, String maxVideoDimensions) {
	    //TODO convert videos to images
    	List<VideoData> tempVideoDataList = paddedSessionVideoDatalist;
	    String cmd = null;
	    int duration = 0;
	    String uniquePath = PlaybackUtil.getUnique();
	    
	    cmd = " convert -size "+maxVideoDimensions+" xc:black C:/Innowhite-git/temp/backgroundImage"+uniquePath+".jpg";
	    System.out.println("command for converting video to images::"+cmd);
	    PlaybackUtil.invokeImageMagickProcess(cmd);
	    
	    for(int i=0; i<tempVideoDataList.size();i++){
	    	cmd = " -i "+tempVideoDataList.get(i).getFilePath()+" -r 2 -f image2 C:/Innowhite-git/temp/sessionVideos/%05d.jpg";
	    	PlaybackUtil.invokeFfmpegProcess(cmd);
	    	
	    	HashMap<String, String> videohm1 = new HashMap<String, String>();
	    	cmd = " -i " + tempVideoDataList.get(i).getFilePath();
	    	PlaybackUtil.invokeVideoAttribProcess(cmd, videohm1);
		    duration = PlaybackUtil.getNum(videohm1.get("duration"));
		    System.out.println("duration of current session video ::"+duration);
	    	
	    	//TODO compose all images to a max width:height black background image
	    	for(int j=0; j<duration*2;j++){
		    	cmd = " convert C:/Innowhite-git/temp/backgroundImage"+uniquePath+".jpg -gravity Center -draw \"image Over 0,0 0,0 'C:/Innowhite-git/temp/sessionVideos/"+String.format("%05d", j)+".jpg'\" "+String.format("%05d", j)+".jpg";
		    	System.out.println("command for converting the images::"+cmd);
		    	PlaybackUtil.invokeImageMagickProcess(cmd);
	    	}
	    	
		    //TODO convert images to videos
		    cmd = " -y -r 2 -i C:/Innowhite-git/temp/sessionVideos/%05d.jpg -an "+paddedSessionVideoDatalist.get(i).getFilePath();
		    System.out.println("command for converting images to videos::"+cmd);
		    PlaybackUtil.invokeFfmpegProcess(cmd);
//		    tempVideoDataList.remove(i);
//		    vd.setFilePath(tempVideoDataList.get(i).get);
//		    tempVideoDataList.add(i, paddedSessionVideoDatalist.get(i).getFilePath());
	    }
    	return tempVideoDataList;
	}
	
	public static void main(String[] args) {
		System.out.println("starting....");
		List<VideoData> vdList = new ArrayList<VideoData>();
		VideoData vd = new VideoData();
//		vd.setStartTime(startTime);
//		vd.setEndTime(endTime);
		vd.setVideoType("WHITEBOARD");
		vd.setFilePath("C:/Innowhite-git/temp/442664077_0384542695800x600finalSessionVideo6484551163playlist.flv");
		vdList.set(0, vd);
		VideoImageMagick vim = new VideoImageMagick();
		vim.formatSessionVideoPlaylist(vdList, "1080x699");
	}

}
