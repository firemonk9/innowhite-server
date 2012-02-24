package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.PollAnswerOptions;
import com.innowhite.whiteboard.persistence.beans.PollQuestion;
import com.innowhite.whiteboard.persistence.beans.PollUserAnswers;
import com.innowhite.whiteboard.service.PollingService;

/**
 * Implementation Class for POllingServlet
 * @author Tanuja
 * @Date Feb 22, 2012
 */

public class PollingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PollingServlet.class);
	
	/* To get the pollid for new poll   */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug(" entereing doget: PollingServlet ");
		
		String reqAction=request.getParameter("reqaction");
		String strQuestDesc=request.getParameter("questDesc");
		String strQuestType=request.getParameter("questType");
		int intUserId=Integer.parseInt(request.getParameter("userId"));
		
		String strOptionDesc=request.getParameter("optionDesc");
		int intOptionId=Integer.parseInt(request.getParameter("optionId"));
		long longQuestId=Long.parseLong(request.getParameter("questId"));
		
		long userAnsUniqueId=Long.parseLong(request.getParameter("useranswerId"));
		long optionUniqueId=Long.parseLong(request.getParameter("optionUniqueId"));
		String strRoomId=request.getParameter("roomId");
		int intReturnVal = 0; long returnUniqueId = 0 ;
		
		if(reqAction=="newpollid"){
			String pollIdXml=PollingService.getPollID();
		}else if(reqAction!=null && reqAction!="" && reqAction.equals("insertpollquestion")){
			returnUniqueId = PollingService.savePollingQuestion(strQuestDesc,strQuestType,intUserId);
			
		}else if(reqAction!=null && reqAction!="" && reqAction.equals("insertpolloptions")){
			returnUniqueId = PollingService.savePollingAnswerOptions(strOptionDesc,longQuestId,intOptionId);
    		 
    	}else if(reqAction!=null && reqAction!="" && reqAction.equals("inseruseranswer")){
    		returnUniqueId = PollingService.savePollingUserAnswer(longQuestId,intOptionId,intUserId,strRoomId);
        	
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("updatepollquestion")){
        	
        	intReturnVal = PollingService.updatePollQuestion(longQuestId,strQuestDesc);
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("updatepolloption")){
        	
        	intReturnVal = PollingService.updatePollAnswerOption(longQuestId,intOptionId,strOptionDesc);
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("updateuseranswer")){
        	
        	intReturnVal =PollingService.updateUserAnswer(userAnsUniqueId,intOptionId,strRoomId);
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("getpollquestions")){
        	
        	String returnXML = PollingService.getPollQuestionsForUser(intUserId);
        	
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("getpolloptions")){
        	List<PollAnswerOptions> ansOptionsList = PollingService.getPollAnswerOptionsForQuestion(longQuestId);
        	
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("getpolluseranswers")){
        	List<PollUserAnswers> usersAnswerList = PollingService.getAllUsersAnswerForQuestion(longQuestId);
        	
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("deletepollquestion")){
        	
        	intReturnVal = PollingService.deletePollQuestion(longQuestId);
        }else if(reqAction!=null && reqAction!="" && reqAction.equals("deletepollansweroptions")){
        	
        	intReturnVal = PollingService.deletePollAnswerOptions(optionUniqueId,longQuestId,intOptionId);
        }
		
		
	}

}
