package com.innowhite.whiteboard.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.beans.ClientMediaVO;
import com.innowhite.whiteboard.persistence.dao.ClientMediaDAO;
import com.util.AeSimpleMD5;
import com.util.InnowhiteProperties;
import com.util.Utility;

public class DownloadThread implements Runnable {
	//private int sleepTime; // random sleep time for thread

	private ClientMediaVO clientMediaVO; // name of thread

	// private static Random generator = new Random();

	
	private static Logger log = Red5LoggerFactory.getLogger(DownloadThread.class, "whiteboard");
	
	public static void main(String args[]) {
		// log.debug(createSecureURL("http://223.27.120.34/frontend.php/helper/library/content/image/stream/35"));

		log.debug(createSecureURL("http://edu.inetdemo.in/frontend.php/helper/library/content/video/stream/66.flv", "66"));
		// log.debug(AeSimpleMD5.MD5("35inno*456white123*key"));

	}

	// assign name to thread
	public DownloadThread(ClientMediaVO name) {
		clientMediaVO = name; // set name of thread

		// pick random sleep time between 0 and 5 seconds
		// sleepTime = generator.nextInt(5000);
	} // end PrintTask constructor

	// method run is the code to be executed by new thread
	public void run() {
		System.out.printf(" going to sleep for  milliseconds.\n" + this);

		URL u = null;
		try {

			String origfilename = clientMediaVO.getContentSource();
			System.out.printf(this + " origfilename :::" + origfilename);
			String contentURL = createSecureURL(clientMediaVO.getContentSource(), clientMediaVO.getContentId());

			u = new URL(contentURL);
			log.debug(" The URL is :::" + contentURL);
			URLConnection uc = u.openConnection();
			// String contentType = uc.getContentType();
			int contentLength = uc.getContentLength();
			// if (contentType.startsWith("text/") || contentLength == -1) {
			// throw new IOException("This is not a binary file.");
			// }

			log.debug(" The file size " + contentLength);
			InputStream raw = uc.getInputStream();
			InputStream in = new BufferedInputStream(raw);
			//byte[] data = new byte[contentLength];
			int bytesRead = 0;
			int offset = 0;

			String outputfile = origfilename.substring(origfilename.lastIndexOf('/') + 1);

			String path = getBaseLocaPath(clientMediaVO.getContentType()) +Utility.getUnique() +"_" +outputfile;

			File ff = new File(path);

			FileOutputStream out = new FileOutputStream(ff);

//			while (offset < contentLength) {
//				bytesRead = in.read(data, offset, data.length - offset);
//				if (bytesRead == -1)
//					break;
//				offset += bytesRead;
//			}

			int x=0;
			byte data[] = new byte[1024];
			while ((x=in.read(data, 0, 1024)) >= 0) {
				out.write(data,0,x);
				//data = new byte[1024];
			}
			out.flush();
			out.close();
			in.close();

			log.debug(this + " origfilename :::" + origfilename + " orig " + clientMediaVO.getContentSource() +"  origname "+clientMediaVO.getOrgName());

			//out.write(data);
			
			//out.close();

			log.debug(this + " The file written to ::  " + out + "   fileName " + outputfile + "  abs path " + ff.getAbsolutePath());
			
			// Once the file is saved ... update the table.
			clientMediaVO.setContentSource(ff.getAbsolutePath());
			
		
			
			
			ClientMediaDAO.saveMedia(clientMediaVO);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print thread name
		System.out.printf(this + " done sleeping\n", "");
	} // end method run

	private String getBaseLocaPath(String contentType) {

//		if (contentType != null) {
//			if (contentType.equals(InnowhiteConstants.VIDEO_TYPE)) {
//				return InnowhiteProperties.getPropertyVal("VIDEOS_PATH");
//			}
//		}

		return InnowhiteProperties.getPropertyVal("MEDIA_PATH");
	}

	private static String createSecureURL(String url, String contentId) {

		try {

			// String filename = url.substring(url.lastIndexOf('/') + 1);
			
			int begin = url.lastIndexOf("/");
			int end = url.lastIndexOf(".");
			
			contentId = url.substring(begin+1, end);
			
			String filename = contentId + "inno*456white123*key";
			log.debug(" filename :: " + filename);
			// byte[] thedigest = md.digest(bytesOfMessage);

			url = url + "/" + AeSimpleMD5.MD5(filename);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;

	}

}
