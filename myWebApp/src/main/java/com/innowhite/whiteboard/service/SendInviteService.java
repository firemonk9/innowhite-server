package com.innowhite.whiteboard.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.CallBackURLsVO;
import com.innowhite.whiteboard.persistence.dao.CallBackUrlsDAO;

public class SendInviteService {

	private static final Logger log = LoggerFactory.getLogger(SendInviteService.class);
	private static String INVITE_URL = "http://innowhite.com/api/send_emails";

	// static {
	//
	// INVITE_URL =
	// InnowhiteProperties.getPropertyVal(InnowhiteConstants.SESSION_DETAIL_URL);
	// }

	public static void main(String args[]) {

		String myXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><invitees>" + "<roomId> 123123123</roomId>" + "<list>abc@abc.com, qwe@aqsdfa.com</list></invitees>";
		getRoomId(myXml);

		// sendInviteService(myXml);

	}

	public static void sendInviteService(String xml) {
		log.debug(" Enterd roomCloseService roomID ");
		try {

			HttpClient httpclient = new DefaultHttpClient();

			// String checksumStr = Constants.ROOM_STR + roomId +
			// Constants.SALT_KEY;
			String comptedCheckum;

			// comptedCheckum = WhiteBoardSHA1.SHA1(checksumStr);

			// log.debug(" prechecksum string checksumStr  " + checksumStr +
			// " computed checksum is : " + comptedCheckum);

			String finalURL = INVITE_URL;// Constants.ROOM_CLOSE_URL
			// +
			// Constants.ROOM_STR
			// + roomId +
			// "&checksum="
			// +
			// comptedCheckum;

			String roomId = getRoomId(xml);

			CallBackURLsVO vo = CallBackUrlsDAO.getCallBackVO(roomId);
			if (vo != null) {
				finalURL = vo.getInviteSendEmailsUrl();
			} else {

				log.warn(" Did not get call back URL VO object for roomId :: " + roomId);
			}

			log.debug(" executing the url to getRoomDetailService:: " + finalURL);

			HttpPost httpPost = new HttpPost(finalURL);

			List<NameValuePair> dataToPost = new ArrayList<NameValuePair>();
			dataToPost.add(new BasicNameValuePair("yourxml", xml));
			httpPost.setEntity(new UrlEncodedFormEntity(dataToPost));

			HttpResponse response = httpclient.execute(httpPost);
			log.debug("" + response.getStatusLine());
			HttpEntity entity = response.getEntity();
			StringBuffer sb = new StringBuffer();
			if (response.getStatusLine().getStatusCode() == 200) {
				InputStream responseIS = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseIS));
				String line = reader.readLine();
				while (line != null) {
					log.debug(line);
					line = reader.readLine();
					if (line == null)
						continue;
					sb.append(line + "\n");
				}
				// return sb.toString();
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
		// return null;
	}

	private static String getRoomId(String xml) {
		if (xml != null) {
			int begin = xml.indexOf("<roomId>");
			int end = xml.indexOf("</roomId>");
			String roomId = xml.substring(begin + 8, end);
			// System.err.println(" roomId ::"+roomId);
			return roomId;
		}

		return null;
	}

}
