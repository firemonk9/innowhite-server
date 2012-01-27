package com.innowhite.PlayBackWebApp.service;

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

import com.innowhite.PlayBackWebApp.dao.CallBackUrlsDao;
import com.innowhite.PlayBackWebApp.util.Constants;
import com.innowhite.PlayBackWebApp.util.WhiteBoardSHA1;

public class InvokeRemoteHttpService {

    private static final Logger log = LoggerFactory.getLogger(InvokeRemoteHttpService.class);

    public static void main(String[] args) throws Exception {
	roomCloseService("12312312", "");
    }

    public static void roomCloseService(String roomId, String url) {

	log.debug(" Enterd roomCloseService roomID " + roomId);
	try {
	    if (roomId != null)
		roomId = roomId.trim();

	    HttpClient httpclient = new DefaultHttpClient();

	    String checksumStr = Constants.ROOM_STR + roomId + Constants.SALT_KEY;
	    String comptedCheckum;

	    comptedCheckum = WhiteBoardSHA1.SHA1(checksumStr);

	    log.debug(" prechecksum string checksumStr  " + checksumStr + " computed checksum is : " + comptedCheckum);

	    String actUrl = Constants.ROOM_CLOSE_URL;
	    if (url != null)
	    	actUrl = url;

	    if(actUrl != null)
	    {
	    	if(!actUrl.endsWith("?") == true)
	    	{
	    		actUrl+="?";
	    	}
	    	
	    }
	    
	    String finalURL = actUrl + Constants.ROOM_STR + roomId + "&checksum=" + comptedCheckum;

	    log.debug(" executing the url to notify of closing the room :: " + finalURL);

	    HttpGet httpget = new HttpGet(finalURL);
	    HttpResponse response = httpclient.execute(httpget);
	    log.debug("" + response.getStatusLine());
	    // HttpEntity entity = response.getEntity();

	    // if (entity != null) {
	    // long len = entity.getContentLength();
	    // if (len != -1 && len < 2048) {
	    // log.debug(EntityUtils.toString(entity));
	    // log.debug("If block");
	    // } else {
	    // log.debug("Else block "+response.getStatusLine());
	    // }
	    // }
	} catch (NoSuchAlgorithmException e) {
	    log.error(e.getMessage());
	    e.printStackTrace();
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
    }

}
