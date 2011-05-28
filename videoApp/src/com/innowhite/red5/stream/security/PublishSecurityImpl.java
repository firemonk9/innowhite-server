package com.innowhite.red5.stream.security;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IScope;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.slf4j.Logger;

import com.innowhite.red5.stream.Application;
import com.innowhite.red5.stream.messaging.VideoStreamNameListener;

public class PublishSecurityImpl implements IStreamPublishSecurity{

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "VideoApp");

	private boolean enableSecurity=true;
	
	public PublishSecurityImpl(String enableSecurity2) {
		if(enableSecurity2 != null &&  enableSecurity2.equals("true"))
			this.enableSecurity = true;
		else
			this.enableSecurity = false;
	}

	@Override
	public boolean isPublishAllowed(IScope scope, String arg1, String arg2) {
		
//		
		
		if(enableSecurity == false)
			return true;
		
		if(scope.getName() != null && VideoStreamNameListener.videoStreamIds.contains(scope.getName()))
		{
			//System.err.println("remove this isPublishAllowed ... "+scope.getName()+"  ip ");
			return true;
		}
		
		log.warn(" ********  unauthorized access to server from room ... "+scope.getName()+"  ip ");
//		System.err.println(" ********  unauthorized access to server from room ... "+scope.getName()+"  ip ");
		return false;

		
	}
	
//	@Override
//	public boolean isPublishAllowed(IScope scope, String arg1, String arg2) {
//		log.info("entered isPublishAllowed ::"+scope.getContextPath()+" arg1 ::"+arg1+" arg2 "+arg2);
//		
//		System.err.println("entered isPublishAllowed ::"+scope.getContextPath()+" arg1 ::"+arg1+" arg2 "+arg2);
//		
//		return true;
//	}


	
	
	

}
