package com.innowhite.red5.stream.security;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;
import org.slf4j.Logger;

import com.innowhite.red5.stream.Application;

public class CustomFileNameGeneratorImpl implements IStreamFilenameGenerator{

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "oflaDemo");

	@Override
	public String generateFilename(IScope arg0, String arg1, GenerationType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateFilename(IScope arg0, String arg1, String arg2,
			GenerationType arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean resolvesToAbsolutePath() {
		// TODO Auto-generated method stub
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
