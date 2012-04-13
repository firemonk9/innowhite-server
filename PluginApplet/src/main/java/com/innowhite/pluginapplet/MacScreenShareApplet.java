package com.innowhite.pluginapplet;

import java.applet.Applet;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MacScreenShareApplet extends Applet{
	
	
	@SuppressWarnings("unchecked")
	public void UpdatePluginFile(String streamid,String recordStatus, int intone, 
			  int inttwo,String serverUrl, String roomId, String port,int intthree) throws IOException{
		
		final Object localPluginpath = AccessController.doPrivileged(new PrivilegedAction() {
			  public Object run() {
				  String OS = System.getProperty("os.name").toUpperCase();
				  Object temp=null;
				  if(OS.contains("MAC"))
				        temp=System.getProperty("user.home") + "/Library/Internet Plug-Ins";
				  return temp;
				  //return System.getenv("Internet Plug-Ins");
	            }
	        });
		
		final Object localInnoAppPath = AccessController.doPrivileged(new PrivilegedAction() {
			  public Object run() {
				  String OS = System.getProperty("os.name").toUpperCase();
				  Object temp=null;
				  if(OS.contains("MAC"))
					  temp = System.getProperty("user.home") + "/Applications";
				  return temp;
				 // return System.getenv("Applications");
	            }
	        });
		
		Object action = AccessController.doPrivileged(new PrivilegedAction() {
			  public Object run() {
					try{
					  
						boolean isPluginExists = false;
						String destPluginLoc = localPluginpath.toString() + "//innowhite.plugin";    //Location:/Library/Internet Plug-Ins
						File destPluginFile = new File(destPluginLoc);
						if(!destPluginFile.exists()){
							destPluginFile.createNewFile();
						}
		
						URL	u = new URL("https://innowhite.com/Innowhite.plugin");
					    URLConnection uc = u.openConnection();
					    int remoteContentLength = uc.getContentLength();
					    if(remoteContentLength == -1) {
					      throw new IOException("This is not a binary file.");
					    }
					    if(destPluginFile.length()==remoteContentLength){
					    	isPluginExists=true;
						} 
						 
					    if(!isPluginExists){  // If the local innowhite.plugin file not same as URL file save the content from URL to local.
							    InputStream raw = uc.getInputStream();
							    InputStream in = new BufferedInputStream(raw);
							    byte[] data = new byte[remoteContentLength];
							    int bytesRead = 0;
							    int offset = 0;
							    while (offset < remoteContentLength) {
							      bytesRead = in.read(data, offset, data.length - offset);
							      if (bytesRead == -1)
							        break;
							      offset += bytesRead;
							    }
							    in.close();
							    if(offset != remoteContentLength) {
							      throw new IOException("Only read " + offset + " bytes; Expected " + remoteContentLength + " bytes");
							    }
					
							    FileOutputStream out = new FileOutputStream(destPluginLoc);
							    out.write(data);
							    out.flush();
							    out.close();
						}
					
				   }catch(Exception e){
					   	e.printStackTrace();
					  	return "FAIL";
				   }
			   return "SUCCESS";
	           }
	       }); 
		
		Object actionApp = AccessController.doPrivileged(new PrivilegedAction() {
			  public Object run() {
					try{
					  
						boolean isInnoAppExists = false;
						String destInnoAppLoc = localInnoAppPath.toString() + "//Innowhite.app";  //Location  : /Applications/
						File destInnoAppFile = new File(destInnoAppLoc);
						if(!destInnoAppFile.exists()){
							destInnoAppFile.createNewFile();
						}
		
						URL	u = new URL("https://innowhite.com/Innowhite.app");
					    URLConnection uc = u.openConnection();
					    int remoteContentLength = uc.getContentLength();
					    if(remoteContentLength == -1) {
					      throw new IOException("This is not a binary file.");
					    }
					    if(destInnoAppFile.length()==remoteContentLength){
					    	isInnoAppExists=true;
						} 
						 
					    if(!isInnoAppExists){   // If the local innowhite.app file not same as URL file save the content from URL to local.
							    InputStream raw = uc.getInputStream();
							    InputStream in = new BufferedInputStream(raw);
							    byte[] data = new byte[remoteContentLength];
							    int bytesRead = 0;
							    int offset = 0;
							    while (offset < remoteContentLength) {
							      bytesRead = in.read(data, offset, data.length - offset);
							      if (bytesRead == -1)
							        break;
							      offset += bytesRead;
							    }
							    in.close();
							    if(offset != remoteContentLength) {
							      throw new IOException("Only read " + offset + " bytes; Expected " + remoteContentLength + " bytes");
							    }
					
							    FileOutputStream out = new FileOutputStream(destInnoAppLoc);
							    out.write(data);
							    out.flush();
							    out.close();
						}
					
				   }catch(Exception e){
					   	e.printStackTrace();
					  	return "FAIL";
				   }
			   return "SUCCESS";
	           }
	       }); 
		
		
		 
		startScreeShare(streamid,recordStatus,intone,inttwo,serverUrl,roomId,port,intthree);
		
	}//end for updatePluginFile  method
	
	
	@SuppressWarnings("unchecked")
	public void startScreeShare(String streamid,String recordStatus, int intone, 
			  int inttwo,String serverUrl, String roomId, String port,int intthree){
						
					  Object pathEnv = AccessController.doPrivileged(new PrivilegedAction() {
						  public Object run() {
							  String OS = System.getProperty("os.name").toUpperCase();
							  Object temp=null;
							  if(OS.contains("MAC"))
							        temp=System.getProperty("user.home") + "/Library/Internet Plug-Ins";
							  return temp;
				            }
				       });
					  
				      String finalPath = pathEnv.toString() + "\\innowhite.plugin";
											  
				      final String command = finalPath+" "+streamid+" "+recordStatus+" "+intone+" "+inttwo+" "+serverUrl+" "+roomId+" "+port+" "+intthree;
			
				      Object action = AccessController.doPrivileged(new PrivilegedAction() {
						  public Object run() {
							  try{
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
