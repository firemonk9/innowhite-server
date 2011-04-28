package com.vireka.service;

import org.red5.server.api.so.ISharedObject;

public class SaveCurrentWBService {

	
	/*This function will copy all the data from the supplied room-pageNumber and save in a 
	 * new file. The new file  is then stored in the file system 
	 * and info is entered in the data base in the records on the user.
	 * 
	 * */
	public static void saveCurrentWBPage(String currentRoomName, int pageNumber, String user){
		
		
	}
	
	/*
	 * Read the file location from the database.
	 * Read the data from the file.
	 * change the page number to current page number
	 * change the timestamp to 0
	 * change the seq starting with the seqNumber and keep incrementing.
	 * Add all the objects to ISharedObject
	 * Add all the objects to the currentRoom file.
	 * 
	 * */
	public static void getSavedWBPage(int fileId,int currentPageNumber,ISharedObject objectDrawingSO,int seqNumber,String currentRoomName){
		
		
	}
	
	
}
