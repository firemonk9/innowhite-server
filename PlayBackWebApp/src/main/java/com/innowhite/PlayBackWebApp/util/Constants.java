package com.innowhite.PlayBackWebApp.util;

public class Constants {

    
    // this is a tempoary solution need to get this from database.
    public static final String ROOM_CLOSE_URL = "http://www.innowhite.com/room_sessions/leave_room?";
    
    public static final String ROOM_STR = "roomId=";
    
    // the salt key, tempoary solution need to get this from database.
    public static final String SALT_KEY = "salt_key_fgjfreyhj78_checksum";   
    
    public static final String JOINED ="JOINED";
    
    public static final String LEFT ="LEFT";
    public static final String SINGLE_ANSWER_QUESTION ="singleanswer";
    public static final String MULTIPLE_ANSWER_QUESTION = "multipleanswers";
    
    public static final int MONITOR_ROOM_CLOSE_FREQ = 20*60*1000;
    
}
