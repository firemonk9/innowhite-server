package com.innowhite.red5.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IClientBroadcastStream;
import org.red5.server.api.stream.IServerStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.stream.StreamService;
import org.slf4j.Logger;

//import com.innowhite.red5.stream.messaging.MessagingService;
import com.innowhite.red5.stream.messaging.VideoStreamNameListener;
import com.innowhite.red5.stream.security.PublishSecurityImpl;
import com.innowhite.red5.stream.util.InnowhiteConstants;

public class Application extends MultiThreadedApplicationAdapter implements IApplication {

	private IScope appScope;

	private IServerStream serverStream;

	// private MessagingService messagingService;
	private String enableSecurity;
	private String recordPath;
	private static final String INNOWHITE_STREAM_STATUS_SO = "deskSO";

	private HashMap<String, ISharedObject> screenShareSharedObjectMap = new HashMap<String, ISharedObject>();
	public static HashMap<String, IClientBroadcastStream> screenShareStremIdMap = new HashMap<String, IClientBroadcastStream>();

	// public void createDeskshareClient(String name){
	// log.debug("Application: entered createDeskshareClient with name "+name);
	// }
	//
	// public void createScreenVideoBroadcastStream(String name){
	// log.debug("Application: entered createScreenVideoBroadcastStream with name "+name);
	// }
	//
	// public void createBroadcastStream(String name, IScope roomScope){
	// log.debug("Application: entered createBroadcastStream with name "+name);
	// }

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}

	public String getEnableSecurity() {
		return enableSecurity;
	}

	public void setEnableSecurity(String enableSecurity) {
		this.enableSecurity = enableSecurity;
	}

	// public void setMessagingService(MessagingService messagingService) {
	// this.messagingService = messagingService;
	// }

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, InnowhiteConstants.APP_NAME);

	// ISharedObject screenShareSo = null;

	/*
	 * when ever a new room is created, this is called. This function creates a
	 * new shared object for the Room ID If not there already.
	 */
	@Override
	public boolean roomConnect(IConnection connection, Object[] params) {
		log.debug("${APP}:roomConnect " + connection.getScope().getName() + " scope  " + connection.getScope());
		System.out.println("${APP}:roomConnect " + connection.getScope().getName() + " scope  " + connection.getScope());

		connection.getScope().getAttributes();
		// ISharedObject so = getSharedObject(connection.getScope(),
		// INNOWHITE_STREAM_STATUS_SO);

		// String streamType =
		// VideoStreamNameListener.videoStreamIds.get(scope.getName());
		// log.debug(" the stream Type creating is : "+streamType);
		// if (streamType != null && streamType.equals("DESKTOP")) {

		log.debug("screen share stream created so create the shared object. ");
		System.out.println("screen share stream created so create the shared object. ");

		createSharedObject(connection.getScope(), INNOWHITE_STREAM_STATUS_SO, false);
		ISharedObject screenShareSo = getSharedObject(connection.getScope(), INNOWHITE_STREAM_STATUS_SO);
		screenShareSharedObjectMap.put(connection.getScope().getName(), screenShareSo);

		// }
		// String voiceBridge = getBbbSession().getVoiceBridge();

		// UserCacheService.addRoomIdUserSharedObj(connection.getScope().getName(),
		// so);
		// clientManager.addSharedObject(connection.getScope().getName(),
		// voiceBridge, so);
		// conferenceService.createConference(voiceBridge);
		return true;
	}

	@Override
	public void roomStop(IScope scope) {
		log.debug("${APP}:roomStop ${scope.name} removing the shared object:: " + scope.getName());
		System.out.println("${APP}:roomStop ${scope.name} removing the shared object:: " + scope.getName());

		// conferenceService.destroyConference(scope.getName());
		// clientManager.removeSharedObject(scope.getName());
		if (hasSharedObject(scope, INNOWHITE_STREAM_STATUS_SO)) {
			
			screenShareSharedObjectMap.remove(scope.getName());
			clearSharedObjects(scope, INNOWHITE_STREAM_STATUS_SO);

		}

	}

	/** {@inheritDoc} */
	@Override
	public boolean appStart(IScope app) {
		super.appStart(app);
		log.info("VideoApp appStart");
		System.out.println("VideoApp appStart");
		// System.out.println("VideoApp appStart");

		// registerStreamPublishSecurity(new SecurityImpl());
		registerStreamPublishSecurity(new PublishSecurityImpl(enableSecurity));
		// /registerStreamPlaybackSecurity(handler)(new SecurityImpl());

		// re

		appScope = app;
		return true;
	}

	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.debug("Entered streamBroadcastStart...");
		System.out.println("Entered streamBroadcastStart...");

		if (stream instanceof ClientBroadcastStream) {
			
//			createSharedObject(stream.getScope(), INNOWHITE_STREAM_STATUS_SO, false);
			ISharedObject so = getSharedObject(stream.getScope(), INNOWHITE_STREAM_STATUS_SO);
			screenShareSharedObjectMap.put(stream.getScope().getName(), so);
			
			ClientBroadcastStream obj = (ClientBroadcastStream) stream;
			log.debug("streamBroadcastStart: stream id:: " + ((ClientBroadcastStream) stream).getStreamId() + ", is recording:: " + obj.isRecording());
			System.out.println("streamBroadcastStart: stream id:: " + ((ClientBroadcastStream) stream).getStreamId() + ", is recording:: " + obj.isRecording());
			String publishedName = stream.getPublishedName();

			// invokeStopScreenShare();
			// if (obj.isRecording() == true) {
			// messagingService.sendStreamMessage("RECORDSTART#" +
			// stream.getPublishedName() + "#" + recordPath +
			// stream.getPublishedName() + ".flv");
			screenShareStremIdMap.put(publishedName, obj);
			log.debug("streamBroadcastStart: the hashmap:: " + screenShareSharedObjectMap + ", stream name:: " + publishedName);
			System.out.println("streamBroadcastStart: the hashmap:: " + screenShareSharedObjectMap + ", stream name:: " + publishedName);
			// }

			// only screen share should be recorded at this point. will/ may
			// change in future.
			// send a call to client informing that the recording has started.
//			if (obj.isRecording() == true) {
				ISharedObject screenShareSo = screenShareSharedObjectMap.get(stream.getScope().getName());
				log.debug("streamBroadcastStart: screenShareSO: " + screenShareSo);
				System.out.println("streamBroadcastStart: screenShareSO: " + screenShareSo);
				if (screenShareSo != null) {
					// screenShareSo.sendMessage("screenShareStarted", new
					// ArrayList<Object>());
					ArrayList<Object> msg = new ArrayList<Object>();
					msg.add(500);
					msg.add(500);
					log.debug("streamBroadcastStart : before sending appletStarted msg..");
					System.out.println("streamBroadcastStart : before sending appletStarted msg..");
					screenShareSo.sendMessage("appletStarted", msg);
//				}
			}
		}

		log.debug("streamPublishStart:: " + stream.getPublishedName() + " time " + new Date().getTime() + "  get parent  " + stream.getScope().getParent() + " scope  " + stream.getScope());
		System.out.println("streamPublishStart:: " + stream.getPublishedName() + " time " + new Date().getTime() + "  get parent  " + stream.getScope().getParent() + " scope  " + stream.getScope());

		// log.debug("streamPublishStart:: "+stream.getPublishedName());
	}

	@Override
	public void streamBroadcastClose(IBroadcastStream stream) {
		log.debug("streamBroadcastClose:::  " + stream.getPublishedName() + " time " + new Date().getTime() + "  get parent  " + stream.getScope().getParent() + " scope  " + stream.getScope());
		System.out.println("streamBroadcastClose:::  " + stream.getPublishedName() + " time " + new Date().getTime() + "  get parent  " + stream.getScope().getParent() + " scope  "
				+ stream.getScope());

		if (stream instanceof ClientBroadcastStream) {
			ClientBroadcastStream obj = (ClientBroadcastStream) stream;

			// ISharedObject screenShareSo =
			// getSharedObject(stream.getScope().getParent(),
			// INNOWHITE_STREAM_STATUS_SO);

			log.debug("obj recording :: " + obj.isRecording());
			System.out.println("obj recording :: " + obj.isRecording());
//			if (obj.isRecording() == true) {
				ISharedObject screenShareSo = screenShareSharedObjectMap.get(stream.getScope().getName());
				log.debug("screenShareSo  " + screenShareSo);
				System.out.println("screenShareSo  " + screenShareSo);
				if (screenShareSo != null) {
					screenShareSo.sendMessage("screenShareStopped", new ArrayList<Object>());
//				}
			}
			// if (obj.isRecording() == true) {

			// stream.getScope().getS
			// messagingService.sendStreamMessage("RECORDSTOP#" +
			// stream.getPublishedName() + "#FILENAME");
			VideoStreamNameListener.videoStreamIds.remove(stream.getPublishedName());
			screenShareStremIdMap.remove(stream.getPublishedName());
			// messagingService.sendStreamMessage("RECORDSTART#"+stream.getPublishedName()+"#"+recordPath+stream.getPublishedName()+".flv");
			// }
		}
	}

	// private void invokeStopScreenShare() {
	//
	// new java.util.Timer().schedule(new java.util.TimerTask() {
	// @Override
	// public void run() {
	// //screenShareSo.sendMessage("screenShareStopped", new
	// ArrayList<Object>());
	// }
	// }, 30000);
	//
	// }

	// public void stopStream(String roomId) {
	//
	//
	// log.debug(" stop the stream is invoked from client for stream id:: "+roomId);
	// //StreamServiceHelper.closeStream(Red5.getConnectionLocal(),Integer.parseInt(roomId));
	// //StremSe(Red5.getConnectionLocal(),roomId);
	// //String conference = getConfId(roomId);
	// //log.debug("Mute all users in room[$conference]");
	// // freeSwitchGateway.mute(conference, mute);
	// }

	/** {@inheritDoc} */
	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("VideoApp appConnect" + params);
		System.out.println("VideoApp appConnect" + params);
		for (Object val : params) {
			log.info(val.toString());
		}

		log.info("VideoApp connection " + conn);

		return super.appConnect(conn, params);
	}

	/** {@inheritDoc} */
	@Override
	public void appDisconnect(IConnection conn) {
		log.info("VideoApp appDisconnect");
		if (appScope == conn.getScope() && serverStream != null) {
			serverStream.close();
		}
		super.appDisconnect(conn);
	}

}
