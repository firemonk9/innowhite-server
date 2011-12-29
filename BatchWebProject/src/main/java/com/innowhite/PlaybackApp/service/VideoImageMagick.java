package com.innowhite.PlaybackApp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.cglib.transform.impl.AddDelegateTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;
import com.innowhite.PlaybackApp.util.PlaybackVO;
import com.innowhite.PlaybackApp.util.ProcessExecutor;

public class VideoImageMagick {

    private static final Logger log = LoggerFactory.getLogger(VideoImageMagick.class);
    
    public static List<VideoData> formatSessionVideoPlaylist(List<VideoData> paddedSessionVideoDatalist, String maxVideoDimensions, PlaybackVO playbackVO) throws Exception {
	log.debug("Inside formatSessionVideoPlaylist...........................................");
	// TODO convert videos to images
	List<VideoData> tempVideoDataList = new ArrayList<VideoData>();
	List<VideoData> inputVideoDataList = paddedSessionVideoDatalist;
	String cmd = null;
	int duration = 0;
	String uniquePath = PlaybackUtil.getUnique();
	String backgroudImagePath =  playbackVO.getTempLocation()+"/backgroundImage"+uniquePath+".jpg";
			
	log.debug("-->Creating a black background image..");
	cmd = " -size " + maxVideoDimensions + " xc:black " + backgroudImagePath;
	PlaybackUtil.invokeImageMagickProcess(cmd);
	for (int i = 0; i < inputVideoDataList.size(); i++) {
	    log.debug("-->Converting video "+i+" to images..");

	    String strDirectoy = playbackVO.getTempLocation() + "/sessionVideo" + uniquePath + i;
	    // TODO Create random sessionVideo directory
	    boolean success = createDir(strDirectoy);
	    if (success == false) {
		log.warn(" Could not create the directory.... returning  ");
		return null;
	    }
	    cmd = " -i " + inputVideoDataList.get(i).getFilePath() + " -r 2 -sameq -f image2 " + strDirectoy + "/%05d.jpg";
	    PlaybackUtil.invokeFfmpegProcess(cmd);

//	    log.debug("-->Transcoding the video..");
//	    ProcessExecutor pe = new ProcessExecutor();
//	    String command = "flvtool2 -U " + tempVideoDataList.get(i).getFilePath();
//	    boolean val = pe.executeProcess(command, "/opt/InnowhiteData/scripts/Transcoder/", null,true);
//	    log.debug("running flvtool -U after creating video from images. "+val);
	    
	    File ff = new File(inputVideoDataList.get(i).getFilePath());
	   	//Duration 0 hack    
	    HashMap<String, String> videohm1 = new HashMap<String, String>();
	    cmd = " -i " + inputVideoDataList.get(i).getFilePath();
	    PlaybackUtil.invokeVideoAttribProcess(cmd, videohm1);
	    duration = PlaybackUtil.getNum(videohm1.get("duration"));
	    log.debug("-->duration of video "+i+":: "+ duration+" previous dur ");
	    
	    log.debug("  This is absolute hack... need to find a soluton as why a video file is having 0 duration..."+inputVideoDataList.get(i).getDuration());
	    if(duration == 0 && inputVideoDataList.get(i).getDuration() != null && PlaybackUtil.getNum(inputVideoDataList.get(i).getDuration()) >0)
	    {
			duration = PlaybackUtil.getNum(inputVideoDataList.get(i).getDuration());
			log.debug("  setting duration from vo. "+duration);
	    }
	    
	    // TODO compose all images to a max width:height black background image
	    log.debug("-->Compose all images of video "+i+" on a black background image");
	    for (int j = 1; j <= duration * 2; j++) {
		// TODO check if file exists
		File f = new File(strDirectoy + "/" + String.format("%05d", j) + ".jpg");
		String imPath = null;
		if (f.exists()) {
		    // log.debug("converting ");
			imPath = strDirectoy + "/" + String.format("%05d", j) + ".jpg";
			//convert composite.jpg -gravity Center -draw "image Over 0,0 0,0 '00019.jpg'" zzzzzz.jpg
		    cmd = " "+backgroudImagePath+" -gravity Center -draw \"image Over 0,0 0,0 '"+imPath+"'\" "+imPath;
		    //log.debug("Composing "+imPath);
		    PlaybackUtil.invokeImageMagickProcess(cmd);
		} else {
		    log.warn("file/image does not exist.. exiting.." + f.getAbsolutePath());
		}
	    }

	    // TODO convert images to videos
	    log.debug("->Convert images of video "+i+" back to videos");
	    cmd = " -y -r 2 -f image2 -i "+strDirectoy+"/%05d.jpg -an -sameq "+inputVideoDataList.get(i).getFilePath().replace(".flv", "_new.flv");
	    PlaybackUtil.invokeFfmpegProcess(cmd);

	    // MakeExectuable obj = new MakeExectu
//	    command = "flvtool2 -D " + inputVideoDataList.get(i).getFilePath();
//	    val = pe.executeProcess(command, "/opt/InnowhiteData/scripts/Transcoder/", null);
//	    log.debug("running flvtool -D after creating video from images. "+val);
	    VideoData vd = new VideoData();
	    vd.setStartTime(inputVideoDataList.get(i).getStartTime());
	    vd.setEndTime(inputVideoDataList.get(i).getEndTime());
	    vd.setDuration(inputVideoDataList.get(i).getDuration());
	    vd.setFilePath(inputVideoDataList.get(i).getFilePath().replace(".flv", "_new.flv"));
	    vd.setId(inputVideoDataList.get(i).getId());
	    vd.setRoomName(inputVideoDataList.get(i).getRoomName());
	    vd.setVideoType(inputVideoDataList.get(i).getVideoType());
	    tempVideoDataList.add(vd);
	}
	return tempVideoDataList;
    }
	    
	    
    public static boolean createDir(String strDirectoy) {

	boolean success = (new File(strDirectoy)).mkdir();
	if (success) {
	    log.debug("Random sessionVideo Directory: " + strDirectoy + " created!");
	} else {
	    log.warn("directory not created. may already exist" + strDirectoy);
	}
	return success;

    }

}
