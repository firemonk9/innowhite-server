package com.innowhite.red5.whiteboard.audio;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;

import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.red5.whiteboard.Main;

/**
 *
 * @author leif
 */
public class ExampleClient {
    //private final Logger log = Red5LoggerFactory.getLogger( ExampleClient.class );
    private static Logger log = Red5LoggerFactory.getLogger(ExampleClient.class, InnowhiteConstants.APP_NAME);
    private String host = "demo.innowhite.com";
    private int port = 8021;
    private String password = "InnO123WhitE";
    private Client client;
  
    private Main red5App;
    
    private FreeSwitchGateway freeSwitchGateway;
    
    
  

//	public void setFreeSwitchGateway(FreeSwitchGateway freeSwitchGateway) {
//		this.freeSwitchGateway = freeSwitchGateway;
//	}
//
//	public void do_connect() throws InterruptedException
//    {
//        client = new Client();
//        client.addEventListener( freeSwitchGateway );
//
//        log.debug( "Client connecting .." );
//        try
//        {
//            client.connect( host, port, password, 2 );
//        }
//        catch ( InboundConnectionFailure e )
//        {
//        	e.printStackTrace();
//            log.error( "Connect failed", e );
//            return;
//        }
//        log.debug( "Client connected .." );
//        log.debug(" Client connected .. to sudio server");
//
//        //client.setEventSubscriptions( "plain", "heartbeat BACKGROUND_JOB CUSTOM" );
//        client.setEventSubscriptions( "plain", "all" );
//       // client.addEventFilter( "Event-Name", "heartbeat" );
//        client.addEventFilter( "Event-Name", "custom" );
//        client.addEventFilter( "Event-Name", "background_job" );
//
//    }
//
//    public void close_client() {
//       // stopHeartBeatThread();
//        client.close();
//    }
//
//    private Main main;
//    
//    public ExampleClient(){
//    	log.debug("   -- ExampleClient -- ");
//    	
//    }
// 
//    public static void main(String args[]){
//    	new ExampleClient();
//    }
//    
//    /**
//     * @param args the command line arguments
//     */
//    public void run() {
//      //  ExampleClient test = new ExampleClient();
//    	
//    	log.debug("Starting the thread ....");
//        try {
//            do_connect();
//            //FIXME.. the reconnect code that this is intended to implement requires refactoring.
//            //test.startHeartBeatThread();
//           // Scanner myInput = new Scanner(System.in);
//            boolean notDone = true;
////            while(notDone){
////                String Input1 = myInput.next();
////                if(Input1.equalsIgnoreCase("q")){
////                    notDone = false;
////                }
//                sleep( 25000 );
////            }
//            //test.stopHeartBeatThread();
//          //  close_client();
//        }catch(InterruptedException ie) {
//        	ie.printStackTrace();
//            log.error("error ",ie);
//        	return;
//        }catch(Exception ie) {
//        	ie.printStackTrace(); 
//        	log.error("error ",ie);
//            return;
//        }
//        
//    }

	public void setRed5App(Main red5App) {
		log.debug("  setting the bean ###################  ");
		this.red5App = red5App;
	}

	public Main getRed5App() {
		return red5App;
	}

	

}
