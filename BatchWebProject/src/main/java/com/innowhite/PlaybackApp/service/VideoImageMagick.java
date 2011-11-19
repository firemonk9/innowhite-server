package com.innowhite.PlaybackApp.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;
import com.innowhite.PlaybackApp.util.PlaybackVO;
import com.innowhite.PlaybackApp.util.ProcessExecutor;

public class VideoImageMagick {

    private static final Logger log = LoggerFactory.getLogger(VideoImageMagick.class);
    
    public static List<VideoData> formatSessionVideoPlaylist(List<VideoData> paddedSessionVideoDatalist, String maxVideoDimensions, PlaybackVO playbackVO) throws Exception {
	log.debug("Inside formatSessionVideoPlaylist...");
	// TODO convert videos to images
	List<VideoData> tempVideoDataList = paddedSessionVideoDatalist;
	String cmd = null;
	int duration = 0;
	String uniquePath = PlaybackUtil.getUnique();

	log.debug("creating black background image..");
	cmd = " -size " + maxVideoDimensions + " xc:black " + playbackVO.getTempLocation()+"/backgroundImage"+uniquePath+".jpg";
	PlaybackUtil.invokeImageMagickProcess(cmd);
	for (int i = 0; i < tempVideoDataList.size(); i++) {
	    log.debug("converting video "+i+" to images..");

	    String strDirectoy = playbackVO.getTempLocation() + "/sessionVideo" + uniquePath + i;
	    // TODO Create random sessionVideo directory
	    boolean success = createDir(strDirectoy);
	    if (success == false) {
		log.warn(" Could not create the directory.... returning  ");
		return null;

	    }

	    cmd = " -i " + tempVideoDataList.get(i).getFilePath() + " -r 2 -f image2 " + strDirectoy + "/%05d.jpg";
	    PlaybackUtil.invokeFfmpegProcess(cmd);

	    ProcessExecutor pe = new ProcessExecutor();
	    String command = "flvtool2 -U " + tempVideoDataList.get(i).getFilePath();
	    boolean val = pe.executeProcess(command, "/opt/InnowhiteData/scripts/Transcoder/", null);
	    log.debug("running flvtool -U after creating video from images. "+val);
	    
	    File ff = new File(tempVideoDataList.get(i).getFilePath());
	   	    
	    HashMap<String, String> videohm1 = new HashMap<String, String>();
	    cmd = " -i " + tempVideoDataList.get(i).getFilePath();
	    PlaybackUtil.invokeVideoAttribProcess(cmd, videohm1);
	    duration = PlaybackUtil.getNum(videohm1.get("duration"));
	    log.debug("duration of video "+i+":: "+ duration+" previous dur ");
	    
	    log.debug("  This is absolute hack... need to find a soluton as why a video file is having 0 duration..."+tempVideoDataList.get(i).getDuration());
	    if(duration == 0 && tempVideoDataList.get(i).getDuration() != null && PlaybackUtil.getNum(tempVideoDataList.get(i).getDuration()) >0)
	    {
		
		duration = PlaybackUtil.getNum(tempVideoDataList.get(i).getDuration());
		log.debug("  setting duration from vo. "+duration);
	    }
	    
	    
	    // TODO compose all images to a max width:height black background image
	    log.debug("compose all images of video "+i+" on a black background image");
	    for (int j = 1; j <= duration * 2; j++) {
		// TODO check if file exists
		File f = new File(strDirectoy + "/" + String.format("%05d", j) + ".jpg");
		if (f.exists()) {
		    // log.debug("converting ");
			String biPath = playbackVO.getTempLocation() + "/backgroundImage" + uniquePath + ".jpg";
			String imPath = strDirectoy + "/" + String.format("%05d", j) + ".jpg";
			//convert composite.jpg -gravity Center -draw "image Over 0,0 0,0 '00019.jpg'" zzzzzz.jpg
		    cmd = " "+biPath+" -gravity Center -draw \"image Over 0,0 0,0 '"+imPath+"'\" "+imPath;
		    //log.debug("Composing "+imPath);
		    PlaybackUtil.invokeImageMagickProcess(cmd);
		} else {
		    log.warn("file/image does not exist.. exiting.." + f.getAbsolutePath());
		}
	    }

	    // TODO convert images to videos
	    log.debug("convert images of video "+i+" back to videos");
	    cmd = " -y -r 2 -i " + strDirectoy + "/%05d.jpg -an " + paddedSessionVideoDatalist.get(i).getFilePath();
	    PlaybackUtil.invokeFfmpegProcess(cmd);

	    
	   
	    // MakeExectuable obj = new MakeExectu
	    
	    
//	    command = "flvtool2 -D " + paddedSessionVideoDatalist.get(i).getFilePath();
//	    val = pe.executeProcess(command, "/opt/InnowhiteData/scripts/Transcoder/", null);
//	    log.debug("running flvtool -D after creating video from images. "+val);
	    
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
