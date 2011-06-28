package com.innowhite.PlaybackApp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.SessionRecordings;

public class SessionRecordingDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(SessionRecordingDao.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<SessionRecordings> getSessionRecordingList(String roomId) {

	Session session = sessionFactory.getCurrentSession();

	Criteria crit = session.createCriteria(SessionRecordings.class);
	@SuppressWarnings("unchecked")
	List<SessionRecordings> list2 = crit.add(Restrictions.eq("roomName", roomId)).addOrder(Order.asc("id")).list();

	session.clear();
	session.flush();
	return list2;

    }

}
