package com.innowhite.PlayBackWebApp.service;

import junit.framework.TestCase;


public class RemoteHttpServiceTests extends TestCase {

    
    public void testRoomCloseService() {

	InvokeRemoteHttpService.roomCloseService("1234","");	
	
	assertTrue(true);
    }
}
