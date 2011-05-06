package com.innowhite.whiteboard.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhiteBoardSHA1 {

	private static final Logger log = LoggerFactory.getLogger(WhiteBoardSHA1.class);
	
	 private static String convertToHex(byte[] data) {
	        StringBuffer buf = new StringBuffer();
	        for (int i = 0; i < data.length; i++) {
	            int halfbyte = (data[i] >>> 4) & 0x0F;
	            int two_halfs = 0;
	            do {
	                if ((0 <= halfbyte) && (halfbyte <= 9))
	                    buf.append((char) ('0' + halfbyte));
	                else
	                    buf.append((char) ('a' + (halfbyte - 10)));
	                halfbyte = data[i] & 0x0F;
	            } while(two_halfs++ < 1);
	        }
	        return buf.toString();
	    }
	 
	    public static String SHA1(String text) 
	    throws NoSuchAlgorithmException, UnsupportedEncodingException  {
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
	    }
	    
	    
	    public static void main(String[] args) {
			try {
			String sha1 = 	WhiteBoardSHA1.SHA1("parentOrg=innowhite&orgName=innowhite&user=user1123ibsdiiuojsndkakjsdl");
			log.debug("sha1: "+sha1);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
