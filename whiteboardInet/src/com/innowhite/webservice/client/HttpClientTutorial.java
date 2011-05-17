package com.innowhite.webservice.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;




public class HttpClientTutorial {
	private static Logger log = Red5LoggerFactory.getLogger(HttpClientTutorial.class, "whiteboard");
	
	 
	  private static String url = "http://www.apache.org/";

	  public static void main(String[] args) throws Exception {
		  HttpClient httpclient = new DefaultHttpClient();
		  HttpGet httpget = new HttpGet("http://localhost:8080/");
		  HttpResponse response = httpclient.execute(httpget);
		  log.debug(""+response);
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
