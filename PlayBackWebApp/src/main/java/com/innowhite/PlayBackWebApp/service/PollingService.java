package com.innowhite.PlayBackWebApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlayBackWebApp.dao.PollingDao;
import com.innowhite.PlayBackWebApp.model.PollAnswerOptions;
import com.innowhite.PlayBackWebApp.model.PollQuestion;
import com.innowhite.PlayBackWebApp.model.PollUserAnswers;

/** To implement PollingService class
 * @author Tanuja
 * @Date Feb 07,2012
 */

public class PollingService {
	
	private static final Logger log = LoggerFactory.getLogger(PollingService.class);
	PollingDao pollingDao;
		 
	public void setPollingDao(PollingDao pollingDao){
		 this.pollingDao=pollingDao;
	}
	public PollingDao getPollingDao() {
			return pollingDao;
	}
	
	/* To save the Poll Question */
	public void savePollingQuestion(String strQuestDesc,String strQuestType,int intUserId){
		log.debug("==entered savePollingQuestion==");
		try {
	    	Date date = new Date();
	    	PollQuestion pq = new PollQuestion();
	    	
	    	pq.setQuestDesc(strQuestDesc);
	    	pq.setQuestType(strQuestType);
	    	pq.setUserId(intUserId);
	    	pq.setCreatedDate(date);
	    	pq.setUpdatedDate(null);  //this one not working. Current date getting inserted.
	 		 
	    	pollingDao.savePollingQuestion(pq);
			 
		  } catch (Exception e) {
			    e.printStackTrace();
		  }
		 
	 }
	
	/* To save given options for a question */
	public void savePollingAnswerOptions(String strOptionDesc,int intQuestId, int intOptionId){
		log.debug("==entered savePollingAnswerOptions==");
		try {
	    	Date date = new Date();
	    	PollAnswerOptions paoObj = new PollAnswerOptions();
	    	
	    	paoObj.setOptionDesc(strOptionDesc);
	    	paoObj.setOptionId(intOptionId);
	    	paoObj.setQuestId(intQuestId);
	    	paoObj.setCreatedDate(date);
	 		 
	    	pollingDao.savePollingAnswerOptions(paoObj);
			 
		  } catch (Exception e) {
			    e.printStackTrace();
		  }
		 
	 }
	
	/* To save user entered answer for a selected question */
	public void savePollingUserAnswer(int intQuestId, int intOptionId,int intUserId,String strRoomId){
		log.debug("==entered savePollingUserAnswer==");
		try {
	    	Date date = new Date();
	    	PollUserAnswers puaObj = new PollUserAnswers();
	    	
	    	puaObj.setUserId(intUserId);
	    	puaObj.setOptionId(intOptionId);
	    	puaObj.setQuestId(intQuestId);
	    	puaObj.setCreatedDate(date);
	    	puaObj.setRoomId(strRoomId);
	 		 
	    	pollingDao.savePollingUserAnswer(puaObj);
		  } catch (Exception e) {
			    e.printStackTrace();
		  }
	 }
	
	/* To display all the questions created based on user */
	public List<PollQuestion> getPollQuestionsForUser(int intUserId){
		log.debug("==entered getPollQuestionsForUser==");
		List<PollQuestion> questList = new ArrayList<PollQuestion>();
		try{
			questList = pollingDao.getPollQuestionsForUser(intUserId);
			
			return questList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/* To display the available options for a specific question */
	public List<PollAnswerOptions> getPollAnswerOptionsForQuestion(int intQuestId){
		log.debug("==entered getPollAnswerOptionsForQuestion==");
		List<PollAnswerOptions> ansOptionsList = new ArrayList<PollAnswerOptions>();
		try{
			ansOptionsList = pollingDao.getPollAnswerOptionsForQuestion(intQuestId);
			
			return ansOptionsList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/* To display all users answers for a question */
	public List<PollUserAnswers> getAllUsersAnswerForQuestion(int intQuestId){
		log.debug("==entered getAllUsersAnswerForQuestion==");
		List<PollUserAnswers> usersAnswerList = new ArrayList<PollUserAnswers>();
		try{
			usersAnswerList = pollingDao.getAllUsersAnswerForQuestion(intQuestId);
			
			return usersAnswerList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/* To update the poll question description */
	public int updatePollQuestion(int intQuestId, String strQuestDesc){
		log.debug("==entered updatePollQuestion==");
		int intReturnval=0;
		try{
			Date date = new Date();
	    	PollQuestion pq = new PollQuestion();
	    	
	    	pq.setId(intQuestId);
	    	pq.setQuestDesc(strQuestDesc);
	    	pq.setUpdatedDate(date);
	    	intReturnval = pollingDao.updatePollQuestion(pq);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To update selected option description for a question */
	public int updatePollAnswerOption(int intQuestId, int intOptionId,String strOptionDesc){
		log.debug("==entered updatePollAnswerOption==");
		int intReturnval=0;
		try{
			Date date = new Date();
	    	PollAnswerOptions paoObj = new PollAnswerOptions();
	    	
	    	paoObj.setOptionDesc(strOptionDesc);
	    	paoObj.setOptionId(intOptionId);
	    	paoObj.setQuestId(intQuestId);
	    	paoObj.setUpdatedDate(date);
	    	intReturnval = pollingDao.updatePollAnswerOption(paoObj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To update user answer */	
	public int updateUserAnswer(int id, int intOptionId, String strRoomId){
		log.debug("==entered updateUserAnswer==");
		int intReturnval=0;
		try{
			Date date = new Date();
	    	PollUserAnswers puaObj = new PollUserAnswers();
	    	puaObj.setId(id);
	    	puaObj.setOptionId(intOptionId);
	    	puaObj.setUpdatedDate(date);
	    	puaObj.setRoomId(strRoomId);
	    	
	    	intReturnval = pollingDao.updateUserAnswer(puaObj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To delete poll Question */	
	public int deletePollQuestion(int intQuestId){
		log.debug("==entered deletePollQuestion==");
		int intReturnval=0;
		try{
			
	    	PollQuestion pq = new PollQuestion();
	    	pq.setId(intQuestId);
	    	
	    	intReturnval = pollingDao.deletePollQuestion(pq);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
	/* To delete selected option for a question */		
	public int deletePollAnswerOptions(int id, int intQuestId, int intOptionId){
		log.debug("==entered deletePollAnswerOptions==");
		int intReturnval=0;
		try{
			
			PollAnswerOptions paoObj = new PollAnswerOptions();
			paoObj.setId(id);
			paoObj.setQuestId(intQuestId);
			paoObj.setOptionId(intOptionId);
	    	
	    	intReturnval = pollingDao.deletePollAnswerOptions(paoObj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturnval;
	}
	
}
