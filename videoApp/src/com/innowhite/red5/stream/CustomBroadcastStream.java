package com.innowhite.red5.stream;

import java.io.IOException;
import java.util.Collection;

import org.red5.server.api.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IStreamCodecInfo;
import org.red5.server.api.stream.IStreamListener;
import org.red5.server.api.stream.ResourceExistException;
import org.red5.server.api.stream.ResourceNotFoundException;
import org.red5.server.messaging.IProvider;
import org.red5.server.net.rtmp.event.Notify;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.stream.IConsumerService;

public class CustomBroadcastStream implements IBroadcastStream {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		System.err.println(" Innowhite  close");
	}

	@Override
	public IStreamCodecInfo getCodecInfo() {
		System.err.println(" Innowhite  getCodecInfo");
		return null;
	}

	@Override
	public long getCreationTime() {
		System.err.println(" Innowhite  getCreationTime");
		return 0;
	}

	@Override
	public String getName() {
		System.err.println(" Innowhite  getName");
		return null;
	}

	@Override
	public IScope getScope() {
		System.err.println(" Innowhite  getScope");
		return null;
	}

	@Override
	public void start() {
		System.err.println(" Innowhite  start");
		
	}

	@Override
	public void stop() {
		System.err.println(" Innowhite  stop");
		
	}

	@Override
	public void addStreamListener(IStreamListener arg0) {
		System.err.println(" Innowhite  addStreamListener");
		
	}

	@Override
	public Notify getMetaData() {
		System.err.println(" Innowhite  getMetaData");
		return null;
	}

	@Override
	public IProvider getProvider() {
		System.err.println(" Innowhite  getProvider");
		return null;
	}

	@Override
	public String getPublishedName() {
		System.err.println(" Innowhite  getPublishedName");
		return null;
	}

	@Override
	public String getSaveFilename() {
		System.err.println(" Innowhite  getSaveFilename");
		return null;
	}

	@Override
	public Collection<IStreamListener> getStreamListeners() {
		System.err.println(" Innowhite  getStreamListeners");
		return null;
	}

	@Override
	public void removeStreamListener(IStreamListener arg0) {
		System.err.println(" Innowhite  removeStreamListener");
		
	}

	@Override
	public void saveAs(String arg0, boolean arg1) throws IOException,
			ResourceNotFoundException, ResourceExistException {
		System.err.println(" Innowhite  saveAs");
		
	}

	@Override
	public void setPublishedName(String arg0) {
		System.err.println(" Innowhite  setPublishedName");
		
	}

	
	
	
}
