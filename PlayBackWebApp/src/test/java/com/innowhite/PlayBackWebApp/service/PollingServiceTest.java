package com.innowhite.PlayBackWebApp.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.PlayBackWebApp.model.PollAnswerOptions;
import com.innowhite.PlayBackWebApp.model.PollQuestion;
import com.innowhite.PlayBackWebApp.model.PollUserAnswers;
import com.innowhite.PlayBackWebApp.util.Constants;

/** To implement PollingServiceTest class
 * @author Tanuja
 * @Date Feb 07,2012
 */

public class PollingServiceTest {

		PollingService pollingService;
		
		public PollingServiceTest() {
	       
		   //initialize the spring container
	       ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
	       pollingService = (PollingService) context.getBean("pollingService");
	    }
		
		// Question Description, Question Type, UserID
		public void insertQuestion(){
			pollingService.savePollingQuestion("This is first Question ?",Constants.SINGLE_ANSWER_QUESTION,99);
			pollingService.savePollingQuestion("This is second Question ?",Constants.MULTIPLE_ANSWER_QUESTION,23);
		}
		
		// Option Description, Question ID, Option ID
		public void insertOption(){
			//pollingService.savePollingAnswerOptions("True",1,1);
			//pollingService.savePollingAnswerOptions("False",1,2);
			pollingService.savePollingAnswerOptions("Option21",2,1);
			pollingService.savePollingAnswerOptions("OPtion22",2,2);
			pollingService.savePollingAnswerOptions("Option23",2,3);
		}
		
		// QuestionID, OptionID, UserID, RoomID
		public void insertUserAnswer(){
			pollingService.savePollingUserAnswer(2,3,101,"123ABC890");
			pollingService.savePollingUserAnswer(2,3,102,"123ABC890");
			
		}
		
		// User ID
		public void getPollQuestionsForUser(){
			List<PollQuestion> questList = pollingService.getPollQuestionsForUser(23);
			System.out.println("====No of questions by user==========="+questList.size());
		}
		
		//Question ID
		public void getAnswerOptionsForQuestion(){
			List<PollAnswerOptions> optionList = pollingService.getPollAnswerOptionsForQuestion(2);
			System.out.println("====No of options for Question========="+optionList.size());
		}
		
		//Question ID
		public void getAllUsersAnswerForQuestion(){
			List<PollUserAnswers> usersAnswerList = pollingService.getAllUsersAnswerForQuestion(2);
			System.out.println("====No of Users answered for Question======="+usersAnswerList.size());
		}
		
		//Question ID , Question Description
		public void updatePollQuestion(){
			int intreturnval = pollingService.updatePollQuestion(6,"This is updated question SIX");
			System.out.println("====updated return val========="+intreturnval);
		}
		
		//Question ID  , Option ID, Option Description
		public void updatePollAnswerOption(){
			int intreturnval = pollingService.updatePollAnswerOption(3,3,"Udated33");
			System.out.println("====updated return val========="+intreturnval);
		}
		
		//id, Option ID, Room ID
		public void updateUserAnswer(){
			int intreturnval = pollingService.updateUserAnswer(2,4,"123ABC890");
			System.out.println("====updated return val========="+intreturnval);
		}
		
		//Question ID
		public void deletePollQuestion(){
			int intreturnval = pollingService.deletePollQuestion(3);
			System.out.println("====delete return val========="+intreturnval);
		}
		
		//id,  Question ID, Option ID
		public void deletePollAnswerOptions(){
			int intReturnVal = pollingService.deletePollAnswerOptions(3,2,1);
			System.out.println("====delete return val========="+intReturnVal);
			if(intReturnVal>0){
				System.out.println("====Successfully Deleted====");
			}else{
				System.out.println("===Deletion Associated. Can't delete===");
			}
		}
		
		public static void main(String[] args) {
			
				PollingServiceTest pstObj =new PollingServiceTest();
				
				pstObj.insertQuestion();
				//pstObj.insertOption();
				//pstObj.insertUserAnswer();
				
				//pstObj.getPollQuestionsForUser();
				//pstObj.getAnswerOptionsForQuestion();
				//pstObj.getAllUsersAnswerForQuestion();
				
				//pstObj.updatePollQuestion();
				//pstObj.updatePollAnswerOption();
				//pstObj.updateUserAnswer();
				
				//pstObj.deletePollQuestion();
				//pstObj.deletePollAnswerOptions();
			
		}
}
