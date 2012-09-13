package com.innowhite.BinaryDataUpload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.myjavatools.web.ClientHttpRequest;

public class BinaryDataUploadTest extends TestCase {

	private String host = "localhost:8080";
	private static final String SCREEN_CAPTURE__URL = "/FirewallWebApp/MyBinTest";
	private URL url;
	URLConnection conn;

	private static final String SEQ_NUM = "seq";
	private static final String ROOM = "reqId";

	private static final String BLOCKDATA = "blockdata";

	private static Logger logger = Logger.getLogger("BinaryDataUploadTest");

	private static final String START = "http://localhost:8080/FirewallWebApp/MyBinTest?reqId=12342&seq=abc&status=START";
	private static final String STOP = "http://localhost:8080/FirewallWebApp/MyBinTest?reqId=12342&seq=abc&status=STOP";

	public void testUpload() throws Exception {

		// HttpClient client = new HttpClient();
		// url += "?";
		// Set keySet = parameters.keySet();
		// for (String string : keySet) {
		// url += string;
		// url += "=";
		// url += parameters.get(string);
		// url += "&";
		// }
		// PostMethod post = new PostMethod(url);
		// post.setRequestEntity(new ByteArrayRequestEntity(data));
		// int code = client.executeMethod(post);

	}

	private void openConnection() throws Exception {
		/**
		 * Need to re-establish connection each time, otherwise, we get
		 * java.net.ProtocolException: Cannot write output after reading input.
		 * 
		 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4382944
		 * 
		 */
		long start = System.currentTimeMillis();
		try {
			url = new URL("http://" + host + SCREEN_CAPTURE__URL);
			conn = url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new Exception("MalformedURLException " + url.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("IOException while connecting to "
					+ url.toString());
		}
		long end = System.currentTimeMillis();
		logger.info("Http[] Open connection took [" + (end - start) + " ms]");
	}

	public void testSendBlockData() {
		FileInputStream fis =null;
		try {

			invokeCurlCommand(START);

			File f = new File(
					"/Users/firemonk9/Documents/MyProjects/server/FirewallWebApp/FirewallWebApp/src/test/resources/31163.flv");

			fis = new FileInputStream(f);

			byte[] b = new byte[100000];
			int num = 0;
			int i = 0;
			while ((num = fis.read(b)) > 0) {
				sendBlockData_new("12342", i++, b);
//				if (i == 3)
//					break;
			}

			invokeCurlCommand(STOP);
			
			
        } catch (Exception e) {

			e.printStackTrace();
		}finally{
			try{
			fis.close();
			}catch(Exception e){}
		}

	}

	private void invokeCurlCommand(String cmd) {

		logger.info(" invoking command " + cmd);

		try {
			// Run ls command

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet req = new HttpGet(cmd);
            try {
                HttpResponse response = httpclient.execute(req);
            }catch (Exception e){
                e.printStackTrace();

            }



            } catch (Exception e) {
			e.printStackTrace(System.err);
		}

		// Read more:
		// http://javarevisited.blogspot.com/2011/02/how-to-execute-native-shell-commands.html#ixzz26HoKD9EV

	}

	private void sendBlockData(String room, int seq, byte[] b) {

		long start = System.currentTimeMillis();
		ClientHttpRequest chr;
		try {
			logger.info("Http[" + room + "] Open connection. In sendBlockData");
			openConnection();
			chr = new ClientHttpRequest(conn);
			chr.setParameter(ROOM, room);
			chr.setParameter(SEQ_NUM, seq);
			ByteArrayInputStream block = new ByteArrayInputStream(b);
			chr.setParameter(BLOCKDATA, "block", block);
			chr.post();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("ERROR: Failed to send block data.");
		}
		long end = System.currentTimeMillis();
		logger.info("[HTTP " + room + "] Sending " + b.length + " bytes took "
				+ (end - start) + " ms");
	}

	private void sendBlockData_new(String room, int seq, byte[] b) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + host
					+ SCREEN_CAPTURE__URL);

			// FileBody bin = new FileBody(new File(fileName));
			// StringBody comment = new StringBody("Filename: " + fileName);

			MultipartEntity reqEntity = new MultipartEntity();
			ByteArrayBody byt = new ByteArrayBody(b, "block");
			reqEntity.addPart(BLOCKDATA, byt);

			reqEntity.addPart(ROOM, new StringBody(room));
			
			reqEntity.addPart(SEQ_NUM, new StringBody(""+seq));

			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			
			HttpEntity resEntity = response.getEntity();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
