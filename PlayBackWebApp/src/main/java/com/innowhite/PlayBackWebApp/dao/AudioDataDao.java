package com.innowhite.PlayBackWebApp.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlayBackWebApp.model.AudioData;



public class AudioDataDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(AudioDataDao.class);
    
  

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void saveAudioData(String recordStatus, String filePath, String roomName,Date curDate) {

	log.debug("entered saveAudioData");
	AudioData audioData = new AudioData();

	audioData.setFilePath(filePath);
	audioData.setRoomName(roomName);
	
	
	
	if (recordStatus != null && recordStatus.equals("RECORDSTART"))
	{
	    audioData.setStartTime(curDate);
	    saveAudioData(audioData);
	    
	}else if (recordStatus != null && recordStatus.equals("RECORDSTOP"))
	{
	    audioData.setEndTime(curDate);
	    updateAudioData(audioData);
	}
    }

    @Transactional
    private void updateAudioData(AudioData audioData) {
	
	log.debug("entered updateAudioData");
	Session session = sessionFactory.getCurrentSession();

	String query = "update AudioData o set o.endTime=:endTime  where o.roomName=:roomName and o.filePath=:filePath";
	int val = session.createQuery(query).setTimestamp("endTime", audioData.getEndTime()).setString("roomName", audioData.getRoomName()).setString("filePath", audioData.getFilePath()).executeUpdate();
	log.debug("retuned updateAudioData val "+val);
	// /log.debug("");

    }

    @Transactional
    private void saveAudioData(AudioData wb) {
	try {
	    log.debug("entered saveAudioData");
	    
	    Session session = sessionFactory.getCurrentSession();
	    session.save(wb);
	    session.flush();
	    session.clear();
	} catch (Exception e) {

	    e.printStackTrace();
	}
    }
}
