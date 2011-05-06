/**
 * 
 */
package com.innowhite.whiteboard.HttpClient;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.innowhite.whiteboard.exception.InnowhiteServiceException;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * @author prashanth
 * 
 */
public class HttpClientService {

	
	private static final Logger log = LoggerFactory.getLogger(HttpClientService.class);
	public static String createSession(String userID, String orgName, String courses, String date) throws InnowhiteServiceException {

		
		//HttpClient
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpContext localContext = new BasicHttpContext();
//		HttpResponse response = null;
//		String content = "";
//		String createSessionURL = InnowhiteConstants.CREATESESSIONURL.replace(InnowhiteConstants.TOKEN, orgName);
//		String webSesionID = "";
//		boolean futureDateFlag = true;
//		if (date == null || date.trim().length() == 0) {
//			futureDateFlag = false;
//		}
//		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		formparams.add(new BasicNameValuePair(InnowhiteConstants.USERID, userID));
//		formparams.add(new BasicNameValuePair(InnowhiteConstants.ORGNAME, orgName));
//		formparams.add(new BasicNameValuePair(InnowhiteConstants.COURSES, courses));
//		formparams.add(new BasicNameValuePair(InnowhiteConstants.SECRETKEY, InnowhiteConstants.SECRETKEYVALUE));
//		if (futureDateFlag) {
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.DATEPICKER, date));
//		}
//
//		try {
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//			HttpPost httppost = new HttpPost(createSessionURL);
//			httppost.setHeader("Accept", "application/xml");
//			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//
//			httppost.setEntity(entity);
//			response = httpclient.execute(httppost, localContext);
//			// log.debug("response from post: "+response);
//			HttpEntity httpEntity = response.getEntity();
//			if (httpEntity != null) {
//				content = EntityUtils.toString(httpEntity);
//				webSesionID = getSessionID(content);
//				// log.debug(content);
//				// log.debug("----------------------------------------");
//				// log.debug("Uncompressed size: "+content.length());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new InnowhiteServiceException("Service Exception");
//		} finally {
//		}

		//return webSesionID;
		return null;
	}

	public static void genericClient(String key, String userID, String wbSessionId, String orgName) throws InnowhiteServiceException {
//
//		log.debug(" Entering  genericClient::"+key);
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		HttpContext localContext = new BasicHttpContext();
//		HttpResponse response = null;
//		//String createSessionURL = InnowhiteConstants.CREATESESSIONURL.replace(InnowhiteConstants.TOKEN, orgName);
//
//		List<NameValuePair> formparams = getParams(key, userID, wbSessionId, orgName);
//		
//		String serviceURL = getKeyURL(key);

//		try {
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//			HttpPost httppost = new HttpPost(serviceURL);
//			httppost.setHeader("Accept", "application/xml");
//			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//
//			httppost.setEntity(entity);
//			response = httpclient.execute(httppost, localContext);
//			// log.debug("response from post: "+response);
//			//HttpEntity httpEntity = response.getEntity();
//
//			log.debug(" Exiting  genericClient::"+key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new InnowhiteServiceException("Service Exception");
//		} finally {
//		}
	}

	
	/*Returns the params for a given web service
	 * 
	 * */
	private static List getParams(String key, String userID, String wbSessionId, String orgName) {

//		if (key != null & key.equals(InnowhiteConstants.JOIN_ROOM_KEY)) {
//			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.USERID, userID));
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.ORGNAME, orgName));
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.SECRETKEY, InnowhiteConstants.SECRETKEYVALUE));
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.WB_SESSION_ID, wbSessionId));
//			return formparams;
//
//		}
//		if (key != null & key.equals(InnowhiteConstants.LEAVE_ROOM_KEY)) {
//
//			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.USERID, userID));
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.ORGNAME, orgName));
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.SECRETKEY, InnowhiteConstants.SECRETKEYVALUE));
//			formparams.add(new BasicNameValuePair(InnowhiteConstants.WB_SESSION_ID, wbSessionId));
//			return formparams;
//		}
		return null;
	}

	
	/*Returns the WEB SERVICE URL for a key
	 * */
	private static String getKeyURL(String key) {

		if (key != null & key.equals("JOIN_ROOM_URL")) {
			return InnowhiteConstants.JOIN_ROOM_URL;	
		}
		if (key != null & key.equals("LEAVE_ROOM_URL")) {
			return InnowhiteConstants.LEAVE_ROOM_URL;
		}
		return null;
	}

	private static String getSessionID(String responseXML) throws InnowhiteServiceException {

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		Document document;
		String wbSessionID = "-1";

		try {
			
			//log.debug(" content :: "+responseXML);
			
			builder = dbfactory.newDocumentBuilder();
			is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));
			document = builder.parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile("//cache-session/session-id[1]/text()");

			Object result = expr.evaluate(document, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			for (int i = 0; i < nodes.getLength(); i++) {
				// log.debug(nodes.item(i).getNodeValue());
				wbSessionID = nodes.item(i).getNodeValue().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new InnowhiteServiceException("Parsing Exception ");
		}

		return wbSessionID;
	}

}
