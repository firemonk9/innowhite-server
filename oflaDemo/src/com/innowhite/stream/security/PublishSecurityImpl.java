package com.innowhite.stream.security;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IScope;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.slf4j.Logger;

import com.innowhite.stream.Application;

public class PublishSecurityImpl implements IStreamPublishSecurity{

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "SecurityImpl");
	
	@Override
	public boolean isPublishAllowed(IScope scope, String arg1, String arg2) {
		log.info("entered isPublishAllowed ::"+scope.getContextPath()+" arg1 ::"+arg1+" arg2 "+arg2);
		
		System.err.println("entered isPublishAllowed ::"+scope.getContextPath()+" arg1 ::"+arg1+" arg2 "+arg2);
		
		return true;
	}

}
