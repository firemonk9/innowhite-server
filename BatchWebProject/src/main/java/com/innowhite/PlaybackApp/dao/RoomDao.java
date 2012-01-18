package com.innowhite.PlaybackApp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.RoomData;

public class RoomDao {
    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(RoomDao.class);

    public static void main(String args[]) {

    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Transactional
    public String getSessionDetailXML(String roomId) {

	try {
	    log.debug("Entered getSessionDetailXML with roomId :" + roomId);
	    Session session = sessionFactory.getCurrentSession();

	    Criteria crit = session.createCriteria(RoomData.class);
	    @SuppressWarnings("unchecked")
	    List<RoomData> list2 = crit.add(Restrictions.eq("roomName", roomId)).list();

	    session.clear();
	    session.flush();

	    if (list2 != null && list2.size() == 1) {
		RoomData data = list2.get(0);
		if (data != null)
		    return data.getRoomDetailXml();
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(),e);
	}
	log.warn("The xml is null so setting default name for the uploaded video.");
	return null;

    }

}
