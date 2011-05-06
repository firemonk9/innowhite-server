/**
 * 
 */
package com.innowhite.whiteboard.webservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.HttpClient.HttpClientService;
import com.innowhite.whiteboard.exception.InnowhiteServiceException;
import com.innowhite.whiteboard.util.InnowhiteConstants;

/**
 * @author prashanth
 *
 */
public class ServiceClientImp  {

	/* (non-Javadoc)
	 * @see com.innowhite.whiteboard.webservice.client.IServiceClient#createFutureSession(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	private static final Logger log = LoggerFactory.getLogger(ServiceClientImp.class);
	

	
	public static String createFutureSession(String userID, String orgName,String courses ,String date) throws InnowhiteServiceException {
		if((userID==null)||(orgName==null)|| (date==null)|| date.trim().length()==0 || userID.trim().length()==0 || orgName.trim().length()==0){
			
			throw new InnowhiteServiceException("Invalid Arguments for userID and orgName");
		}
	//	HttpClientService httpClientService = new HttpClientService();
		String wbSessionID ="";
		try {
			wbSessionID = HttpClientService.createSession(userID, orgName,courses ,date);
		} catch (InnowhiteServiceException e) {
			throw new InnowhiteServiceException("Unsupported Encoding Exception");
		} 
		return wbSessionID;
	}

	/* (non-Javadoc)
	 * @see com.innowhite.whiteboard.webservice.client.IServiceClient#createSession(java.lang.String, java.lang.String)
	 */
	
	public static String createSession(String userID, String orgName, String courses) throws InnowhiteServiceException{
		
		log.debug("entering createSession");
		if((userID==null)||(orgName==null)||userID.trim().length()==0 || orgName.trim().length()==0){
			
			throw new InnowhiteServiceException("Invalid Arguments for userID and orgName");
		}
		//HttpClientService httpClientService = new HttpClientService();
		String wbSessionID ="";
		try {
			wbSessionID = HttpClientService.createSession(userID, orgName,courses ,null);
			
			HttpClientService.genericClient(InnowhiteConstants.JOIN_ROOM_KEY, userID, wbSessionID, orgName);

		} catch (InnowhiteServiceException e) {
			throw new InnowhiteServiceException("Unsupported Encoding Exception");
		} 
		
		log.debug(" wbSessionID returned from server::"+wbSessionID);
		log.debug("exiting createSession");
		return wbSessionID;
	}

	
	/* (non-Javadoc)
	 * @see com.innowhite.whiteboard.webservice.client.IServiceClient#createSession(java.lang.String, java.lang.String)
	 */
	
	public  static String closeSession(String userID, String wbSessionID,String orgName) throws InnowhiteServiceException{
		if((userID==null)||(wbSessionID==null)||userID.trim().length()==0 || wbSessionID.trim().length()==0){
			
			throw new InnowhiteServiceException("Invalid Arguments for userID and orgName");
		}
		//HttpClientService httpClientService = new HttpClientService();
		//String wbSessionID ="";
		try {
			HttpClientService.genericClient(InnowhiteConstants.LEAVE_ROOM_KEY, userID, wbSessionID, orgName);
		} catch (InnowhiteServiceException e) {
			throw new InnowhiteServiceException("Unsupported Encoding Exception");
		} 
		return wbSessionID;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.innowhite.whiteboard.webservice.client.IServiceClient#joinSession(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public static void leaveRoom(String userID, String orgName, String wbSessionID)throws InnowhiteServiceException {
				
		log.debug("entering leaveRoom");
		
		log.debug("userId :"+userID+" wbSessionID "+wbSessionID);
		if((userID==null)||(wbSessionID==null)||userID.trim().length()==0 ){
			
			throw new InnowhiteServiceException("Invalid Arguments for userID and orgName");
		}
		//HttpClientService httpClientService = new HttpClientService();
		//String wbSessionID ="";
		try {
						
			HttpClientService.genericClient(InnowhiteConstants.LEAVE_ROOM_KEY, userID, wbSessionID, orgName);

		} catch (InnowhiteServiceException e) {
			throw new InnowhiteServiceException("Unsupported Encoding Exception");
		} 
		
		log.debug(" wbSessionID returned from server::"+wbSessionID);
		log.debug("exiting leaveRoom");
	}

}
