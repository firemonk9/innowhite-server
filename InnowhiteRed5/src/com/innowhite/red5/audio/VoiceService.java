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
import org.slf4j.Logger;

import com.innowhite.red5.util.InnowhiteConstants;

/*  This class gets invoked from client(Flex Applicaiton).  
 * 
 * */
public class VoiceService extends ApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(VoiceService.class, InnowhiteConstants.APP_NAME);

    private FreeSwitchGateway freeSwitchGateway;



    public void recordStart(String roomId) {
	String conference = getConfId(roomId);
	freeSwitchGateway.startRecord(conference, 0);
    }

    public void recordStop(String roomId) {
	String conference = getConfId(roomId);
	freeSwitchGateway.stopRecord(conference, 0);
    }

    public void muteAllUsers(String roomId, boolean mute) {
	String conference = getConfId(roomId);
	log.debug("Mute all users in room[$conference]");
	// freeSwitchGateway.mute(conference, mute);
    }

    // public boolean isRoomMuted(String roomId){
    // String conference =getConfId(roomId);
    // return freeSwitchGateway.isRoomMuted(conference);
    // }

    public void muteUnmuteUser(String roomId, Integer userid, Boolean mute) {
	String conference = getConfId(roomId);
	log.debug("MuteUnmute request for user [$userid] in room[$conference]");
	// System.err.println("------ Invoked muteUnmuteUser ----------  ");
	freeSwitchGateway.mute(conference, userid, mute);
    }

    // public void lockMuteUser(String roomId,Integer userid, Boolean lock) {
    // String conference =getConfId(roomId);
    // log.debug("Lock request for user [$userid] in room[$conference]");
    // freeSwitchGateway.lock(conference, userid, lock);
    // }

    public void kickUSer(String roomId, Integer userid) {
	String conference = getConfId(roomId);
	log.debug("KickUser $userid from $conference roomId" + roomId + " userid: " + userid + "  conference " + conference);
	// System.err.println("------ Invoked kickUSer ----------  ");
	freeSwitchGateway.eject(conference, userid);
    }

    public void setFreeSwitchGateway(FreeSwitchGateway s) {
	log.debug("Setting voice server");
	freeSwitchGateway = s;
	log.debug("Setting voice server DONE");
    }

    private String getConfId(String roomId) {
	if (roomId != null)
	    return roomId.substring(2);
	else
	    return null;

    }
}
