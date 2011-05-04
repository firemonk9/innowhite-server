package com.innowhite.PlayBackWebApp.service;

import java.util.HashMap;

import com.innowhite.PlaybackApp.model.WhiteboardDataDao;

public class WhiteboardDataService {

	WhiteboardDataDao whiteboardDataDao;


	public WhiteboardDataDao getWhiteboardDataDao() {
		return whiteboardDataDao;
	}

	public void setWhiteboardDataDao(WhiteboardDataDao whiteboardDataDao) {
		this.whiteboardDataDao = whiteboardDataDao;
	}

	public void saveWhitebordObj(HashMap m) {
		try {
			whiteboardDataDao.saveWhitebordObj(m);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
