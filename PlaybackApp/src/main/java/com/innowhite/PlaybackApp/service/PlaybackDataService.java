package com.innowhite.PlaybackApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.PlaybackApp.model.WhiteboardData;
import com.innowhite.PlaybackApp.model.WhiteboardDataDao;
import com.innowhite.PlaybackApp.model.WhitebordDTO;

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
	
	
	public static List<WhitebordDTO> getWhiteboardData(String roomName) {
		// TODO Auto-generated method stub
		
		
		if(factory == null)
			loadInit();
		WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)factory.getBean("whiteboardDataDao");
		List<WhiteboardData> l = whiteboardDataDao.getWhiteboardDTOForRoom(roomName);
		
		List<WhitebordDTO> dtoList = new ArrayList<WhitebordDTO>();
		
		for(WhiteboardData wb : l){
			
			WhitebordDTO dto = new WhitebordDTO();
			dto.setBordercolor(wb.getBordercolor());
			dto.setFillcolor(wb.getFillcolor());
			dto.setImageURL(wb.getImageURL());
			dto.setMainscalex(wb.getMainscalex());
			dto.setMainscaley(wb.getMainscaley());
			dto.setObjDate(wb.getObjDate());
			dto.setObjName(wb.getObjName());
			dto.setObjType(wb.getObjType());
			dto.setPenthickness(wb.getPenthickness());
			dto.setPoints(wb.getPoints());
			dto.setRoomName(wb.getRoomName());
			dto.setRotation(wb.getRotation());
			dto.setSecondName(wb.getSecondName());
			dto.setSecondSeq(wb.getSecondSeq());
			dto.setSeq(wb.getSeq());
			dto.setShpHeight(wb.getShpHeight());
			dto.setShpWidth(wb.getShpWidth());
			dto.setSprText(wb.getSprText());
			dto.setTxtFont(wb.getTxtFont());
			dto.setTxtType(wb.getTxtType());
			dto.setUserId(wb.getUserId());
			dto.setVersionNumber(wb.getVersionNumber());
			dto.setWbNumber(wb.getWbNumber());
			dto.setX1(wb.getX1());
			dto.setX2(wb.getX1());
			dto.setXpos(wb.getXpos());
			dto.setY1(wb.getY1());
			dto.setY2(wb.getY2());
			dto.setYpos(wb.getYpos());
			
			dtoList.add(dto);
		}
		
		return dtoList;
		
	}
	
	

}
