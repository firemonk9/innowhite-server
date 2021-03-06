package com.innowhite.PlaybackApp.dao;

import java.util.List;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
		    log.error(e.getMessage(),e);
		}
		return intmaxId;	
		
	 }
    
    @Transactional
    public Date getRoomEndDate(String roomId) {
    	Date endDateObj = null;
		try {
				log.debug("Entered getRoomEndDate");
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createSQLQuery("select end_date from room where room_id ='"+roomId+"' ");
				endDateObj = (Date)(query.uniqueResult());
				log.debug("Inside getRoomEndDate with endDateObj :" + endDateObj);
				   
				session.clear();
				session.flush();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return endDateObj;	
		
	 }

}
