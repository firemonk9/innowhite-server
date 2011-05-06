package com.innowhite.PlaybackApp.service;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.PlaybackApp.model.WhiteboardData;
import com.innowhite.PlaybackApp.model.WhiteboardDataDao;

public class PlaybackDataService {

	/**
	 * @param args
	 */
	
	static BeanFactory factory=null;
	
	public static void loadInit(){
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
		        new String[] {"app-context.xml"});
		// of course, an ApplicationContext is just a BeanFactory
		factory = (BeanFactory) appContext;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
		        new String[] {"app-context.xml"});
		// of course, an ApplicationContext is just a BeanFactory
		BeanFactory factory = (BeanFactory) appContext;
		
		
		WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)factory.getBean("whiteboardDataDao");
		whiteboardDataDao.getWhiteboardDTOForRoom("room1");
	}
	
	
	public static List<WhiteboardData> getWhiteboardData(String roomName) {
		// TODO Auto-generated method stub
		
		
		if(factory == null)
			loadInit();
		WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)factory.getBean("whiteboardDataDao");
		List<WhiteboardData> l = whiteboardDataDao.getWhiteboardDTOForRoom(roomName);
		
		//List<WhitebordDTO> dtoList = new ArrayList<WhitebordDTO>();
		
		
		
		return l;
		
	}
	
	

}
