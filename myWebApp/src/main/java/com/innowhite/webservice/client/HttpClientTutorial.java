package com.innowhite.webservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientTutorial {
    private static final Logger log = LoggerFactory.getLogger(HttpClientTutorial.class);

    private static String url = "http://www.apache.org/";

    public static void main(String[] args) throws Exception {
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpGet httpget = new HttpGet("http://localhost:8080/");
	// HttpResponse response = httpclient.execute(httpget);
	// log.debug(""+response);
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// long len = entity.getContentLength();
	// if (len != -1 && len < 2048) {
	// log.debug(EntityUtils.toString(entity));
	// log.debug("If block");
	// } else {
	// log.debug("Else block");
	// }
	//
	// }
    }

}
