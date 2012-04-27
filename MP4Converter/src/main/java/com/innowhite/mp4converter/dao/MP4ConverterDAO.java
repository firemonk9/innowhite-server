package com.innowhite.mp4converter.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.mp4converter.model.PlayBackPlayList;

public class MP4ConverterDAO {

	private static final Logger log = LoggerFactory.getLogger(MP4ConverterDAO.class);
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void updateWebMFilePath(PlayBackPlayList playBackObj) {
		log.debug("entered updateMp4FilePath");
		int returnVal = 0;
		try {
			Session session = sessionFactory.getCurrentSession();

			String query = "update PlayBackPlayList set webmPath=:webmPath  where id=:id";
			returnVal = session.createQuery(query).setString("webmPath", playBackObj.getWebmPath()).setLong("id", playBackObj.getId()).executeUpdate();
			session.clear();
			session.flush();
			log.debug("retuned updateMp4FilePath val " + returnVal);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	
	@Transactional
	public void updateMp4FilePath(PlayBackPlayList playBackObj) {
		log.debug("entered updateMp4FilePath");
		int returnVal = 0;
		try {
			Session session = sessionFactory.getCurrentSession();

			String query = "update PlayBackPlayList set mp4Path=:mp4Path  where id=:id";
			returnVal = session.createQuery(query).setString("mp4Path", playBackObj.getMp4Path()).setLong("id", playBackObj.getId()).executeUpdate();
			session.clear();
			session.flush();
			log.debug("retuned updateMp4FilePath val " + returnVal);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Transactional
	public int getRoomMaxID() {
		int intmaxId = 0;
		try {
			log.debug("Entered getRoomMaxID");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery("select max(id) from room");
			intmaxId = (Integer) query.uniqueResult();
			log.debug("Inside getRoomMaxID with intmaxId :" + intmaxId);

			session.clear();
			session.flush();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return intmaxId;

	}

	@Transactional
	public String getRoomID(String id) {
		String roomName = null;
		try {
			log.debug("Entered getRoomID");
			Session session = sessionFactory.getCurrentSession();
			Long val = Long.parseLong(id);
			String strquery = "select room_id from playback_playlist where id=:id";
			Query query = session.createSQLQuery(strquery).setLong("id", val);
			roomName = (String) query.uniqueResult().toString();
			
			
			log.debug("Exiting getRoomID  :" + roomName);

			session.clear();
			session.flush();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return roomName;

	}

}
