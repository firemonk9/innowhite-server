package com.innowhite.PlaybackApp.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.VideoData;

public class VideoDataDao {

    SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(VideoDataDao.class);

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    
    
    
    
    @Transactional
    public List<VideoData> getVideoDataList(String roomId) {

	Session session = sessionFactory.getCurrentSession();

	Criteria crit = session.createCriteria(VideoData.class);
	@SuppressWarnings("unchecked")
	
	List<VideoData> list2 = crit.add(Restrictions.eq("roomName", roomId))
	       .add( Restrictions.in( "videoType", new String[] { "DESKTOP", "WHITEBOARD" } ) )
	    .list();
	
	session.clear();
	session.flush();
	return list2;

    }

    @Transactional
    public void saveVideoData(String recordStatus, String filePath, String roomName, Date curDate) {

	log.debug("entered saveVideoData");
	VideoData videoData = new VideoData();

	videoData.setFilePath(filePath);
	videoData.setRoomName(roomName);
	videoData.setStartTime(curDate);
	videoData.setEndTime(curDate);

	if (recordStatus != null && recordStatus.equals("RECORDSTART"))
	    updateStartTime(videoData);
	else if (recordStatus != null && recordStatus.equals("RECORDSTOP"))
	    updateEndTime(videoData);
    }

    @Transactional
    private void updateEndTime(VideoData videoData) {

	log.debug("entered updateEndTime");
	Session session = sessionFactory.getCurrentSession();

	String query = "update VideoData o set o.endTime=:endTime   where o.id=:roomName ";
	int val = session.createQuery(query).setTimestamp("endTime", videoData.getEndTime()).setString("roomName", videoData.getRoomName()).executeUpdate();
	// setString("videoType", "SCREEN_SHARE")
	// setString("filePath", videoData.getFilePath())
	log.debug("retuned updateEndTime val " + val);
	// /log.debug("");

    }

    @Transactional
    private void updateStartTime(VideoData videoData) {
	try {
	    log.debug("entered updateStartTime");
	    Session session = sessionFactory.getCurrentSession();

	    String query = "update VideoData o set o.startTime=:startTime , o.filePath = :filePath where o.id=:roomName ";
	    int val = session.createQuery(query).setTimestamp("startTime", videoData.getStartTime()).setString("filePath", videoData.getFilePath()).setString("roomName", videoData.getRoomName())
		    .executeUpdate();
	    // setString("videoType", "SCREEN_SHARE")
	    // setString("filePath", videoData.getFilePath())
	    log.debug("retuned updateStartTime val " + val);
	} catch (Exception e) {

	    e.printStackTrace();
	}
    }
}
