package com.innowhite.whiteboard.service;

import java.util.Date;

import com.innowhite.whiteboard.persistence.dao.RoomUsersDAO;

public class TimeService {

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub

    }

    public static String getElapsedTime(String room) {

	Date time = RoomUsersDAO.getRoomStartTime(room);

	long diff =0;
	if (time != null) {

	    diff = (new Date().getTime() - time.getTime())/1000;
	}
	StringBuffer xml = new StringBuffer();
	xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	xml.append("<session_time>");
	xml.append(""+diff);
	xml.append("</session_time>");

	return xml.toString();
    }
    
    
    public static String getElapsedTimeStr(String room) {

	Date time = RoomUsersDAO.getRoomStartTime(room);

	long diff =0;
	if (time != null) {

	    diff = (new Date().getTime() - time.getTime())/1000;
	}
	StringBuffer xml = new StringBuffer();
	xml.append("<session_time>"+diff+"</session_time>");
	

	return xml.toString();
    }
    
    
    

}
