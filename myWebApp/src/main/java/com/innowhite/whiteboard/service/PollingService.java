package com.innowhite.whiteboard.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.PollAnswerOptions;
import com.innowhite.whiteboard.persistence.beans.PollQuestion;
import com.innowhite.whiteboard.persistence.beans.PollUserAnswers;
import com.innowhite.whiteboard.persistence.dao.PollingDAO;


/** To implement PollingService class
 * @author Tanuja
 * @Date Feb 16, 2012
 */

public class PollingService {
	private static final Logger log = LoggerFactory.getLogger(PollingService.class);
	
	/* To get the pollid for new poll   */
	public static String getPollID() {
		log.debug("Entered getPollID");
		
		long intPollId=0;
		intPollId=PollingDAO.getpollId();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<polling>");
		sb.append("<pollid>" + intPollId + "</pollid>");
		sb.append("</polling>");
		 
		return sb.toString();
	}
	
	
		 
	
	
	/* To save the Poll Question */
	public static long savePollingQuestion(String strQuestDesc,String strQuestType,int intUserId){
		log.debug("==entered savePollingQuestion==");
		long longQuestId=0;
		try {
	    	PollQuestion pq = new PollQuestion();
	    	
	    	pq.setQuestDesc(strQuestDesc);
	    	pq.setQuestType(strQuestType);
	    	pq.setUserId(intUserId);
	    	 
	    	longQuestId=PollingDAO.savePollingQuestion(pq);
			 
		  } catch (Exception e) {
			    e.printStackTrace();
		  }
		 return longQuestId;
	 }
	
	/* To save given options for a question */
	public static void savePollingAnswerOptions(String strOptionDesc,int intQuestId, int intOptionId){
		log.debug("==entered savePollingAnswerOptions==");
		try {
	    	PollAnswerOptions paoObj = new PollAnswerOptions();
	    	
	    	paoObj.setOptionDesc(strOptionDesc);
	    	paoObj.setOptionId(intOptionId);
	    	paoObj.setQuestId(intQuestId);
	    	 
	    	PollingDAO.savePollingAnswerOptions(paoObj);
			 
		  } catch (Exception e) {
			    e.printStackTrace();
		  }
		 
	 }
	
	/* To save user entered answer for a selected question */
	public static void savePollingUserAnswer(int intQuestId, int intOptionId,int intUserId,String strRoomId){
		log.debug("==entered savePollingUserAnswer==");
		try {
	    	PollUserAnswers puaObj = new PollUserAnswers();
	    	
	    	puaObj.setUserId(intUserId);
	    	puaObj.setOptionId(intOptionId);
	    	puaObj.setQuestId(intQuestId);
	    	puaObj.setRoomId(strRoomId);
	 		 
	    	PollingDAO.savePollingUserAnswer(puaObj);
		  } catch (Exception e) {
			    e.printStackTrace();
		  }
	 }
	
	/* To display all the questions created based on user */
	public static List<PollQuestion> getPollQuestionsForUser(int intUserId){
		log.debug("==entered getPollQuestionsForUser==");
		List<PollQuestion> questList = new ArrayList<PollQuestion>();
		try{
			questList = PollingDAO.getPollQuestionsForUser(intUserId);
			
			return questList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/* To display the available options for a specific question */
	public static List<PollAnswerOptions> getPollAnswerOptionsForQuestion(int intQuestId){
		log.debug("==entered getPollAnswerOptionsForQuestion==");
		List<PollAnswerOptions> ansOptionsList = new ArrayList<PollAnswerOptions>();
		try{
			ansOptionsList = PollingDAO.getPollAnswerOptionsForQuestion(intQuestId);
			
			return ansOptionsList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/* To display all users answers for a question */
	public static List<PollUserAnswers> getAllUsersAnswerForQuestion(long intQuestId){
		log.debug("==entered getAllUsersAnswerForQuestion==");
		List<PollUserAnswers> usersAnswerList = new ArrayList<PollUserAnswers>();
		try{
			usersAnswerList = PollingDAO.getAllUsersAnswerForQuestion(intQuestId);
			
			return usersAnswerList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/* To update the poll question description */
	public static int updatePollQuestion(long intQuestId, String strQuestDesc){
		log.debug("==entered updatePollQuestion==");
		int intReturnval=0;
		try{
			PollQuestion pq = new PollQuestion();
	    	
	    	pq.setId(intQuestId);
	    	pq.setQuestDesc(strQuestDesc);
	    	intReturnval = PollingDAO.updatePollQuestion(pq);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To update selected option description for a question */
	public static int updatePollAnswerOption(long intQuestId, int intOptionId,String strOptionDesc){
		log.debug("==entered updatePollAnswerOption==");
		int intReturnval=0;
		try{
			PollAnswerOptions paoObj = new PollAnswerOptions();
	    	
	    	paoObj.setOptionDesc(strOptionDesc);
	    	paoObj.setOptionId(intOptionId);
	    	paoObj.setQuestId(intQuestId);
	    	
	    	intReturnval = PollingDAO.updatePollAnswerOption(paoObj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To update user answer */	
	public static int updateUserAnswer(long id, int intOptionId, String strRoomId){
		log.debug("==entered updateUserAnswer==");
		int intReturnval=0;
		try{
			
			PollUserAnswers puaObj = new PollUserAnswers();
	    	puaObj.setId(id);
	    	puaObj.setOptionId(intOptionId);
	    	puaObj.setRoomId(strRoomId);
	    	
	    	intReturnval = PollingDAO.updateUserAnswer(puaObj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To delete poll Question */	
	public static int deletePollQuestion(long questId){
		log.debug("==entered deletePollQuestion==");
		int intReturnval=0;
		try{			
	    	intReturnval = PollingDAO.deletePollQuestion(questId);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To delete selected option for a question */		
	public static int deletePollAnswerOptions(long id, long intQuestId, int intOptionId){
		log.debug("==entered deletePollAnswerOptions==");
		int intReturnVal=0;
		try{
			
			PollAnswerOptions paoObj = new PollAnswerOptions();
			paoObj.setId(id);
			paoObj.setQuestId(intQuestId);
			paoObj.setOptionId(intOptionId);
	    	
			intReturnVal = PollingDAO.deletePollAnswerOptions(paoObj);
	    	if(intReturnVal>0){
				System.out.println("====Successfully Deleted====");
			}else{
				System.out.println("===Deletion Associated. Can't delete===");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnVal;
	}
	

	
	
	
}
