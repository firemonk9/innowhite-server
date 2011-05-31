package com.innowhite.PlayBackWebApp.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.util.Constants;

public class InvokeRemoteHttpService {

    private static final Logger log = LoggerFactory.getLogger(InvokeRemoteHttpService.class);

    public static void main(String[] args) throws Exception {
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet httpget = new HttpGet("http://demo.innowhite.com/");
	HttpResponse response = httpclient.execute(httpget);
	log.debug("" + response);
	HttpEntity entity = response.getEntity();
	if (entity != null) {
	    long len = entity.getContentLength();
	    if (len != -1 && len < 2048) {
		log.debug(EntityUtils.toString(entity));
		log.debug("If block");
	    } else {
		log.debug("Else block");
	    }
	}
    }

    public static void roomCloseService(String roomId) throws Exception {
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet httpget = new HttpGet(Constants.ROOM_CLOSE_URL);
	
	HttpResponse response = httpclient.execute(httpget);
	log.debug("" + response);
	HttpEntity entity = response.getEntity();
	if (entity != null) {
	    long len = entity.getContentLength();
	    if (len != -1 && len < 2048) {
		log.debug(EntityUtils.toString(entity));
		log.debug("If block");
	    } else {
		log.debug("Else block");
	    }
	}
    }

}
