package com.innowhite.PlaybackApp.service;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.PlaybackApp.dao.AudioDataDao;
import com.innowhite.PlaybackApp.dao.SessionRecordingDao;
import com.innowhite.PlaybackApp.dao.VideoDataDao;
import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.SessionRecordings;
import com.innowhite.PlaybackApp.model.VideoData;

public class PlaybackDataService {

    /**
     * @param args
     */

    static BeanFactory factory = null;

    public static void loadInit() {
	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "app-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	factory = (BeanFactory) appContext;

    }

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "app-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	BeanFactory factory = (BeanFactory) appContext;

	String roomId = "123123";

	SessionRecordingDao sessionRecordingsDao = (SessionRecordingDao) factory.getBean("sessionRecordingsDao");
	List<SessionRecordings> sessionRecordingsList = sessionRecordingsDao.getSessionRecordingList(roomId);

	AudioDataDao audioDataDao = (AudioDataDao) factory.getBean("audioDataDao");
	List<AudioData> audioDataList = audioDataDao.getAudioDataList(roomId);

	VideoDataDao videoDataDao = (VideoDataDao) factory.getBean("videoDataDao");
	List<VideoData> videoDataList = videoDataDao.getVideoDataList(roomId);

	// WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
	// factory.getBean("whiteboardDataDao");
	// whiteboardDataDao.getWhiteboardDTOForRoom("room1");
    }

}
