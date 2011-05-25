package com.innowhite.red5.stream;

import java.util.Date;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IServerStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;

import com.innowhite.red5.stream.messaging.MessagingService;
import com.innowhite.red5.stream.messaging.VideoStreamNameListener;
import com.innowhite.red5.stream.security.PublishSecurityImpl;

public class Application extends MultiThreadedApplicationAdapter implements IApplication {

    private IScope appScope;

    private IServerStream serverStream;

    private MessagingService messagingService;
    private String enableSecurity;
    private String recordPath;
    private static final String INNOWHITE_STREAM_STATUS_SO = "ScreenShareSO";
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

    public void setMessagingService(MessagingService messagingService) {
	this.messagingService = messagingService;
    }

    private static Logger log = Red5LoggerFactory.getLogger(Application.class, "oflaDemo");

    
    /*when ever a new room is created, this is called.*/
    @Override
    public boolean roomConnect(IConnection connection, Object[] params) {
	log.debug("${APP}:roomConnect "+connection.getScope().getName());
	
	ISharedObject so = getSharedObject(connection.getScope(), INNOWHITE_STREAM_STATUS_SO);
	ISharedObject screenShareSo = getSharedObject(connection.getScope(), INNOWHITE_STREAM_STATUS_SO);

	// String voiceBridge = getBbbSession().getVoiceBridge();
	

	//UserCacheService.addRoomIdUserSharedObj(connection.getScope().getName(), so);
	// clientManager.addSharedObject(connection.getScope().getName(),
	// voiceBridge, so);
	// conferenceService.createConference(voiceBridge);
	return true;
    }

    @Override
    public void roomStop(IScope scope) {
	log.debug("${APP}:roomStop ${scope.name} removing the shared object");

	// conferenceService.destroyConference(scope.getName());
	// clientManager.removeSharedObject(scope.getName());
	if (!hasSharedObject(scope, INNOWHITE_STREAM_STATUS_SO)) {
	    clearSharedObjects(scope, INNOWHITE_STREAM_STATUS_SO);
	}
	
    }

    
    /** {@inheritDoc} */
    @Override
    public boolean appStart(IScope app) {
	super.appStart(app);
	log.info("oflaDemo appStart");
	// System.out.println("oflaDemo appStart");

	// registerStreamPublishSecurity(new SecurityImpl());
	registerStreamPublishSecurity(new PublishSecurityImpl(enableSecurity));
	// /registerStreamPlaybackSecurity(handler)(new SecurityImpl());

	// re

	appScope = app;
	return true;
    }

    @Override
    public void streamBroadcastStart(IBroadcastStream stream) {

	if (stream instanceof ClientBroadcastStream) {
	    ClientBroadcastStream obj = (ClientBroadcastStream) stream;
	    if (obj.isRecording() == true)
		messagingService.sendStreamMessage("RECORDSTART#" + stream.getPublishedName() + "#" + recordPath + stream.getPublishedName() + ".flv");
	}
	log.debug("streamPublishStart:: " + stream.getPublishedName() + " time " + new Date().getTime());

	// log.debug("streamPublishStart:: "+stream.getPublishedName());
    }

    @Override
    public void streamBroadcastClose(IBroadcastStream stream) {
	log.debug("streamBroadcastClose:::  " + stream.getPublishedName() + " time " + new Date().getTime());

	if (stream instanceof ClientBroadcastStream) {
	    ClientBroadcastStream obj = (ClientBroadcastStream) stream;
	    if (obj.isRecording() == true) {
		
		messagingService.sendStreamMessage("RECORDSTOP#" + stream.getPublishedName() + "#FILENAME");
		VideoStreamNameListener.videoStreamIds.remove(stream.getPublishedName());
		// messagingService.sendStreamMessage("RECORDSTART#"+stream.getPublishedName()+"#"+recordPath+stream.getPublishedName()+".flv");
	    }
	}

	// stream.getMetaData();
    }

    /** {@inheritDoc} */
    @Override
    public boolean appConnect(IConnection conn, Object[] params) {
	log.info("oflaDemo appConnect");

	
	return super.appConnect(conn, params);
    }

    /** {@inheritDoc} */
    @Override
    public void appDisconnect(IConnection conn) {
	log.info("oflaDemo appDisconnect");
	if (appScope == conn.getScope() && serverStream != null) {
	    serverStream.close();
	}
	super.appDisconnect(conn);
    }

}
