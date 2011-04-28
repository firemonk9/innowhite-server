package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.servlets.WTest;

public class UploadImages  {

	private static Logger log = Red5LoggerFactory.getLogger(UploadImages.class, "whiteboard");
	
	public static File createUploadedFile(String encodedStr,String fileName) {

		//String base64str = request.getParameter(Constants.IMAGE_DATE);
		Base64 b64 = new Base64();
		byte[] decoded = b64.decode(encodedStr.getBytes());
		//String fileName = request.getParameter(Constants.FILE_NAME);
		FileOutputStream fop = null;
		File f=null;
		try {
			
			String urlPath = Constants.UBUNTU_FOLDER_PATH;
			log.debug("  fileName  "+fileName+" urlPath "+urlPath);
			
		
			
		//	urlPath = Constants.APP_NAME;//urlPath.replaceAll(Constants.APP_NAME, request.getContextPath().substring(1));
		
			
			f = new File(urlPath,
					fileName);
			fop = new FileOutputStream(f);
			if (f.exists())
				fop.write(decoded);

			log.debug(f.getAbsolutePath());

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (fop != null)
				try {
					fop.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return f;
	}


}
