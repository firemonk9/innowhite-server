/**
 * 
 */
package com.innowhite.whiteboard.util;

/**
 * @authmaor prashanth
 *
 */
public class InnowhiteConstants {
	
	public static final String SECRETKEY = "secret_key";
	public static final String WB_SESSION_ID = "session_id";
	
	
	// below two constants are application names in Red5.
	public static final String VIDEO_APP = "VideoApp";
	public static final String DATA_APP = "DataApp";
	
	public static final String DATEPICKER = "datepicker";
	public static final String SECRETKEYVALUE = "cb5869d542f60ab3dd0eeecbe8ac8e18abe44353875e4db636a40153021e07be3c56209091ee08620cb7b0c403d079f1ef2094ff658f99da54a06c38d380b30d";
	public static final String USERID = "web_session[user_id]";
	public static final String ORGNAME = "web_session[organization_name]";
	public static final String COURSES = "web_session[tag_list]";
	public static final String TOKEN = "1234567890INNOWHITE";
	public static String CREATESESSIONURL = "http://"+TOKEN+".innowhite.com/web_sessions";
	public static String CLOSESESSIONURL = "http://innowhite.com/web_sessions";	

	public static final String JOIN_ROOM_URL = CREATESESSIONURL+"/join_room";
	public static final String LEAVE_ROOM_URL = CLOSESESSIONURL+"/leave_room";


	public static final String JOIN_ROOM_KEY = "JOIN_ROOM_URL";
	public static final String LEAVE_ROOM_KEY = "LEAVE_ROOM_URL";
	
	
	public static final String PROCESSING = "PROCESSING";
	public static final String SUCCESS = "SUCCESS";
	public static final String AUTH_FAILED = "AUTH_FAILED";

	
	public static final String PARTNER_ORG = "orgName";
	public static final String INSTITUTE_NAME = "institutionName";
	
	public static final String USER = "user";
	public static final String CHECKSUM = "checksum";
	public static final String ROOMLEADER = "roomLeader";
	public static final String ROOML_ID = "roomId";
	
	 public static final String GROUP_EADER = "groupLeader";
	 public static final String CLIENT_NAME = "clientname";	
	 public static final String JOIN_ROOM = "joinroom";
	 
	 
	 public static final String WHITEBOARD_SERVER_DATA = "WHITEBOARD";
	 public static final String WHITEBOARD_SERVER = "wbSer";
	 public static final String WHITEBOARD_SERVER_PORT = "wbSerPort";
	 
	 public static final String PREVIOUS_SESSION = "previousSession";
	public static final String ORG_NAME = "orgName";
	public static final String PARENT_ORG = "parentOrg";
	
	public static final String STREAM_TYPE = "streamType";
	public static final String ROOM_ID = "roomName";
	
	//User uploaded files(images, ppt, etc)
	public static final String USER_UPLOADED_FILES = "USER_UPLOADED_FILES_PATH";
	
	//lesson plan(videos etc)
	public static final String VIDEOS_PATH = "VIDEOS_PATH";
	
	//
	public static final String MEDIA_PATH = "MEDIA_PATH";
	
	
	public static final String DATA_PATH = "DATA_PATH";
	
	public static final String SHARED_PATH = "SHARED_PATH";
	public static final String DOCUMENT_SHARE_LOCATION = "DOCUMENT_SHARE_LOCATION";
	public static final String PHONE_NUM = "PHONE_NUM";
	public static final String MEETING_NUM = "MEETING_NUM";
	public static final String TEMP_LOCATION = "TEMP_LOCATION";
	public static final String REFRESH_CACHE = "REFRESH_CACHE";
	
	public static String CONTEXT_PATH = null;
	 
	public static String FILE_SEPRATOR = null;

	public static String IMAGE_TYPE = "IMAGE";
	public static String VIDEO_TYPE = "VIDEO";
	public static String AUDIO_TYPE = "AUDIO";
	public static String DOCUMENT_TYPE = "DOCUMENT";
	public static String EUREKA_TYPE = "EUREKA";
	
	
}
