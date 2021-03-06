package com.innowhite.red5;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.statistics.IScopeStatistics;
import org.red5.server.api.stream.IServerStream;
import org.slf4j.Logger;

import com.innowhite.red5.audio.FreeswitchServiceProvider;
import com.innowhite.red5.messaging.MessagingService;
import com.innowhite.red5.util.Constants;
import com.innowhite.red5.util.InnowhiteConstants;
import com.innowhite.red5.util.UserInfo;
import com.innowhite.red5.util.Utility;
import com.innowhite.red5.vo.ChatVO;
import com.innowhite.red5.vo.RoomSharedVO;
import com.innowhite.red5.vo.RoomVO;
import com.innowhite.red5.vo.ShapeEventsVO;
import com.innowhite.red5.vo.UserListVO;
import com.innowhite.red5.vo.VideoDisplayVO;

public class Main extends MultiThreadedApplicationAdapter {
	private IScope appScope;
	long keyIndex = 0;
	private IServerStream serverStream;

	private boolean enableWhiteboardDataRecord = false;

	public void setEnableWhiteboardDataRecord(String enableWhiteboardDataRecord) {
		if (enableWhiteboardDataRecord != null && enableWhiteboardDataRecord.equals("true"))
			this.enableWhiteboardDataRecord = true;

	}

	private HashMap<String, RoomVO> shapeSeqMap = new HashMap<String, RoomVO>();
	private HashMap<String, Integer> chatSeqMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> roomInfoSeqMap = new HashMap<String, Integer>();
	// private HashMap<String, Integer> userSeqMap = new HashMap<String,
	// Integer>();

	private HashMap<String, Integer> videoSeqMap = new HashMap<String, Integer>();
	private HashMap<String, Map> clientNamesMap = new HashMap<String, Map>();

	private HashMap<String, List<VideoDisplayVO>> disconnectUser = new HashMap<String, List<VideoDisplayVO>>();

	private static Logger log = Red5LoggerFactory.getLogger(Main.class, InnowhiteConstants.APP_NAME);

	private String enableSecurity;

	// private static Logger log = Red5LoggerFactory.getLogger(Main.class);

	public String getEnableSecurity() {
		return enableSecurity;
	}

	public void setEnableSecurity(String enableSecurity) {
		this.enableSecurity = enableSecurity;
	}

	FreeswitchServiceProvider freeSwitchServiceProvider;
	MessagingService messagingService;

	public void setMessagingService(MessagingService messagingService) {
		this.messagingService = messagingService;
	}

	public void setFreeSwitchServiceProvider(FreeswitchServiceProvider freeSwitchServiceProvider) {
		this.freeSwitchServiceProvider = freeSwitchServiceProvider;
	}

	/** {@inheritDoc} */
	public boolean appStart(IScope app) {

		// new ExampleClient();
		try {

			// ExampleClient ec = new ExampleClient(this);
			freeSwitchServiceProvider.startup();
			// regi

		} catch (Exception e) {
			e.printStackTrace();
			log.error(" error in start up ", e);
		}

		appScope = app;
		return true;
	}

	/** {@inheritDoc} */
	public boolean appConnect(IConnection conn, Object[] params) {
		// Trigger calling of "onBWDone", required for some FLV players
		log.debug("App connect");
		log.debug("App connect in debuggggggggggggggggg..... ");

		return super.appConnect(conn, params);

	}

