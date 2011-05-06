/**
 * 
 */
package com.innowhite.whiteboard.webservice.client;

import com.innowhite.whiteboard.exception.InnowhiteServiceException;

/**
 * @author prashanth
 *
 */
public interface IServiceClient {
	
	public String createSession(String userID, String orgName) throws InnowhiteServiceException;
	public String createFutureSession(String userID, String orgName, String date) throws InnowhiteServiceException;
	public String joinSession(String userID, String orgName, String wbSessionID);


}
