package com.innowhite.whiteboard.service;

import junit.framework.TestCase;

public class LoadBalancerServiceTest extends TestCase {

    public void testGetServersName() {

	System.err.println(LoadBalancerService.getServerURL("VideoApp", null));
	System.err.println(LoadBalancerService.getServerURL("VideoApp", null));
	System.err.println(LoadBalancerService.getServerURL("VideoApp", null));
	System.err.println(LoadBalancerService.getServerURL("DataApp", null));
	System.err.println(LoadBalancerService.getServerURL("DataApp", null));
	assertTrue(true);
    }

}