	public boolean updateShapeEvents(ShapeEventsVO v) {

		try {
			ISharedObject objectDrawingSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "ObjectDrawingSO");

			IScope scope = Red5.getConnectionLocal().getScope();

			// this.get
			// this.
			RoomVO roomVO = shapeSeqMap.get(Red5.getConnectionLocal().getScope().getName());
			int num = roomVO.getSeqNum();
			v.setSeq(num++);
			objectDrawingSO.setAttribute(v.getObjName(), v);

			// log.debug(" seq  " + v.getSeq() + "  objtype "
			// + v.getObjType() + "  objname  " + v.getObjName()
			// + "  actiontype  " + v.getActionType());
			long timeVal = roomVO.getMediaTimeNum();
			if (roomVO.getMediaDate() != null) {
				timeVal = timeVal + new Date().getTime() - roomVO.getMediaDate().getTime() - 2000;
			}

			v.setObjDate(timeVal);
			roomVO.setSeqNum(num);

			long l = (new Date()).getTime() - (Long) scope.getAttribute("START_TIME");

			if (enableWhiteboardDataRecord == true)
				messagingService.sendWhiteboardData(v, Red5.getConnectionLocal().getScope().getName(), l);

			// log.debug("     " + num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean updateUserList(UserListVO uservo) {

		try {

			log.debug("  updateUserList " + uservo.getUsername() + "    " + "   " + uservo.getMyStatus() + "     " + "   " + uservo.getRemoveMe());

			ISharedObject userListSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "UserListSO");

			Map<String, String> map = clientNamesMap.get(Red5.getConnectionLocal().getScope().getName());
			// we want to set the time if instructor and get the time for normal
			// users for the first time.

			if (map == null || Utility.userFirstTime(uservo.getUsername(), map)) {

				if (uservo.getUserJoinedTime() == 0) {

					uservo.setUserJoinedTime((new Date().getTime()));
					log.debug(" user joined time is " + uservo.getUserJoinedTime() + " username " + uservo.getUsername());

					// check if the user called in by phone and got his WB
					// disconnected. Need to set
					// join conf to true.
					if (UserCacheService.userExistsInConf(Red5.getConnectionLocal().getScope().getName(), uservo.getUsername()) == true) {
						log.debug(" the user already exists in the audio conf : " + uservo.getUsername());
						uservo.setVoiceConfJoined(1);
					}
				}

				RoomVO roomVO = shapeSeqMap.get(Red5.getConnectionLocal().getScope().getName());

				int num = roomVO.getSeqNum();
				uservo.setShapeCount(num);

				// userListSO.setAttribute(uservo.getUsername(), uservo);

			} else {
			}

			int num = videoSeqMap.get(Red5.getConnectionLocal().getScope().getName());
			// v.setSeq(num++);
			uservo.setSeq(num);
			userListSO.setAttribute("" + num++, uservo);

			videoSeqMap.put(Red5.getConnectionLocal().getScope().getName(), num);

			// log.debug(" the size of user list is ########: " + num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean updateChat(ChatVO chatvo) {
		try {
			ISharedObject chatSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "ChatSO");

			int num = chatSeqMap.get(Red5.getConnectionLocal().getScope().getName());
			chatvo.setSeq(num++);
			chatSeqMap.put(Red5.getConnectionLocal().getScope().getName(), num);

			chatSO.setAttribute("" + chatvo.getSeq(), chatvo);
			// SavingData.saveToFile(chatvo,
			// Red5.getConnectionLocal().getScope().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean updateRoom12(RoomSharedVO mroomvo) {
		try {
			System.err.println("testing ...mroomvo" + mroomvo);
			ISharedObject roomInfoSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "MyRoomSO");

			// int num =
			// roomInfoSeqMap.get(Red5.getConnectionLocal().getScope().getName());
			// roomInfoSO.setSeq(num++);
			// roomInfoSeqMap.put(Red5.getConnectionLocal().getScope().getName(),
			// num);

			roomInfoSO.setAttribute("" + 0, mroomvo);

			System.err.println(" userso :: lock : " + mroomvo.isLock());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean updateRoom12(boolean lock, String moderator, String presenter,boolean recording, boolean webinar, boolean muteAudio, boolean autoJoin, boolean privateChat, String viewState ) {

		try {
			System.err.println("testing ...updateRoom12 lock" + lock);
			ISharedObject roomInfoSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "MyRoomSO");
			RoomSharedVO mroomvo = new RoomSharedVO();
			mroomvo.setLock(lock);
			mroomvo.setModerator(moderator);
			mroomvo.setPresenter(presenter);
			mroomvo.setRecording(recording);
			mroomvo.setWebinar(webinar);
			mroomvo.setMuteAudio(muteAudio);
			mroomvo.setAutoJoin(autoJoin);
			mroomvo.setPrivateChat(privateChat);
			mroomvo.setViewState(viewState);
			
			
			roomInfoSO.setAttribute("" + 0, mroomvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	public boolean updateVideo(VideoDisplayVO videovo) {
		ISharedObject videoSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "VideoDisplaySO");

		// if(mediaDate)

		int num = videoSeqMap.get(Red5.getConnectionLocal().getScope().getName());
		videovo.setSeq(num++);
		videoSeqMap.put(Red5.getConnectionLocal().getScope().getName(), num);

		String roomName = Red5.getConnectionLocal().getScope().getName();
		RoomVO roomVO = shapeSeqMap.get(roomName);

		log.debug("  the video vo obj is : " + videovo);

		videoSO.setAttribute("" + videovo.getSeq(), videovo);

		if (roomVO.getMediaDate() == null && (videovo.isAudio() == true || videovo.isVideo() == true)) {
			roomVO.setMediaDate(new Date());
		} else if (roomVO.getMediaDate() != null && (videovo.isAudio() == false && videovo.isVideo() == false)) {

			roomVO.setMediaTimeNum(roomVO.getMediaTimeNum() + (new Date()).getTime() - (roomVO.getMediaDate().getTime()));
			roomVO.setMediaDate(null);
		}

		userProperCloseConnection(videovo, roomName);

		return true;
	}

	private void userProperCloseConnection(VideoDisplayVO videovo, String roomName) {

		String key = roomName + videovo.getUsername();

		if (videovo.getStreamType().equals("DESKTOP")) {
			if (videovo.getShareStatus().equals("STARTED")) {
				// add to the list
				if (disconnectUser.containsKey(key) == true && disconnectUser.get(key) != null) {

					List<VideoDisplayVO> l = disconnectUser.get(key);
					l.add(videovo);

				} else {
					List<VideoDisplayVO> l = new ArrayList<VideoDisplayVO>();
					l.add(videovo);
					disconnectUser.put(key, l);

				}

			} else {
				// remove from the disconnecUser.
				List<VideoDisplayVO> l = disconnectUser.get(key);
				if (l != null) {
					// VideoDisplayVO vid=null;
					for (VideoDisplayVO vid : l) {
						if (vid.getStreamType().equals("DESKTOP")) {
							l.remove(vid);

							if (l.size() == 0)
								disconnectUser.remove(l);

							break;
						}
					}
				}
			}
		}

		if (videovo.getStreamType().equals("VIDEO")) {
			if (videovo.getVideo() == true) {
				// add to the list
				if (disconnectUser.containsKey(key) == true && disconnectUser.get(key) != null) {

					List<VideoDisplayVO> l = disconnectUser.get(key);
					l.add(videovo);

				} else {
					List<VideoDisplayVO> l = new ArrayList<VideoDisplayVO>();
					l.add(videovo);
					disconnectUser.put(key, l);

				}

			} else {

				List<VideoDisplayVO> l = disconnectUser.get(key);
				if (l != null) {
					// VideoDisplayVO vid=null;
					for (VideoDisplayVO vid : l) {
						if (vid.getStreamType().equals("VIDEO")) {
							l.remove(vid);

							if (l.size() == 0)
								disconnectUser.remove(l);

							break;
						}
					}
				}
			}
		}

		System.err.println(" the HashMap of users broadcasting video/screenshare  " + videovo);

	}

	/*
	 * This function sends close to Video and Desktop share for a user when the
	 * user closes the browser.
	 */
	private void properUserLogout(String user, String roomName) {

		String key = roomName + user;
		List<VideoDisplayVO> l = disconnectUser.get(key);
		if (l != null && l.size() > 0) {
			for (VideoDisplayVO vidVO : l) {

				if (vidVO.getStreamType().equals("DESKTOP")) {
					vidVO.setShareStatus("STOPPED");
				} else if (vidVO.getStreamType().equals("VIDEO")) {
					vidVO.setVideo(false);
				}

				ISharedObject videoSO = this.getSharedObject(Red5.getConnectionLocal().getScope(), "VideoDisplaySO");

				log.debug("   Why the hell is this null .... videoSO  .." + videoSO);

				if (videoSO != null) {
					int num = videoSeqMap.get(Red5.getConnectionLocal().getScope().getName());
					// num++;
					vidVO.setSeq(num);
					videoSO.setAttribute("" + num, vidVO);
					num++;
					videoSeqMap.put(Red5.getConnectionLocal().getScope().getName(), num);

					log.debug("  putting .." + num + " as key " + vidVO.getFlvpath() + "  stopped");
				} else {
					disconnectUser.remove(key);
					break;
				}

			}
			disconnectUser.remove(key);
		}
	}

	public boolean roomStart(IScope room) {
		try {
			log.debug("room start ::" + room);

			if (!super.roomStart(room)) {
				// log.error("Unable to start " + room.getName() +
				// " for the application");
				return false;
			} else {
				room.setPath("/opt/InnowhiteData/tmp/");
				room.setPersistent(true);
			}
			// log.debug(" New room connected : roomStart ");
			IScopeStatistics sc = room.getStatistics();
			sc.getCreationTime();

			room.setAttribute("START_TIME", sc.getCreationTime());

			log.debug("room start time ::" + sc.getCreationTime());

			this.createSharedObject(room, "ObjectDrawingSO", false);
			// this.createSharedObject(room, "CurrentCounterSO", false);
			this.createSharedObject(room, "UserListSO", false);
			this.createSharedObject(room, "ChatSO", false);
			this.createSharedObject(room, "VideoDisplaySO", false);
			this.createSharedObject(room, "MyRoomSO", false);

			room.setAttribute("keyValue", new Integer(0));
			RoomVO roomVO = new RoomVO();
			roomVO.setSeqNum(1);
			roomVO.setMediaTimeNum(0);
			shapeSeqMap.put(room.getName(), roomVO);
			chatSeqMap.put(room.getName(), 1);

			videoSeqMap.put(room.getName(), 1);

			log.debug(" setting the seq " + videoSeqMap.size() + " chat soze  " + chatSeqMap.size());

			String msg = room.getName() + "_STARTED_" + sc.getCreationTime();

			messagingService.sendRoomMessage(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean checkRoomExists(String str) {
		return true;

	}

	/**
	 * @Invoked When a request comes to create a new room.
	 * 
	 * @param conn
	 *            , the connection string of the client with red5
	 * @param param
	 *            , an Object array[] passed from client side
	 * 
	 * @return true/false to create the room successfully connect/not connect
	 */
	public boolean roomConnect(IConnection conn, Object[] params) {

		try {
			// String[] arry = (String[])params;
			// log.debug(" roomconnect :: " + params.length);

			if (enableSecurity != null && enableSecurity.equals("true")) {
				if (UserCacheService.roomExists(conn.getScope().getName()) == false)
					return false;
			}

			// RoomVO roomVO = shapeSeqMap.get(conn.getScope().getName());
			try {

				// boolean bool = false;
				for (int i = 0; i < params.length; i++) {

					log.debug(" :::  " + params[i]);

					IClient client = conn.getClient();
					if (params[0] != null) {
						log.debug("  Name :  " + (String) params[0] + "  client id:  " + client.getId());

						Map<String, String> map = clientNamesMap.get(conn.getScope().getName());
						if (map == null) {
							map = new HashMap<String, String>();
							clientNamesMap.put(conn.getScope().getName(), map);
						}
						map.put(client.getId(), (String) params[0]);
						UserInfo.addUser(client.getId(), conn.getSessionId());
						if (clientNamesMap.get(conn.getScope().getName()).size() > 1)

							log.debug(" clientNamesMap  " + clientNamesMap + " size :" + clientNamesMap.size());

					}

				}

				if (!super.roomConnect(conn, params)) {
					// log.error("Unable to connect from " +
					// conn.getScope().getName() +
					// " for the application");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Invoked after successfull room connect.
	 * 
	 * @param scope
	 *            , the scope of the room
	 * @param client
	 *            , the reference to hold the client information
	 * @return true/false if room successfully join/not join
	 */
	public boolean roomJoin(IClient client, IScope scope) {
		// log.info("RoomJoined Called for client: " + client.getId() +
		// "of scope " + scope.getName());

		super.roomJoin(client, scope);
		Constants.bsessionFlag = true;

		log.debug(" roomJoin  ###### " + client.toString() + "   " + scope.getName());
		log.debug("calling db update ::" + scope.getName());
		// WhiteboardAuthenticatorService.startRoom(scope.getName());

		log.debug(" roomJoin  ###### " + client.getAttributeNames());

		// String msg = uName + "_" + scope.getName() + "_JOINED_" +
		// Calendar.getInstance().getTimeInMillis();
		// messagingService.sendUserRoomMessage(msg);

		return true;
	}

	public void setApplicationListeners(Set<IApplication> listeners) {
		int count = 0;
		Iterator<IApplication> iter = listeners.iterator();
		while (iter.hasNext()) {
			super.addListener((IApplication) iter.next());
			count++;
		}
	}

	/*
	 * When ever a user leaves a room, This function is called.
	 */
	public void roomLeave(IClient client, IScope room) {

		try {

			super.roomLeave(client, room);
			log.debug(" room Leave  ###### " + client.toString() + "   " + room.getName());
			clientNamesMap.remove(client.getId());

			Map<String, String> map = clientNamesMap.get(room.getName());
			log.debug(" roomLeave map is:" + map + "  client id:" + client.getId());

			RoomVO roomVO = shapeSeqMap.get(room.getName());

			if (map != null) {
				String uName = map.get(client.getId());
				map.remove(client.getId());
				ISharedObject userListSO = this.getSharedObject(room, "UserListSO");

				// userListSO.setAttribute(uservo.getUsername(), uservo);
				boolean removed = false;
				if (userListSO != null) {
					List<Object> list = new ArrayList<Object>();
					list.add(uName);

					// UserCacheService.removeparticipantIDUser(participant);
					// addparticipantIDUser(participant, innoUniqueId.trim() );

					userListSO.sendMessage("removeUser", list);
					// removed = userListSO.removeAttribute(uName);

				}// SavingData.closeFile(room.getName());
					// clientNamesMap.put(conn.getScope().getName(), map);

				log.debug(" roomLeave  ###### " + room.getName() + "  uname " + uName + "  removed " + removed);

				// to make sure to send requests to all clicts to close the
				// streams.
				properUserLogout(uName, room.getName());

				// When a user is disc , remove all objects sent by that user

				if (userListSO != null) {
					Map<String, Object> users = userListSO.getAttributes();
					log.debug(" The length of users list :: " + map.size() + "  the map " + users);
					for (String s : users.keySet()) {
						log.debug(" the id is :: " + s);
						UserListVO userVO = (UserListVO) users.get(s);
						if (userVO.getUsername().equals(uName)) {
							userListSO.removeAttribute(s);
							log.debug(" removing user with id :" + s + " username: " + uName);
						}
					}
				}

				// UserCacheService.addRoomIdUserSharedObj(room.getName(),this.getSharedObject(room,
				// room.getName()));

				String msg = uName + "_" + room.getName() + "_LEFT_" + Calendar.getInstance().getTimeInMillis();
				messagingService.sendUserRoomMessage(msg);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// log.info("Roomleave Called for : " + client.getId() + " , " +
		// scope.getName());
	}

	/**
	 * Called when room scope is stopped
	 * 
	 * @param scope
	 *            , the scope of the room
	 */
	public void roomStop(IScope scope) {
		try {
			super.roomStop(scope);

			// sending msg to close the room.
			String msg = scope.getName() + "_STOPPED_" + Calendar.getInstance().getTimeInMillis();
			messagingService.sendRoomMessage(msg);
			UserCacheService.removeRoomConfMap(scope.getName());

			// removing all cache.
			SavingData.closeFile(scope.getName());
			shapeSeqMap.remove(scope.getName());
			chatSeqMap.remove(scope.getName());

			videoSeqMap.remove(scope.getName());
			// TimeMaintainerService.removeRoom(scope.getName());
			log.debug(" roomStop  ###### ");
			SavingData.closeFile(scope.getName());
			// WhiteboardAuthenticatorService.stopRoom(scope.getName());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// log.info("RoomStop Called for : " + scope.getName());
	}

	public void updateUserJoinVoiceConf(String userID, String roomID) {

	}

	/**
	 * Called when client disconnect from the application
	 * 
	 * @param conn
	 *            , the connection of client from the server
	 */
	public void roomDisconnect(IConnection conn) {

		super.roomDisconnect(conn);
		Constants.bsessionFlag = false;
		log.debug(" roomDisconnect  ###### ");

	}

	/** {@inheritDoc} */
	public void appDisconnect(IConnection conn) {
		if (appScope == conn.getScope() && serverStream != null) {
			serverStream.close();
		}
		super.appDisconnect(conn);
	}

}