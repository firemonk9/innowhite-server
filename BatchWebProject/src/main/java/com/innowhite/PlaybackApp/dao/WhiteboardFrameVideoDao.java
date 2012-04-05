package com.innowhite.PlaybackApp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.WhiteboardVideoFrame;

public class WhiteboardFrameVideoDao {

	SessionFactory sessionFactory;

	private static final Logger log = LoggerFactory.getLogger(WhiteboardFrameVideoDao.class);

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<WhiteboardVideoFrame> getFramesList(String roomId) {
		log.debug(" Entered  getFramesList for room : " + roomId);
		Session session = sessionFactory.getCurrentSession();

		Criteria crit = session.createCriteria(WhiteboardVideoFrame.class);
		@SuppressWarnings("unchecked")
		List<WhiteboardVideoFrame> list2 = crit.add(Restrictions.eq("roomId", roomId)).addOrder(Order.asc("fileId")).addOrder(Order.asc("seq")).list();

		session.clear();
		session.flush();
		return list2;

	}

	@Transactional
	public void deleteRoomData(String roomId) {

		log.debug(" Entered  deleteRoomData for room : " + roomId);
		Session session = sessionFactory.getCurrentSession();

		String hql = "delete from WhiteboardVideoFrame  where roomId = '" + roomId+"'";
		Query query = session.createQuery(hql);
		int row = query.executeUpdate();
		log.debug(" deleted   records : " + row + " for room " + roomId);

	}

}
