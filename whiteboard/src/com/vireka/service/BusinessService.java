package com.vireka.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.vireka.business.TimeMaintainerService;

public class BusinessService {

	
	private static Logger log = Red5LoggerFactory.getLogger(BusinessService.class, InnowhiteConstants.APP_NAME);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// authSession("78636026887");
		getNewSession();
	}

	public static String getNewSession() {

		String line=null;
		try {
			String xmlText = "<wb_session> " + "<author>Ram</author>"
					+ "<description>Math 101</description>" + "</wb_session>";

			URL url = new URL("http://localhost:3000/wb_sessions.xml");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
			wr.write(xmlText);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			
			if ((line = rd.readLine()) != null) {
				log.debug(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			log.debug("Error" + e);
		}
		return line;
	}

	public static boolean authSession(String sessId) {

		String str;
		try {
			log.debug("in auth session :"+sessId);
			URL url = new URL(
					"http://localhost:3000/auth_session/authinticateSession/"+sessId);
			URLConnection urlConnection = url.openConnection();
			// urlConnection.setRequestProperty("accept", "text/xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			

			if ((str = in.readLine()) != null) {
				log.debug(str);
			}
			if(str != null)
			{
				if(str.equals("true"))
					return true;
			}
			in.close();
		} catch (Exception e) {
			log.debug(" exception ... ",e);
		}
		return false;
	}

}
