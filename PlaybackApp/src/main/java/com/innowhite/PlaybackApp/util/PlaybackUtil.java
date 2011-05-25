package com.innowhite.PlaybackApp.util;

import java.util.Calendar;

public class PlaybackUtil {

    public static String getUnique() {

	String val = String.valueOf(Calendar.getInstance().getTimeInMillis());
	String r = "" + Math.round((Math.random() * 100)) + val.substring(5);
	return r;
    }
}
