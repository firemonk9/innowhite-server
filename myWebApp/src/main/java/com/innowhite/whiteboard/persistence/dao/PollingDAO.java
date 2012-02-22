package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.PollAnswerOptions;
import com.innowhite.whiteboard.persistence.beans.PollQuestion;
import com.innowhite.whiteboard.persistence.beans.PollUserAnswers;

/** To implement PollingDAO class
 * @author Tanuja
 * @Date Feb 16, 2012
 */
public class PollingDAO {
	 private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();
	 private static final Logger log = LoggerFactory.getLogger(PollingDAO.class);
	
	 /* To get the pollid for new poll   */
	public static long getpollId() {
    	log.debug("Entered getpollId");
    	long retunrVal=0; long maxId=0;
    	try {
    		maxId=(Long)sqlMapClient.queryForObject("pollQuestMaxId");
    	    log.debug(" returned from getpollId is : " + maxId);
    	    retunrVal=maxId+1;
    	} catch (SQLException e) {
    	    log.error(e.getMessage());
    	}
    	return retunrVal;
      }

	
    /* To save the Poll Question */
    public static long savePollingQuestion(PollQuestion pollQuestion){
    	log.debug("==entered==PollingDAO== savePollingQuestion==");
    	long retunrQuestId=0;
    	try{
    		
    		if(pollQuestion!=null){
    			Object obj = sqlMapClient.insert("savePollQuestion", pollQuestion);
	    	    log.debug(" returned from savePollingQuestion is : " + obj);
	    	    retunrQuestId=Long.parseLong(obj.toString());
	    	}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return retunrQuestId;	
    }
    
    /* To save given options for a question */
    public static long savePollingAnswerOptions(PollAnswerOptions pollAnswerOptions){
    	log.debug("==entered==PollingDAO== savePollingAnswerOptions==");
    	long retunrVal=0;
    	try{
    		if(pollAnswerOptions!=null){
    			Object obj = sqlMapClient.insert("savePollAnswerOptions", pollAnswerOptions);
	    	    log.debug(" returned from savePollingAnswerOptions is : " + obj);
	    	    retunrVal=Long.parseLong(obj.toString());
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return retunrVal;	
    }
    
    /* To save user entered answer for a selected question */ 
    public static long savePollingUserAnswer(PollUserAnswers pollUserAnswer){
    	log.debug("==entered==PollingDAO== savePollingUserAnswer==");
    	long retunrVal=0;
    	try{
    		if(pollUserAnswer!=null){
    			Object obj = sqlMapClient.insert("savePollUserAnswers", pollUserAnswer);
	    	    log.debug(" returned from savePollingUserAnswer is : " + obj);
	    	    retunrVal=Long.parseLong(obj.toString());
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return retunrVal;
    }
    
    /* To display all the questions created based on user */
    public static List<PollQuestion> getPollQuestionsForUser(int intUserId){
    	log.debug("==entered==PollingDAO== getPollQuestionsForUser==");
    	List<PollQuestion> questList = new ArrayList<PollQuestion>();
    	try{    		
    		questList =(List)sqlMapClient.queryForList("pollQuestionList", intUserId);
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		
    	return questList;
     }
    
    /* To display the available options for a specific question */
    public static List<PollAnswerOptions> getPollAnswerOptionsForQuestion(long longQuestId){
    	log.debug("==entered==PollingDAO== getPollAnswerOptionsForQuestion==");
    	List<PollAnswerOptions> answerOptionsList = new ArrayList<PollAnswerOptions>();
    	try{
    		answerOptionsList =(List)sqlMapClient.queryForList("answerOptionsList", longQuestId);
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		
    	return answerOptionsList;
     }
    
    /* To display all users answers for a question */
    @Transactional
    public static List<PollUserAnswers> getAllUsersAnswerForQuestion(long longQuestId){
    	log.debug("==entered==PollingDAO== getAllUsersAnswerForQuestion==");
    	List<PollUserAnswers> usersAnswerList = new ArrayList<PollUserAnswers>();
    	try{
    		usersAnswerList =(List)sqlMapClient.queryForList("userAnswersList", longQuestId);
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		
    	return usersAnswerList;
     }
    
    /* To update the poll question description */
    public static int updatePollQuestion(PollQuestion pqObj) {

		log.debug("==entered==PollingDAO== updatePollQuestion==");
		int updatedval=0;
		try{
			
			Object obj = sqlMapClient.update("updatePollQuestion", pqObj);
    	    log.debug(" returned from updatePollQuestion is : " + obj);
    	    updatedval=Integer.parseInt(obj.toString());
		
		}catch(SQLException e){
			e.printStackTrace();
		}
		return updatedval;
	}
    
    /* To update selected option description for a question */
    public static int updatePollAnswerOption(PollAnswerOptions paoObj) {

		log.debug("==entered==PollingDAO== updatePollAnswerOption==");
		int updatedval=0;
		try{
			Object obj = sqlMapClient.update("updateAnswerOption", paoObj);
    	    log.debug(" returned from updatePollAnswerOption is : " + obj);
    	    updatedval=Integer.parseInt(obj.toString());

		}catch(SQLException e){
			e.printStackTrace();
		}
		return updatedval;
	}
    
    /* To update user answer */	
    public static int updateUserAnswer(PollUserAnswers puaObj) {

		log.debug("==entered==PollingDAO== updateUserAnswer==");
		int updatedval=0;
		try{
			Object obj = sqlMapClient.update("updateUserAnswer", puaObj);
    	    log.debug(" returned from updateUserAnswer is : " + obj);
    	    updatedval=Integer.parseInt(obj.toString());
		
		}catch(SQLException e){
			e.printStackTrace();
		}
		return updatedval;
	}
    
    /* To delete poll Question. */	
    public static int deletePollQuestion(long questId) {

		log.debug("==entered==PollingDAO== deletePollQuestion==");
		int intAnsOpt=0; int intUserAns=0; 
		try{
			Object obj1 = sqlMapClient.delete("deleteUserAnswers", questId);
			intUserAns=Integer.parseInt(obj1.toString());
			
			Object obj2 = sqlMapClient.delete("deleteAnswerOptions",questId);
			intAnsOpt=Integer.parseInt(obj2.toString());
			
			Object obj3 = sqlMapClient.delete("deletePollQuestion",questId);
			
			log.debug("deletePollQuestion---intUserAns-- "+ intUserAns + "--intAnsOpt---" + intAnsOpt);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;			
		}
		
	}
    
    /* To delete selected option for a question */	
     public static int deletePollAnswerOptions(PollAnswerOptions paoObj) {

		log.debug("==entered==PollingDAO== deletePollAnswerOptions==");
		int intReturnVal=0; int intCount=0; 
		try{
			intCount =(Integer)sqlMapClient.queryForObject("countUserAnswers", paoObj);
			if(intCount<=0){
				Object obj = sqlMapClient.delete("deletePollAnswerOption", paoObj.getId());
				log.debug(" returned from deletePollAnswerOptions  : " + obj);
		    	intReturnVal=1;
				
			}else if(intCount>0){
				//Deletion is Associated. (Option is already selected by some Users)
				intReturnVal=0;
			}
			
			log.debug("deletePollAnswerOptions---intCount-- "+ intCount + "--intReturnVal---" + intReturnVal);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return intReturnVal;
	}
    
    


}
