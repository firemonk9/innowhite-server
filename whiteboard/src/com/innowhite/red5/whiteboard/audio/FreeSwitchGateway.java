/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innowhite.red5.whiteboard.audio;

//import org.bigbluebutton.webconference.voice.freeswitch.actions.EjectParticipantCommand;
//import org.bigbluebutton.webconference.voice.freeswitch.actions.MuteParticipantCommand;
//import org.bigbluebutton.webconference.voice.events.ConferenceEventListener;
//import org.bigbluebutton.webconference.voice.freeswitch.FreeswitchHeartbeatMonitor;
import java.util.HashMap;
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
import com.innowhite.red5.audio.events.ParticipantJoinedEvent;
import com.innowhite.red5.audio.events.ParticipantLeftEvent;
import com.innowhite.red5.audio.events.ParticipantMutedEvent;
import com.innowhite.red5.audio.events.ParticipantTalkingEvent;

/**
 *
 * @author leif
 */
public class FreeSwitchGateway extends Observable implements IEslEventListener {
    //private final Logger log = Red5LoggerFactory.getLogger( EslEventListener.class );
    private static Logger log = Red5LoggerFactory.getLogger(FreeSwitchGateway.class, "whiteboard");
   
    private ManagerConnection managerConnection;
   // private ConferenceEventListener conferenceEventListener;
    private FreeswitchHeartbeatMonitor heartbeatMonitor;
    static Map<String,Boolean> resultMap = new HashMap<String,Boolean>();
    
     static   {
       
        resultMap.put("start-talking", true);
        resultMap.put("stop-talking", false);
        
    }
    
    
    public void startup() {

    	
    	
        Client c = managerConnection.getESLClient();
        c.addEventListener( this );
     //   c.cancelEventSubscriptions();
        c.setEventSubscriptions( "plain", "all" );
        c.addEventFilter( "Event-Name", "heartbeat" );
        c.addEventFilter( "Event-Name", "custom" );
        c.addEventFilter( "Event-Name", "background_job" );
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(FreeswitchHeartbeatMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }


        //Start Heartbeat and exception Event Observer Monitor
        if(heartbeatMonitor == null) { //Only startup once. as startup will be called for reconnect.
            heartbeatMonitor = new FreeswitchHeartbeatMonitor(managerConnection, this);
            this.addObserver(heartbeatMonitor);
            heartbeatMonitor.start();
        }
    }
    
    public void setManagerConnection(ManagerConnection managerConnection) {
		this.managerConnection = managerConnection;
	}

//	public void eventReceived( EslEvent event )
//    {
//        log.info( "Event received [{}]", event );
//    }
	
	
	  public void eventReceived(EslEvent event) {
	        
		  if(event.getEventName().equals(FreeswitchHeartbeatMonitor.EVENT_HEARTBEAT)) {
	            setChanged();
	            notifyObservers(event);
	            return; //No need to log.debug or process further the Observer will act on this
	        }
	        //Ignored, Noop This is all the NON-Conference Events except Heartbeat
	        log.debug( "eventReceived [{}]", event );

	    }


    public void backgroundJobResultReceived( EslEvent event )
    {
        log.info( "Background job result received [{}]", event );
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
        String jobId = managerConnection.getESLClient().sendAsyncApiCommand( mpc.getCommand(), mpc.getCommandArgs());
        log.debug("mute called for room [{}] jobid [{}]", confRoom, jobId);
    }

//    @Override
    public void eject(String confRoom, Integer participant) {
        EjectParticipantCommand mpc = new EjectParticipantCommand(confRoom, participant, 0);
        String jobId = managerConnection.getESLClient().sendAsyncApiCommand( mpc.getCommand(), mpc.getCommandArgs());
        log.debug("eject/kick called for room [{}]  participant [{}]", confRoom, participant);
        
    }

	
	
	// added innoUserUnique parameter
    public void conferenceEventJoin(String uniqueId, String confName, int confSize,String callerUsername ,EslEvent event) {
       
    	
    	// this will print all the values that come from freeswitch.
    	 
    	
    	Integer memberId = getMemeberIdFromEvent(event);
    	String innoUserUnique = getInnoUniqueIdFromEvent(event);
    	
    	StringBuilder sb = new StringBuilder("");
        sb.append(uniqueId);
        Object[] args = new Object[5];
        args[0] = confName;
        args[1] = confSize;
        args[2] = sb.toString();
        args[3] = innoUserUnique;
        args[4] = callerUsername;
        
        
   //     IConnection con = Red5.getConnectionLocal();
        
        //Red5.getConnectionLocal().get
        ParticipantJoinedEvent pj = new ParticipantJoinedEvent(""+memberId, confName,
        		callerUsername, innoUserUnique, true, true);
        audioEventListener.handleConferenceEvent(pj);

        
      
        log.info("Conference join: uniqueId "+uniqueId+"  confName "+confName+" confSize "+confSize+"  innoUserUnique "+innoUserUnique+"  callerUsername  "+callerUsername+"   event  "+event);
        
       // log.info ("Conference [{}]({}) JOIN [{}] InnoUser [{}] source [{}]", args);
    }

