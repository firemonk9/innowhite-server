package com.innowhite.whiteboard.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innowhite.transformation.messages.AudioRecordMessageProducer;
import com.innowhite.whiteboard.persistence.dao.AudioDataDAO;
import com.innowhite.whiteboard.persistence.dao.SessionRecordingDAO;
import com.innowhite.whiteboard.util.InnowhiteProperties;

public class SessionRecordingService {

    private static final Logger log = LoggerFactory.getLogger(SessionRecordingService.class);

    public static void main(String args[]) {

	Map<String, String> data = new HashMap<String, String>();
	data.put("room", "12345");
	data.put("record", "start");

	Gson gson = new Gson();
	String encoded = gson.toJson(data, new TypeToken<Map<String, String>>() {
	}.getType());

	Gson gsond = new Gson();

	Map target2 = gson.fromJson(encoded, new TypeToken<Map<String, String>>() {
	}.getType());

	System.err.println(target2 + "   " + encoded);

    }

    public static void sessionRecord(String roomId, String recordStatus) {
	if (recordStatus != null && recordStatus.equals("recordStart")) {

	    startRecording(roomId);
	} else {

	    stopRecording(roomId, recordStatus);
	}
    }

    private static void startRecording(String roomId) {
	log.debug(" entering session recording start " + roomId);
	SessionRecordingDAO.startSessionRecording(roomId);

	BeanFactory factory = InnowhiteProperties.getBeanFactory();
	AudioRecordMessageProducer audioRecordMessageProducer = (AudioRecordMessageProducer) factory.getBean("audioRecordMessageProducer");

	// check if audio conf is on. if yes send a message so that a message is sent to audio server to start recording.
	if (true) {
	    Map<String, String> data = new HashMap<String, String>();
	    data.put("room", roomId);
	    data.put("record", "start");

	    Gson gson = new Gson();
	    String encoded = gson.toJson(data, new TypeToken<Map<String, String>>() {
	    }.getType());

	    audioRecordMessageProducer.sendMessage(encoded);
	}
    }

    private static void stopRecording(String roomId, String recordStatus) {
	log.debug(" entering session recording stop " + roomId);
	if (recordStatus != null && recordStatus.equals("recordStopKill"))
	// this is force kill when user closes the browser while recording is
	// still on.
	{
	    SessionRecordingDAO.endSessionRecording(roomId);
	    WhiteboardToVideoService.stopRecording(roomId);
	} else if (recordStatus != null && recordStatus.equals("recordStop")) {
	    SessionRecordingDAO.endSessionRecording(roomId);
	}
	
	// update audio end time ...
	AudioDataDAO.updateAudiosEndTime(roomId);
	
	BeanFactory factory = InnowhiteProperties.getBeanFactory();
	AudioRecordMessageProducer audioRecordMessageProducer = (AudioRecordMessageProducer) factory.getBean("audioRecordMessageProducer");
	audioRecordMessageProducer.sendMessage("roomId_stop");
    }
}
