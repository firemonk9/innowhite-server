/**
 * 
 */
package com.innowhite.whiteboard.test;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.Servlet.WBFilter;
import com.innowhite.whiteboard.webservice.client.ServiceClientImp;

/**
 * @author prashanth
 *
 */
public class InnowhiteServiceTest {

	private static Logger log = Red5LoggerFactory.getLogger(InnowhiteServiceTest.class, "whiteboard");
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
