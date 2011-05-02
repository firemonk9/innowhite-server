package com.innowhite.PlayBackWebApp.model;

import java.util.HashMap;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class WhiteboardDataDao {

	
	SessionFactory sessionFactory;

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
