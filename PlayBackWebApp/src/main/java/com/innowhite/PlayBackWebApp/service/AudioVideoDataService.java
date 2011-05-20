package com.innowhite.PlayBackWebApp.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.dao.AudioDataDao;
import com.innowhite.PlayBackWebApp.dao.VideoDataDao;

public class AudioVideoDataService {

    
    VideoDataDao videoDataDao;
    
    public VideoDataDao getVideoDataDao() {
        return videoDataDao;
    }

    public void setVideoDataDao(VideoDataDao videoDataDao) {
        this.videoDataDao = videoDataDao;
        
    }

    AudioDataDao audioDataDao;

    private static final Logger log = LoggerFactory.getLogger(AudioVideoDataService.class);

    public void setAudioDataDao(AudioDataDao audioDataDao) {
	this.audioDataDao = audioDataDao;
    }

    /*
     * RECORDSTART_ROOMID_FILENAME RECORDSTOP_ROOMID_FILENAME
     */
    public void saveAudioData(String message, String msgType,String curtime) {
	try {
	    log.debug("entered saveAudioData msg:" + message);
	    if (message != null) {
		String[] arr = message.split("#");

		if (arr != null && arr.length == 3) {
		    String recordStatus = arr[0];
		    String roomId = arr[1];
		    String fileName = arr[2];
		    log.debug("recordStatus   " + recordStatus + "  roomId " + roomId + "  fileName " + fileName);
		    
		    Date curDate = new Date(Long.parseLong(curtime));
		    
		    
		    if(msgType.equals("AUDIO"))
			audioDataDao.saveAudioData(recordStatus, fileName, roomId,curDate);
		    else   if(msgType.equals("VIDEO"))
			videoDataDao.saveVideoData(recordStatus, fileName, roomId,curDate);
		    
		} else
		    log.warn("expecting three tokens and got " + message);

	    }
	    // whiteboardDataDao.saveWhitebordObj(m);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}

    }

}
