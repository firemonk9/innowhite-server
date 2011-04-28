package com.innowhite.stream;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IServerStream;
import org.slf4j.Logger;

import com.innowhite.stream.security.PublishSecurityImpl;

public class Application extends MultiThreadedApplicationAdapter {

	private IScope appScope;

	private IServerStream serverStream;
	
	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "oflaDemo");
	
	/** {@inheritDoc} */
    @Override
	public boolean appStart(IScope app) {
	    super.appStart(app);
		log.info("oflaDemo appStart");
		System.out.println("oflaDemo appStart");  
		
		//registerStreamPublishSecurity(new SecurityImpl());
		registerStreamPublishSecurity(new PublishSecurityImpl());
//		/registerStreamPlaybackSecurity(handler)(new SecurityImpl());
		appScope = app;
		return true;
	}

    @Override
    public void streamBroadcastStart(IBroadcastStream stream){
    	log.info("streamPublishStart:: "+stream.getPublishedName());
		System.out.println("streamPublishStart:: "+stream.getPublishedName()); 
    }
    
    @Override
    public void streamBroadcastClose(IBroadcastStream stream){
    	log.info("streamBroadcastClose:::  "+stream.getPublishedName());
		System.out.println("streamBroadcastClose:: "+stream.getPublishedName()); 
    }
    

       
	/** {@inheritDoc} */
    @Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("oflaDemo appConnect");
		
		
		// Trigger calling of "onBWDone", required for some FLV players
		
		// commenting out the bandwidth code as it is replaced by the mina filters
		//measureBandwidth(conn);
//		if (conn instanceof IStreamCapableConnection) {
//			IStreamCapableConnection streamConn = (IStreamCapableConnection) conn;
//			SimpleConnectionBWConfig bwConfig = new SimpleConnectionBWConfig();
//			bwConfig.getChannelBandwidth()[IBandwidthConfigure.OVERALL_CHANNEL] =
//				1024 * 1024;
//			bwConfig.getChannelInitialBurst()[IBandwidthConfigure.OVERALL_CHANNEL] =
//				128 * 1024;
//			streamConn.setBandwidthConfigure(bwConfig);
//		}
		
//		if (appScope == conn.getScope()) {
//			serverStream = StreamUtils.createServerStream(appScope, "live0");
//			SimplePlayItem item = new SimplePlayItem();
//			item.setStart(0);
//			item.setLength(10000);
//			item.setName("on2_flash8_w_audio");
//			serverStream.addItem(item);
//			item = new SimplePlayItem();
//			item.setStart(20000);
//			item.setLength(10000);
//			item.setName("on2_flash8_w_audio");
//			serverStream.addItem(item);
//			serverStream.start();
//			try {
//				serverStream.saveAs("aaa", false);
//				serverStream.saveAs("bbb", false);
//			} catch (Exception e) {}
//		}
		 
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
