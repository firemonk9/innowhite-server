package com.innowhite.PlaybackApp.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.PlayBackPlayList;

public class PlayBackPlayListDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(PlayBackPlayListDao.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Transactional
    public String savePlayBackPlayList(PlayBackPlayList flowPlayerVideo) {
    	log.debug("-----Entered savePlayBackPlayList ---");
    	String returnFileId = null;
    	//	for (PlayBackPlayList obj : flowPlayerVideo) {
	    // hard coding the server 2 addr for now. will need to change it.
    	flowPlayerVideo.setServer("innos2.innowhite.com");
    	returnFileId =  save(flowPlayerVideo);
    	//    }
    	log.debug("-----Leaving savePlayBackPlayList ------returnfileId-----"+returnFileId);
    	return returnFileId;
    }

    @Transactional
    public String save(PlayBackPlayList video) {
    	Serializable returnFileId=0;
		try {
		    Session session = sessionFactory.getCurrentSession();
		    returnFileId = session.save(video);
		    session.flush();
		    session.clear();
		    log.debug("--PlayBackPlayListDao---Leaving save ----returnfileId---"+returnFileId);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	return returnFileId.toString();
    }

}
