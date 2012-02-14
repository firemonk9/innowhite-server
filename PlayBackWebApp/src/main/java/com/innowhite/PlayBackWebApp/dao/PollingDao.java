package com.innowhite.PlayBackWebApp.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlayBackWebApp.model.PollAnswerOptions;
import com.innowhite.PlayBackWebApp.model.PollQuestion;
import com.innowhite.PlayBackWebApp.model.PollUserAnswers;

/** To implement PollingDao class
 * @author Tanuja
 * @Date Feb 07,2012
 */

public class PollingDao {
	
		private static final Logger log = LoggerFactory.getLogger(PollingDao.class);
		SessionFactory sessionFactory;

	    public void setSessionFactory(SessionFactory sessionFactory) {
	    	this.sessionFactory = sessionFactory;
	    }
	   
	    /* To save the Poll Question */
	    @Transactional
	    public void savePollingQuestion(PollQuestion pollQuestion){
	    	log.debug("==entered savePollingQuestion==");
	    	try{
	    		Session session = sessionFactory.getCurrentSession();
	    		if(pollQuestion!=null){
	    			session.save(pollQuestion);
	    			session.flush();
	    			session.clear();
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	    /* To save given options for a question */
	    @Transactional
	    public void savePollingAnswerOptions(PollAnswerOptions pollAnswerOptions){
	    	log.debug("==entered savePollingAnswerOptions==");
	    	try{
	    		Session session = sessionFactory.getCurrentSession();
	    		if(pollAnswerOptions!=null){
	    			session.save(pollAnswerOptions);
	    			session.flush();
	    			session.clear();
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    
	    /* To save user entered answer for a selected question */ 
	    @Transactional
	    public void savePollingUserAnswer(PollUserAnswers pollUserAnswer){
	    	log.debug("==entered savePollingUserAnswer==");
	    	try{
	    		Session session = sessionFactory.getCurrentSession();
	    		if(pollUserAnswer!=null){
	    			session.save(pollUserAnswer);
	    			session.flush();
	    			session.clear();
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	    /* To display all the questions created based on user */
	    @Transactional
	    public List<PollQuestion> getPollQuestionsForUser(int intUserId){
	    	log.debug("==entered getPollQuestionsForUser==");
	    	List<PollQuestion> questList = new ArrayList<PollQuestion>();
	    	try{
	    		Session session = sessionFactory.getCurrentSession();
	    		Query query = session.createSQLQuery("select * from poll_question where user_id=:userId").addEntity(PollQuestion.class).setInteger("userId", intUserId);
	    		
	    		questList = query.list();
	    		session.clear();
	    		session.flush();
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
			
	    	return questList;
	     }
	    
	    /* To display the available options for a specific question */
	    @Transactional
	    public List<PollAnswerOptions> getPollAnswerOptionsForQuestion(int intQuestId){
	    	log.debug("==entered getPollAnswerOptionsForQuestion==");
	    	List<PollAnswerOptions> answerOptionsList = new ArrayList<PollAnswerOptions>();
	    	try{
	    		Session session = sessionFactory.getCurrentSession();
	    		Query query = session.createSQLQuery("select * from poll_answer_options where quest_id=:questId").addEntity(PollAnswerOptions.class).setInteger("questId", intQuestId);
	    		
	    		answerOptionsList = query.list();
	    		session.clear();
	    		session.flush();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
			
	    	return answerOptionsList;
	     }
	    
	    /* To display all users answers for a question */
	    @Transactional
	    public List<PollUserAnswers> getAllUsersAnswerForQuestion(int intQuestId){
	    	log.debug("==entered getAllUsersAnswerForQuestion==");
	    	List<PollUserAnswers> usersAnswerList = new ArrayList<PollUserAnswers>();
	    	try{
	    		Session session = sessionFactory.getCurrentSession();
	    		Query query = session.createSQLQuery("select * from poll_user_answers where quest_id=:questId").addEntity(PollUserAnswers.class).setInteger("questId", intQuestId);
	    		
	    		usersAnswerList = query.list();
	    		session.clear();
	    		session.flush();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
			
	    	return usersAnswerList;
	     }
	    
	    /* To update the poll question description */
	    @Transactional
	    public int updatePollQuestion(PollQuestion pqObj) {
	
			log.debug("==entered updatePollQuestion==");
			Session session = sessionFactory.getCurrentSession();
			int updatedval=0;
			try{
				
				String query = "update PollQuestion set questDesc=:strqdesc, updatedDate=:updatedate  where id=:intqid";
				updatedval = session.createQuery(query).setLong("intqid", pqObj.getId()).setString("strqdesc",pqObj.getQuestDesc()).setTimestamp("updatedate", pqObj.getUpdatedDate()).executeUpdate();
				log.debug("retuned updatePollQuestion val " + updatedval);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			return updatedval;
		}
	    
	    /* To update selected option description for a question */
	    @Transactional
	    public int updatePollAnswerOption(PollAnswerOptions paoObj) {
	
			log.debug("==entered updatePollAnswerOption==");
			Session session = sessionFactory.getCurrentSession();
			int updatedval=0;
			try{
				
				String query = "update PollAnswerOptions set optionDesc=:stroptdesc, updatedDate=:updatedate  where questId=:intqid and optionId=:intoptid";
				updatedval = session.createQuery(query).setString("stroptdesc",paoObj.getOptionDesc()).setTimestamp("updatedate", paoObj.getUpdatedDate()).
							setInteger("intqid", paoObj.getQuestId()).setInteger("intoptid", paoObj.getOptionId()).executeUpdate();
				log.debug("updatePollAnswerOption---retuned  val " + updatedval);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			return updatedval;
		}
	    
	    /* To update user answer */	
	    @Transactional
	    public int updateUserAnswer(PollUserAnswers puaObj) {
	
			log.debug("==entered updateUserAnswer==");
			Session session = sessionFactory.getCurrentSession();
			int updatedval=0;
			try{
				//session.update(puaObj);
	    			
				String query = "update PollUserAnswers set optionId=:intoptid, updatedDate=:updatedate  where id=:intid";
				updatedval = session.createQuery(query).setInteger("intoptid",puaObj.getOptionId()).setTimestamp("updatedate", puaObj.getUpdatedDate()).
							setLong("intid", puaObj.getId()).executeUpdate();
				
				log.debug("updateUserAnswer-----retuned  val " + updatedval);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			return updatedval;
		}
	    
	    /* To delete poll Question. */	
	    @Transactional
	    public int deletePollQuestion(PollQuestion pqObj) {
	
			log.debug("==entered deletePollQuestion==");
			Session session = sessionFactory.getCurrentSession();
			int intAnsOpt=0; int intUserAns=0; 
			try{
				String query1 = "delete from PollUserAnswers where questId=:intqid";
				intUserAns = session.createQuery(query1).setLong("intqid",pqObj.getId()).executeUpdate();
				
				String query2 = "delete from PollAnswerOptions where questId=:intqid";
				intAnsOpt = session.createQuery(query2).setLong("intqid",pqObj.getId()).executeUpdate();
				
				session.delete(pqObj);
				
				log.debug("deletePollQuestion---intUserAns-- "+ intUserAns + "--intAnsOpt---" + intAnsOpt);
				return 1;
			}catch(Exception e){
				e.printStackTrace();
				return 0;			
			}
			
		}
	    
	    /* To delete selected option for a question */	
	    @Transactional
	    public int deletePollAnswerOptions(PollAnswerOptions paoObj) {
	
			log.debug("==entered deletePollAnswerOptions==");
			Session session = sessionFactory.getCurrentSession();
			int intReturnVal=0; int intUserAns=0; 
			try{
				String query1 = "select count(*) from PollUserAnswers where questId=:intqid and optionId=:intoptid";
				Query query = session.createQuery(query1).setInteger("intqid",paoObj.getQuestId())
						.setInteger("intoptid", paoObj.getOptionId());
				
				intUserAns=((Long)query.uniqueResult()).intValue();
				
				if(intUserAns>0){
					//Deletion is Associated. (Option is already selected by some Users)
					intReturnVal=0;
				}else if(intUserAns==0){
					session.delete(paoObj);
					intReturnVal=1;
				}
				log.debug("deletePollAnswerOptions---intUserAns-- "+ intUserAns + "--intReturnVal---" + intReturnVal);
			}catch(Exception e){
				e.printStackTrace();
			}
			return intReturnVal;
		}
	    
	    
}
