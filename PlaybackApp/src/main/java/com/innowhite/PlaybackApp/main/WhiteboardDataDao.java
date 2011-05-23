package com.innowhite.PlaybackApp.main;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.WhiteboardData;

public class WhiteboardDataDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(WhiteboardDataDao.class);
    
    @Transactional
    public List<WhiteboardData> getWhiteboardDTOForRoom(String roomId) {

	Session session = sessionFactory.getCurrentSession();

	Query query = session
		.createSQLQuery(
			"select actionType, bordercolor, fillcolor, imageURL, " + "mainscalex, mainscaley, objDate, objName, objType, penthickness, points,"
				+ " roomName, rotation, secondName, secondSeq, seq, shpHeight, shpWidth, sprText, " + "txtFont, txtType, userId, versionNumber, wbNumber, x1, x2, xpos, y1, y2, ypos"
				+ " from whiteboard_data a where a.roomname=:roomname")
				.setString("roomname", roomId)
				.setResultTransformer(Transformers.aliasToBean(WhiteboardData.class));

	@SuppressWarnings("rawtypes")
	List list2 = query.list();
	@SuppressWarnings("unchecked")
	List<WhiteboardData> list = list2;
	System.err.println(" the records returned :: " + list + " size " + list.size());

	session.clear();
	session.flush();
	return list;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void saveWhitebordObj(HashMap m) {

	WhiteboardData wb = new WhiteboardData();

	wb.setRoomName((String) m.get("roomName"));
	wb.setActionType((String) m.get("actionType"));
	wb.setObjName((String) m.get("objName"));
	wb.setObjType((String) m.get("objType"));

	wb.setXpos((Float) m.get("xpos"));
	wb.setYpos((Float) m.get("ypos"));

	wb.setX1((Float) m.get("x1"));
	wb.setY1((Float) m.get("y1"));
	wb.setX2((Float) m.get("x2"));
	wb.setY2((Float) m.get("y2"));

	wb.setShpHeight((Float) m.get("shpHeight"));
	wb.setShpWidth((Float) m.get("shpWidth"));

	wb.setMainscalex((Float) m.get("mainscalex"));
	wb.setMainscaley((Float) m.get("mainscaley"));

	wb.setBordercolor((String) m.get("bordercolor"));
	wb.setFillcolor((String) m.get("fillcolor"));
	wb.setSecondName((String) m.get("secondName"));
	//
	wb.setRotation((Integer) m.get("rotation"));
	wb.setObjDate((Long) m.get("objDate"));

	wb.setPenthickness((Integer) m.get("penthickness"));
	wb.setVersionNumber((Integer) m.get("versionNumber"));

	wb.setPoints((String) m.get("elems"));
	wb.setSprText((String) m.get("sprText"));
	wb.setTxtType((String) m.get("txtType"));
	wb.setTxtFont((String) m.get("txtFont"));
	wb.setImageURL((String) m.get("imageURL"));

	wb.setSeq((Integer) m.get("seq"));
	wb.setSecondSeq((Integer) m.get("secondSeq"));
	wb.setWbNumber((Integer) m.get("wbNumber"));

	wb.setUserId((String) m.get("userId"));

	save(wb);

    }

    @Transactional
    private void save(WhiteboardData wb) {
	try {
	    Session session = sessionFactory.getCurrentSession();
	    session.save(wb);
	    session.flush();
	    session.clear();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
