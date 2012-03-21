/** 
 * ===License Header===
 *
 * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
 *
 * Copyright (c) 2010 BigBlueButton Inc. and by respective authors (see below).
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2.1 of the License, or (at your option) any later
 * version.
 *
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
 * 
 * ===License Header===
 */

package com.innowhite.red5.audio;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.adapter.IApplication;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.so.ISharedObject;
import org.slf4j.Logger;

import com.innowhite.red5.UserCacheService;
import com.innowhite.red5.util.InnowhiteConstants;

public class VoiceHandler extends ApplicationAdapter implements IApplication {
	private static Logger log = Red5LoggerFactory.getLogger(VoiceHandler.class, InnowhiteConstants.APP_NAME);

	// private static final String VOICE = "VOICE";
	// private static final String VOICE_SO = "meetMeUsersSO";
	private static final String INNOWHITE_VOICE_SO = "AudioConfSO";
	private static final String INNOWHITE_SCREEN_SHARE_SO = "ScreenShareSO";

	// private static final String APP = "VOICE";

	// private ClientNotifier clientManager;
	// private ConferenceService conferenceService;

	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.debug("${APP}:appConnect");
		return true;
	}

	@Override
	public void appDisconnect(IConnection conn) {
		log.debug("${APP}:appDisconnect");
	}

	@Override
	public boolean appJoin(IClient client, IScope scope) {
		log.debug("${APP}:appJoin ${scope.name}");
		return true;
	}

	@Override
	public void appLeave(IClient client, IScope scope) {
		log.debug("${APP}:appLeave ${scope.name}");

	}

	@Override
	public boolean appStart(IScope scope) {
		log.debug("${APP}:appStart ${scope.name}");
		// conferenceService.startup();
		return true;
	}

	@Override
	public void appStop(IScope scope) {
		log.debug("${APP}:appStop ${scope.name}");
		// conferenceService.shutdown();
	}

	@Override
	public boolean roomConnect(IConnection connection, Object[] params) {
		log.debug("${APP}:roomConnect");
		log.debug("In live mode");
		ISharedObject so = getSharedObject(connection.getScope(), INNOWHITE_VOICE_SO);
		ISharedObject screenShareSo = getSharedObject(connection.getScope(), INNOWHITE_SCREEN_SHARE_SO);

		// String voiceBridge = getBbbSession().getVoiceBridge();
		log.debug("Setting up voiceBridge $voiceBridge");

		UserCacheService.addRoomIdUserSharedObj(connection.getScope().getName(), so);
		// clientManager.addSharedObject(connection.getScope().getName(),
		// voiceBridge, so);
		// conferenceService.createConference(voiceBridge);
		return true;
	}

	@Override
	public void roomDisconnect(IConnection connection) {
		log.debug("${APP}:roomDisconnect");
	}

	@Override
	public boolean roomJoin(IClient client, IScope scope) {
		log.debug("${APP}:roomJoin ${scope.name} - ${scope.parent.name}");
		return true;
	}

	@Override
	public void roomLeave(IClient client, IScope scope) {
		log.debug("${APP}:roomLeave ${scope.name}");
	}

	@Override
	public boolean roomStart(IScope scope) {
		log.debug("${APP} - roomStart ${scope.name}");

		if (!hasSharedObject(scope, INNOWHITE_VOICE_SO)) {
			if (createSharedObject(scope, INNOWHITE_VOICE_SO, false)) {
				return true;
			}
		}

		log.error("Failed to start room ${scope.name}");
		return false;
	}

	@Override
	public void roomStop(IScope scope) {
		log.debug("${APP}:roomStop ${scope.name}");

		// conferenceService.destroyConference(scope.getName());
		// clientManager.removeSharedObject(scope.getName());
		if (!hasSharedObject(scope, INNOWHITE_VOICE_SO)) {
			clearSharedObjects(scope, INNOWHITE_VOICE_SO);
		}
		UserCacheService.removeRoomIdUserSharedObj(scope.getName());
	}

	// public void setClientNotifier(ClientNotifier c) {
	// log.debug("Setting voice application");
	// // clientManager = c;
	// }
	//
	// public void setConferenceService(ConferenceService s) {
	// log.debug("Setting voice server");
	// // conferenceService = s;
	// log.debug("Setting voice server DONE");
	// }

	// private BigBlueButtonSession getBbbSession() {
	// return (BigBlueButtonSession)
	// Red5.getConnectionLocal().getAttribute(Constants.SESSION);
	// }
}
