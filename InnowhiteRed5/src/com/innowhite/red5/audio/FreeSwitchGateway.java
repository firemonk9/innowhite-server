/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innowhite.red5.audio;

//import org.bigbluebutton.webconference.voice.freeswitch.actions.EjectParticipantCommand;
//import org.bigbluebutton.webconference.voice.freeswitch.actions.MuteParticipantCommand;
//import org.bigbluebutton.webconference.voice.events.ConferenceEventListener;
//import org.bigbluebutton.webconference.voice.freeswitch.FreeswitchHeartbeatMonitor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.manager.ManagerConnection;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.red5.audio.commands.EjectParticipantCommand;
import com.innowhite.red5.audio.commands.MuteParticipantCommand;
import com.innowhite.red5.audio.events.AudioFileStartStopEvent;
import com.innowhite.red5.audio.events.ParticipantJoinedEvent;
import com.innowhite.red5.audio.events.ParticipantLeftEvent;
import com.innowhite.red5.audio.events.ParticipantMutedEvent;
import com.innowhite.red5.audio.events.ParticipantTalkingEvent;
import com.innowhite.red5.util.InnowhiteConstants;

/**
 * 
 * @author leif
 */
public class FreeSwitchGateway extends Observable implements IEslEventListener {
    // private final Logger log = Red5LoggerFactory.getLogger(
    // EslEventListener.class );
    private static Logger log = Red5LoggerFactory.getLogger(FreeSwitchGateway.class, InnowhiteConstants.APP_NAME);

    private ManagerConnection managerConnection;
    // private ConferenceEventListener conferenceEventListener;
    private FreeswitchHeartbeatMonitor heartbeatMonitor;
    static Map<String, Boolean> resultMap = new HashMap<String, Boolean>();

    static {

	resultMap.put("start-talking", true);
	resultMap.put("stop-talking", false);

    }

    public void startup() {

	Client c = managerConnection.getESLClient();
	c.addEventListener(this);
	// c.cancelEventSubscriptions();
	c.setEventSubscriptions("plain", "all");
	c.addEventFilter("Event-Name", "heartbeat");
	c.addEventFilter("Event-Name", "custom");
	c.addEventFilter("Event-Name", "background_job");
	c.addEventFilter("Event-Name", "channel_create");

	try {
	    Thread.sleep(5000);
	} catch (InterruptedException ex) {
	    java.util.logging.Logger.getLogger(FreeswitchHeartbeatMonitor.class.getName()).log(Level.SEVERE, null, ex);
	}

	// Start Heartbeat and exception Event Observer Monitor
	if (heartbeatMonitor == null) { // Only startup once. as startup will be
					// called for reconnect.
	    heartbeatMonitor = new FreeswitchHeartbeatMonitor(managerConnection, this);
	    this.addObserver(heartbeatMonitor);
	    heartbeatMonitor.start();
	}
    }

    public void setManagerConnection(ManagerConnection managerConnection) {
	this.managerConnection = managerConnection;
    }

    // public void eventReceived( EslEvent event )
    // {
    // log.info( "Event received [{}]", event );
    // }

    public void eventReceived(EslEvent event) {
	if (event.getEventName().equals(FreeswitchHeartbeatMonitor.EVENT_HEARTBEAT)) {
	    setChanged();
	    notifyObservers(event);
	    return; // No need to log.debug or process further the Observer will
		    // act on this
	}
	// Ignored, Noop This is all the NON-Conference Events except Heartbeat
	// log.debug("eventReceived [{}]", event);

    }

    public void backgroundJobResultReceived(EslEvent event) {
	log.info("Background job result received [{}]", event);
    }

    private AudioEventListener audioEventListener;

    public AudioEventListener getAudioEventListener() {
	return audioEventListener;
    }

    public void setAudioEventListener(AudioEventListener audioEventListener) {
	this.audioEventListener = audioEventListener;
    }

    // @Override
    public void mute(String confRoom, Integer participant, Boolean mute) {
	MuteParticipantCommand mpc = new MuteParticipantCommand(confRoom, participant, mute, 0);
	String jobId = managerConnection.getESLClient().sendAsyncApiCommand(mpc.getCommand(), mpc.getCommandArgs());
	log.debug("mute called for room [{}] jobid [{}]", confRoom, jobId);
    }

