/**
 * 
 */
package com.innowhite.whiteboard.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.webservice.client.ServiceClientImp;

/**
 * @author prashanth
 *
 */
public class InnowhiteServiceTest {

	private static final Logger log = LoggerFactory.getLogger(InnowhiteServiceTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//ServiceClientImp service = new ServiceClientImp();
		//String content = service.createSession("teacher", "xyz");
		String content = ServiceClientImp.createFutureSession("teacher", "xyz","math" ,"03/20/2010");
		
		log.debug("content in main method "+content);

		//deleteRooms("","","");
		
	}

}
