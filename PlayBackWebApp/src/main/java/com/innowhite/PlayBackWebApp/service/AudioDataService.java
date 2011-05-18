package com.innowhite.PlayBackWebApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.model.AudioDataDao;

public class AudioDataService {

    AudioDataDao audioDataDao;

    private static final Logger log = LoggerFactory.getLogger(AudioDataService.class);

    public void setAudioDataDao(AudioDataDao audioDataDao) {
	this.audioDataDao = audioDataDao;
    }

    /*
     * RECORDSTART_ROOMID_FILENAME RECORDSTOP_ROOMID_FILENAME
     */
    public void saveAudioData(String m) {
	try {
	    log.debug("entered saveAudioData msg:" + m);
	    if (m != null) {
		String[] arr = m.split("#");

		if (arr != null && arr.length == 3) {
		    String recordStatus = arr[0];
		    String roomId = arr[1];
		    String fileName = arr[2];
		    log.debug("recordStatus   " + recordStatus + "  roomId " + roomId + "  fileName " + fileName);
		    audioDataDao.saveWhitebordObj(recordStatus, fileName, roomId);
		} else
		    log.warn("expecting three tokens and got " + m);

	    }
	    // whiteboardDataDao.saveWhitebordObj(m);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}

    }

}
