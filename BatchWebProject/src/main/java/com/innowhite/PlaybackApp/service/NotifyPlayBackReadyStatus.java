package com.innowhite.PlaybackApp.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.util.Constants;
import com.innowhite.PlaybackApp.util.WhiteBoardSHA1;

public class NotifyPlayBackReadyStatus {

    private static final Logger log = LoggerFactory.getLogger(NotifyPlayBackReadyStatus.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
	notifyPlayBackReady("993048460","");

    }

    /*
     * This is a temporary fix. The saltkey and the url needs to be read from
     * database based on orgname.
     */

    public static void notifyPlayBackReady(String roomId, String notifyPlayBackStatusUrl) {

	log.info(" Enterd notifyPlayBackReady roomID " + roomId);
	try {
	    if (roomId != null)
		roomId = roomId.trim();

	    HttpClient httpclient = new DefaultHttpClient();

	    String checksumStr = Constants.ROOM_STR + roomId + Constants.SALT_KEY;
	    String comptedCheckum;

	    comptedCheckum = WhiteBoardSHA1.SHA1(checksumStr);

	    log.debug(" prechecksum string checksumStr  " + checksumStr + " computed checksum is : " + comptedCheckum);

	   // String finalURL = Constants.ROOM_CLOSE_URL + Constants.ROOM_STR + roomId + "&checksum=" + comptedCheckum;
	    String finalURL = notifyPlayBackStatusUrl + Constants.ROOM_STR + roomId + "&checksum=" + comptedCheckum;

	    log.debug(" executing the url to notify of playback video ready :: " + finalURL);

	    HttpGet httpget = new HttpGet(finalURL);
	    HttpResponse response = httpclient.execute(httpget);
	    log.debug("The return status from playback status update ::" + response.getStatusLine());

	} catch (NoSuchAlgorithmException e) {
	    log.error(e.getMessage(), e);

	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage(), e);

	} catch (ClientProtocolException e) {
	    log.error(e.getMessage(), e);

	} catch (IOException e) {
	    log.error(e.getMessage(), e);

	}
    }

}
