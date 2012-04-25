package com.innowhite.PlaybackApp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.CallBackUrlsData;
import com.innowhite.PlaybackApp.model.RoomData;

public class CallBackUrlsDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(CallBackUrlsDao.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public CallBackUrlsData getURLData(String roomId) {

	log.debug("entered getURLData");
	Session session = sessionFactory.getCurrentSession();

	Criteria crit = session.createCriteria(RoomData.class);
	@SuppressWarnings("unchecked")
	List<RoomData> list2 = crit.add(Restrictions.eq("roomName", roomId)).list();

	if (list2 != null && list2.size() == 1) {

	    RoomData roomVo = (RoomData) list2.get(0);
	    String orgName = roomVo.getOrgName();
	    String source = roomVo.getSource();

	    crit = session.createCriteria(CallBackUrlsData.class);
	    @SuppressWarnings("unchecked")
	    List<CallBackUrlsData> list = crit.add(Restrictions.eq("orgName", orgName)).add(Restrictions.eq("source", source)).list();
	    if(list != null && list.size() ==1)
	 	return list.get(0);
	    
	}
	session.clear();
	session.flush();

	return null;
    }

}
