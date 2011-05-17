package com.innowhite.whiteboard.service;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;

public class WhiteboardToVideoService {

	private static HashMap<String, DataOutputStream> roomFileMap = new HashMap<String, DataOutputStream>();
	private static final Logger log = LoggerFactory
			.getLogger(WhiteboardToVideoService.class);

	public static void main(String args[]) {

		//Base64 b64= new Base64();
		
		
		
		//b64.
		//BASE64Decoder bd = new BASE64Decoder();
		String s = "RkxWAQEAAAAJAAAAABIAAK4AAAAAAAAAAgAKb25NZXRhRGF0YQgAAAAHAAV3aWR0aABAdAAAAAAAAAAGaGVpZ2h0AEB0AAAAAAAAAAlmcmFtZXJhdGUAQBAAAAAAAAAADHZpZGVvY29kZWNpZABACAAAAAAAAAAMY2FuU2Vla1RvRW5kAQEAD21ldGFkYXRhY3JlYXRvcgIAKVNpbXBsZUZMVldyaXRlci5hcyB2MC44IHplcm9wb2ludG5pbmUuY29tAAAJ";
//		byte[] bbb = s.getBytes();
//		byte[] result = b64.decode(bbb);
		
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes;
		try {
			decodedBytes = decoder.decodeBuffer(s);

			String filePath = "/Users/firemonk/Desktop/javaVideo.flv";
			DataOutputStream os = new DataOutputStream(new FileOutputStream(filePath));
			os.write(decodedBytes);
			
//		for(int i=0;i<decodedBytes.length;i++)
//			System.err.println(i+"    "+decodedBytes[i]);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeToFile(byte[] data, String roomId) {
		try {
			log.debug("entered writeToFile " + roomId);
			DataOutputStream os = null;
			if (roomId != null && roomFileMap.get(roomId) != null) {
				os = (DataOutputStream) roomFileMap.get(roomId);

			} else if (roomFileMap.get(roomId) == null) {
				String filePath = InnowhiteProperties
						.getPropertyVal(InnowhiteConstants.TEMP_LOCATION)
						+ roomId + ".flv";
				os = new DataOutputStream(new FileOutputStream(filePath));
				roomFileMap.put(roomId, os);

			}
			os.write(data);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static void stopRecording(String roomId) {
		log.debug("entered stopRecording ");
		if (roomId != null && roomFileMap.get(roomId) != null) {
			DataOutputStream os = (DataOutputStream) roomFileMap.get(roomId);
			try {
				os.close();
				roomFileMap.remove(roomId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

}
