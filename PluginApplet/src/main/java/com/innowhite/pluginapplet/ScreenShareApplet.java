package com.innowhite.pluginapplet;

import java.applet.Applet;
import java.security.AccessController;
import java.security.PrivilegedAction;


public class ScreenShareApplet extends Applet{
    
	    
		 // startScreenShare(stream_id, recordStatus, 75,2,serverUrl,roomId,port,15);
		@SuppressWarnings("unchecked")
		public void startScreenShare(String streamid,String recordStatus, int intone, 
				  int inttwo,String serverUrl, String roomId, String port,int intthree ){
			
			  
			  Object pathEnv = AccessController.doPrivileged(new PrivilegedAction() {
				  public Object run() {
					  return System.getenv("appdata");
		            }
		       });
			  
		      String finalPath = pathEnv.toString() + "\\InnoWhite\\Innowhite.exe";
		      
		      final String command = finalPath+" "+streamid+" "+recordStatus+" "+intone+" "+inttwo+" "+serverUrl+" "+roomId+" "+port+" "+intthree;
		      
		      Object action = AccessController.doPrivileged(new PrivilegedAction() {
				  public Object run() {
					  try{
						 // ProcessExecutor pe = new ProcessExecutore();
						 // pe.executeProcess(command, null, true);
					      Runtime rt = Runtime.getRuntime();
						  Process proc = rt.exec(command);
						   return "SUCCESS";
					    }catch(Exception e){
					    	e.printStackTrace();
					    	return "FAIL";
					    }
		            }
		        });
		      
		      
		  }
		  
}
