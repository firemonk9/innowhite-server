package com.innowhite.PlayBackWebApp.service;

import java.util.StringTokenizer;

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
	    log.debug("entered saveAudioData");
	    if (m != null) {
		StringTokenizer st = new StringTokenizer(m, "_");

		String recordStatus = st.nextToken();
		String roomId = st.nextToken();
		String fileName = st.nextToken();
		audioDataDao.saveWhitebordObj(recordStatus, fileName, roomId);
	    }
	    // whiteboardDataDao.saveWhitebordObj(m);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}

    }

}
