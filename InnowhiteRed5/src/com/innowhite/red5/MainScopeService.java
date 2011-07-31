package com.innowhite.red5;

import java.util.HashMap;

import org.red5.server.Scope;

import com.innowhite.red5.util.InnowhiteConstants;

public class MainScopeService {

    private HashMap<String, Scope> scopeMap= new HashMap<String, Scope>();
    
    
    public void lockRoom(String roomId){
	if(roomId != null && scopeMap.get(roomId) != null){
	    scopeMap.get(roomId).setAttribute(InnowhiteConstants.LOCK, "true");
	}
    }
    
    
    public void unLockRoom(String roomId){
	if(roomId != null && scopeMap.get(roomId) != null){
	    scopeMap.get(roomId).setAttribute(InnowhiteConstants.LOCK, "false");
	}
    }
    
    
}
