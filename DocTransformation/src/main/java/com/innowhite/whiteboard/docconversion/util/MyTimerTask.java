/**
 * 
 */
package com.innowhite.whiteboard.docconversion.util;

import java.util.TimerTask;

import com.innowhite.whiteboard.docconversion.persistence.dao.MessagePersistenceDAO;

/**
 * @author dhiraj :
 * 	This is a temporary solution. Need to find a better solution to fix time out issue.
 * 
 */
public class MyTimerTask extends TimerTask {

    

    
    @Override
    public void run() {
	
	try{
	    MessagePersistenceDAO.selectTestSQL();
	}catch(Exception e){e.printStackTrace();}
	
    }

}
