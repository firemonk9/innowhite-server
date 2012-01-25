package com.innowhite.PlaybackApp.dao;

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
    public void savePlayBackPlayList(PlayBackPlayList flowPlayerVideo) {

//	for (PlayBackPlayList obj : flowPlayerVideo) {
	    // hard coding the server 2 addr for now. will need to change it.
    	flowPlayerVideo.setServer("innos2.innowhite.com");
	    save(flowPlayerVideo);
//    }
    }

    @Transactional
    public void save(PlayBackPlayList video) {
	try {
	    Session session = sessionFactory.getCurrentSession();
	    session.save(video);
	    session.flush();
	    session.clear();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
