package com.innowhite.whiteboard.test;

import java.util.List;

import com.innowhite.whiteboard.persistence.beans.PollAnswerOptions;
import com.innowhite.whiteboard.persistence.beans.PollQuestion;
import com.innowhite.whiteboard.persistence.beans.PollUserAnswers;
import com.innowhite.whiteboard.service.PollingService;
import com.innowhite.whiteboard.util.Constants;


/** To implement PollingServiceTest class
 * @author Tanuja
 * @Date Feb 16,2012
 */

public class PollingServiceTest {
	
	
	// To get the pollid for new poll   
	public void getPollId(){
		String pollIdXml=PollingService.getPollID();
		System.out.println("======pollIdXml====="+pollIdXml);
	}

				
		// Question Description, Question Type, UserID
		public void insertQuestion(){
			PollingService.savePollingQuestion("This is first Question ?",Constants.SINGLE_ANSWER_QUESTION,99);
			PollingService.savePollingQuestion("This is second Question ?",Constants.MULTIPLE_ANSWER_QUESTION,99);
		}
		
		// Option Description, Question ID, Option ID
		public void insertOption(){
			PollingService.savePollingAnswerOptions("True",1,1);
			PollingService.savePollingAnswerOptions("False",1,2);
		}
		
		// QuestionID, OptionID, UserID, RoomID
		public void insertUserAnswer(){
			PollingService.savePollingUserAnswer(1,2,107,"123ABC890");
			PollingService.savePollingUserAnswer(1,2,108,"123ABC890");
		}
		
		// User ID
		public void getPollQuestionsForUser(){
			String returnXML = PollingService.getPollQuestionsForUser(99);
			System.out.println("====Questions for user in XML======"+returnXML);
		}
		
		//Question ID
		public void getAnswerOptionsForQuestion(){
			List<PollAnswerOptions> optionList = PollingService.getPollAnswerOptionsForQuestion(8);
			System.out.println("====No of options for Question========="+optionList.size());
		}
		
		//Question ID
		public void getAllUsersAnswerForQuestion(){
			List<PollUserAnswers> usersAnswerList = PollingService.getAllUsersAnswerForQuestion(8);
			System.out.println("====No of Users answered for Question======="+usersAnswerList.size());
		}
		
		//Question ID , Question Description
		public void updatePollQuestion(){
			int intreturnval = PollingService.updatePollQuestion(7,"This is updated question Seven");
			System.out.println("====updated return val========="+intreturnval);
		}
		
		//Question ID  , Option ID, Option Description
		public void updatePollAnswerOption(){
			int intreturnval = PollingService.updatePollAnswerOption(8,4,"update84");
			System.out.println("====updated return val========="+intreturnval);
		}
		
		//id, Option ID, Room ID
		public void updateUserAnswer(){
			int intreturnval = PollingService.updateUserAnswer(6,2,"123ABC890");
			System.out.println("====updated return val========="+intreturnval);
		}
		
		//Question ID
		public void deletePollQuestion(){
			int intreturnval = PollingService.deletePollQuestion(7);
			System.out.println("====delete return val========="+intreturnval);
		}
		
		//id,  Question ID, Option ID
		public void deletePollAnswerOptions(){
			int intReturnVal = PollingService.deletePollAnswerOptions(12,8,4);
			System.out.println("====delete return val========="+intReturnVal);
		}
		
		public static void main(String[] args) {
			
				PollingServiceTest pstObj =new PollingServiceTest();
				
				//pstObj.insertQuestion();
				//pstObj.insertOption();
				//pstObj.insertUserAnswer();
				
				pstObj.getPollQuestionsForUser();
				//pstObj.getAnswerOptionsForQuestion();
				//pstObj.getAllUsersAnswerForQuestion();
				
				//pstObj.updatePollQuestion();
				//pstObj.updatePollAnswerOption();
				//pstObj.updateUserAnswer();
				
				//pstObj.deletePollQuestion();
				//pstObj.deletePollAnswerOptions();
				
				//pstObj.getPollId();
				
			
		}
}
