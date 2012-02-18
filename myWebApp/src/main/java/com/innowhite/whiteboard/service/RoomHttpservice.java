package com.innowhite.whiteboard.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;

//import com.innowhite.PlayBackWebApp.util.Constants;
//import com.innowhite.PlayBackWebApp.util.WhiteBoardSHA1;

public class RoomHttpservice {

    private static  String ROOM_DETAIL_URL;

    private static final Logger log = LoggerFactory.getLogger(RoomHttpservice.class);

    public static void main(String[] args) throws Exception {
	getRoomDetailService("778938841");
    }

    
    static{
	
	ROOM_DETAIL_URL= InnowhiteProperties.getPropertyVal(InnowhiteConstants.SESSION_DETAIL_URL);
    }
    
    /*
     * This is a temporary fix. The saltkey and the url needs to be read from
     * database based on orgname.
     */

    public static String getRoomDetailService(String roomId) {

	log.debug(" Enterd getRoomDetailService roomID " + roomId);
	try {
	    if (roomId != null)
		roomId = roomId.trim();

	    HttpClient httpclient = new DefaultHttpClient();

	    // String checksumStr = Constants.ROOM_STR + roomId +
	    // Constants.SALT_KEY;
	    String comptedCheckum;

	    // comptedCheckum = WhiteBoardSHA1.SHA1(checksumStr);

	    // log.debug(" prechecksum string checksumStr  " + checksumStr +
	    // " computed checksum is : " + comptedCheckum);

	     
	    
	    String finalURL = ROOM_DETAIL_URL + roomId + ".xml";// Constants.ROOM_CLOSE_URL
								// +
								// Constants.ROOM_STR
								// + roomId +
								// "&checksum="
								// +
								// comptedCheckum;

	    log.debug(" executing the url to getRoomDetailService:: " + finalURL);

	    HttpGet httpget = new HttpGet(finalURL);
	    HttpResponse response = httpclient.execute(httpget);
	    log.debug("" + response.getStatusLine());
	    HttpEntity entity = response.getEntity();
	    StringBuffer sb = new StringBuffer();
	    if (response.getStatusLine().getStatusCode() == 200) {
		InputStream responseIS = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(responseIS));
		String line = reader.readLine();
		while (line != null) {
		    System.out.println(line);
		    line = reader.readLine();
		    if(line == null)
			continue;
		    sb.append(line+"\n");
		}
		return sb.toString();
	    }
	    log.error("Not able to get the session detail XML. :  " + finalURL + "  the status is : " + response.getStatusLine().getStatusCode());

	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	    e.printStackTrace();
	} catch (ClientProtocolException e) {
	    log.error(e.getMessage());
	    e.printStackTrace();
	} catch (IOException e) {
	    log.error(e.getMessage());
	    e.printStackTrace();
	}
	return null;
    }

}
