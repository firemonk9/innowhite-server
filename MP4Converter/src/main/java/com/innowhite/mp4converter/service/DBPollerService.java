/** TO poll the DataBase in regular intervals of time */
package com.innowhite.mp4converter.service;

import java.util.TimerTask;

import com.innowhite.mp4converter.dao.MP4ConverterDAO;


/**
 * @author tanuja
 * Date April 20, 2012
 */
public class DBPollerService  extends TimerTask {
	
	public MP4ConverterDAO getMp4ConverterDAO() {
		return mp4ConverterDAO;
	}

	public void setMp4ConverterDAO(MP4ConverterDAO mp4ConverterDAO) {
		this.mp4ConverterDAO = mp4ConverterDAO;
	}

	private MP4ConverterDAO mp4ConverterDAO;
	
	@Override
	public void run() {
		
		mp4ConverterDAO.getRoomMaxID();
	
	}

	

}