    public void conferenceEventLeave(String uniqueId, String confName, int confSize, EslEvent event) {
    	
    	Integer memberId = this.getMemeberIdFromEvent(event);
        StringBuilder sb = new StringBuilder("");
        sb.append(uniqueId);
        Object[] args = new Object[3];
        args[0] = confName;
        args[1] = confSize;
        args[2] = sb.toString();
        
        
        ParticipantLeftEvent pj = new ParticipantLeftEvent(""+memberId,confName);
        audioEventListener.handleConferenceEvent(pj);
        
        log.info("Conference Leave: uniqueId "+uniqueId+"  confName "+confName+" confSize "+confSize+" event "+event);
       // log.info ("Conference [{}]({}) LEAVE [{}]", args);
    }

    public void conferenceEventMute(String uniqueId, String confName, int confSize, EslEvent event) {
        
    	Integer memberId = this.getMemeberIdFromEvent(event);
    	StringBuilder sb = new StringBuilder("");
        sb.append(uniqueId);
        
        
        
        ParticipantMutedEvent pj = new ParticipantMutedEvent(""+memberId,confName,true);
        audioEventListener.handleConferenceEvent(pj);
        
        log.debug ("Conference [{}] MUTE [{}]", confName, sb.toString());
    }

    public void conferenceEventUnMute(String uniqueId, String confName, int confSize, EslEvent event) {
    	
    	Integer memberId = this.getMemeberIdFromEvent(event);
        StringBuilder sb = new StringBuilder("");
        sb.append(uniqueId);
        
        ParticipantMutedEvent pj = new ParticipantMutedEvent(""+memberId,confName,true);
        audioEventListener.handleConferenceEvent(pj);
        
        log.debug ("Conference [{}] UNMUTE [{}]", confName, sb.toString());
    }

    public void conferenceEventAction(String uniqueId, String confName, int confSize, String action, EslEvent event) {
    	
    	Integer memberId = this.getMemeberIdFromEvent(event);
        StringBuilder sb = new StringBuilder("");
        sb.append(""+memberId);
        sb.append(" action=[");
        sb.append(action);
        sb.append("]");
        
        //log.debug("Conference leave: "+sb);
        log.debug("Conference Action talk: memberId "+memberId+"  confName "+confName+" confSize "+confSize+" event "+event+"  action "+action);
        
        if(resultMap.get(action) != null)
        {
        	ParticipantTalkingEvent pj = new ParticipantTalkingEvent(""+memberId,confName, resultMap.get(action));
        	audioEventListener.handleConferenceEvent(pj);
        
        	log.debug("Conference Action talk: memberId "+memberId+"  confName "+confName+" confSize "+confSize+" event "+event+"  action "+action);
        }
        // log.info ("Conference [{}] Action [{}]", confName, sb.toString());
    }

    
   
    
    
    public void conferenceEventTransfer(String uniqueId, String confName, int confSize, EslEvent event) {
        //Noop
    }

    public void conferenceEventThreadRun(String uniqueId, String confName, int confSize, EslEvent event) {
        //Noop
    }

    public void conferenceEventPlayFile(String uniqueId, String confName, int confSize, EslEvent event) {
        //Noop
    }

    public void exceptionCaught(ExceptionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private Integer getMemeberIdFromEvent(EslEvent e)
    {
        return new Integer(e.getEventHeaders().get("Member-ID"));
    }
    private String getInnoUniqueIdFromEvent(EslEvent e)
    {
        return e.getEventHeaders().get("Inno-Unique-ID");
    }

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conferenceEventJoin(String uniqueId, String confName,
			int confSize, EslEvent event) {
		Integer memberId = getMemeberIdFromEvent(event);
    	String innoUserUnique = getInnoUniqueIdFromEvent(event);
    	
    	StringBuilder sb = new StringBuilder("");
        sb.append(uniqueId);
        Object[] args = new Object[5];
        args[0] = confName;
        args[1] = confSize;
        args[2] = sb.toString();
        args[3] = innoUserUnique;
        args[4] = memberId;
        
        
   //     IConnection con = Red5.getConnectionLocal();
        
        //Red5.getConnectionLocal().get
        ParticipantJoinedEvent pj = new ParticipantJoinedEvent(""+memberId, confName,
        		""+memberId, innoUserUnique, true, true);
        audioEventListener.handleConferenceEvent(pj);

        
      
        log.info("Conference join: uniqueId "+uniqueId+"  confName "+confName+" confSize "+confSize+"  innoUserUnique "+innoUserUnique+"  callerUsername  "+memberId+"   event  "+event);
        
 
		
	}

}