    public void invalidConference(String confRoom, Integer participant, Boolean mute) {
	MuteParticipantCommand mpc = new MuteParticipantCommand(confRoom, participant, mute, 0);
	String jobId = managerConnection.getESLClient().sendAsyncApiCommand(mpc.getCommand(), mpc.getCommandArgs());
	log.debug("mute called for room [{}] jobid [{}]", confRoom, jobId);
    }

    // @Override
    public void eject(String confRoom, Integer participant) {
	EjectParticipantCommand mpc = new EjectParticipantCommand(confRoom, participant, 0);
	String jobId = managerConnection.getESLClient().sendAsyncApiCommand(mpc.getCommand(), mpc.getCommandArgs());
	log.debug("eject/kick called for room [{}]  participant [{}]", confRoom, participant);

    }

    // added innoUserUnique parameter
    public void conferenceEventJoin(String confName, int confSize, EslEvent event) {

	Integer memberId = this.getMemeberIdFromEvent(event);
	StringBuilder sb = new StringBuilder("");

	String innoUserUnique = getInnoUniqueIdFromEvent(event);
	String callerUsername = getInnoUniqueIdFromEvent(event);
	String uniqueId = getFSUniqueIdFromEvent(event);

	Object[] args = new Object[5];
	args[0] = confName;
	args[1] = confSize;
	args[2] = uniqueId;
	args[3] = innoUserUnique;
	args[4] = callerUsername;

	// IConnection con = Red5.getConnectionLocal();
	// printAllItemsinEvent(event);
	String callSource = getFSCalledSource(event);

	// Red5.getConnectionLocal().get
	ParticipantJoinedEvent pj = new ParticipantJoinedEvent("" + memberId, confName, callerUsername, innoUserUnique, true, true, callSource);
	audioEventListener.handleConferenceEvent(pj);

	log.info("Conference join: uniqueId " + uniqueId + "  confName " + confName + " confSize " + confSize + "  innoUserUnique " + innoUserUnique + "  callerUsername  " + callerUsername
		+"callSource: "+callSource+"   event  " + event);

	// log.info ("Conference [{}]({}) JOIN [{}] InnoUser [{}] source [{}]",
	// args);
    }

    private void printAllItemsinEvent(EslEvent event) {

	Map<String, String> m = event.getEventHeaders();
	for (String str : m.keySet()) {
	    log.debug(str + " key ::  value  " + m.get(str));
	}

    }

    public void conferenceEventLeave(String confName, int confSize, EslEvent event) {

	Integer memberId = this.getMemeberIdFromEvent(event);
	String uniqueId = getFSUniqueIdFromEvent(event);
	Object[] args = new Object[3];
	args[0] = confName;
	args[1] = confSize;
	args[2] = uniqueId;

	ParticipantLeftEvent pj = new ParticipantLeftEvent("" + memberId, confName);
	audioEventListener.handleConferenceEvent(pj);

	log.info("Conference Leave: uniqueId " + uniqueId + "  confName " + confName + " confSize " + confSize + " event " + event);
	// log.info ("Conference [{}]({}) LEAVE [{}]", args);
    }

    public void conferenceEventMute(String confName, int confSize, EslEvent event) {

	String uniqueId = getFSUniqueIdFromEvent(event);
	Integer memberId = this.getMemeberIdFromEvent(event);

	ParticipantMutedEvent pj = new ParticipantMutedEvent("" + memberId, confName, true);
	audioEventListener.handleConferenceEvent(pj);

	log.debug("Conference [{}] MUTE [{}]", confName, uniqueId);
    }

    public void conferenceEventUnMute(String confName, int confSize, EslEvent event) {

	String uniqueId = getFSUniqueIdFromEvent(event);
	Integer memberId = this.getMemeberIdFromEvent(event);

	ParticipantMutedEvent pj = new ParticipantMutedEvent("" + memberId, confName, true);
	audioEventListener.handleConferenceEvent(pj);

	log.debug("Conference [{}] UNMUTE [{}]", confName, uniqueId);
    }

