package com.innowhite.whiteboard.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WhiteBoardUtil {

	
	
	
	public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