    /*
     * Call to Freeswitch
     */
    public void conferenceEventAction(String confName, int confSize, String action, EslEvent event) {

	String uniqueId = getFSUniqueIdFromEvent(event);
	Integer memberId = this.getMemeberIdFromEvent(event);
	StringBuilder sb = new StringBuilder("");
	sb.append("" + memberId);
	sb.append(" action=[");
	sb.append(action);
	sb.append("]");

	// log.debug("Conference leave: "+sb);
	log.debug("Conference Action talk: memberId " + memberId + "  confName " + confName + " confSize " + confSize + " event " + event + "  action " + action);

	if (resultMap.get(action) != null) {
	    ParticipantTalkingEvent pj = new ParticipantTalkingEvent("" + memberId, confName, resultMap.get(action));
	    audioEventListener.handleConferenceEvent(pj);

	    log.debug("Conference Action talk: memberId " + memberId + "  confName " + confName + " confSize " + confSize + " event " + event + "  action " + action);
	}
	// log.info ("Conference [{}] Action [{}]", confName, sb.toString());
    }

    public void conferenceEventTransfer(String confName, int confSize, EslEvent event) {
	// Noop
    }

    public void conferenceEventThreadRun(String confName, int confSize, EslEvent event) {
	// --Conference-Recorded-File-End
	// --Conference-Recorded-File-Begin

	log.debug("Entered conferenceEventThreadRun uniqueId::" + " confName::" + confName + " memberID " + getMemeberIdFromEvent(event));
	Integer memberId = this.getMemeberIdFromEvent(event);
	
	String startFile = getStartFileFromEvent(event);
	String endFile = getEndFileFromEvent(event);
	//String action = event.get
	if (startFile != null || endFile != null) {
	    AudioFileStartStopEvent ae = new AudioFileStartStopEvent("" + memberId, confName, startFile, endFile);
	    audioEventListener.handleConferenceEvent(ae);
	}

    }

    private String getPath(EslEvent event) {
	return (event.getEventHeaders().get("Path"));
    }
    private String getAction(EslEvent event) {
	return (event.getEventHeaders().get("Action"));
    }    
    //
    

    public void conferenceEventPlayFile(String confName, int confSize, EslEvent event) {
	// Noop
    }

    public void exceptionCaught(ExceptionEvent e) {
	// throw new UnsupportedOperationException("Not supported yet.");
    }

    private String getStartFileFromEvent(EslEvent e) {
	if( (getAction(e) != null && getAction(e).equals(("start-recording"))))
	{
	   return  getPath(e);
	}
	return null;
    }

    private String getEndFileFromEvent(EslEvent e) {
	if( (getAction(e) != null &&  getAction(e).equals(("stop-recording"))))
	{
	    return getPath(e);
	}
	return null;
    }

    private String getInnoUniqueIdFromEvent(EslEvent e) {
	return (e.getEventHeaders().get("Inno-Unique-ID"));
    }

    private String getFSUniqueIdFromEvent(EslEvent e) {

	return (e.getEventHeaders().get("Caller-Unique-ID"));
    }

    // This needs to be tested.
    private String getFSCalledSource(EslEvent e) {
	// skypopen
	// sipinnouser
	// sofia/internal/+18066860745@66.62.60.228:5060
	String str = e.getEventHeaders().get("Caller-Channel-Name");
	// if()
	if (str != null) {
	    if (str.indexOf("skypopen") >= 0) {
		return "SKYPE";
	    } else if (str.indexOf("sipinnouser") >= 0) {
		return "VOIP";
	    } else if (str.indexOf("sofia/internal") >= 0) {
		return "PHONE";
	    }

	}
	return (e.getEventHeaders().get("Caller-Channel-Name"));
    }

    private Integer getMemeberIdFromEvent(EslEvent e) {
	try {
	    if (e != null)
		return new Integer(e.getEventHeaders().get("Member-ID"));

	} catch (Exception ex) {
	    log.warn("member id is null..");
	}
	return 0;
    }

    public void shutdown() {
	// TODO Auto-generated method stub

    }

}
