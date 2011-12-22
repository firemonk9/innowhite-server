/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.innowhite.PlayBackWebApp;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlayBackWebApp.model.AudioData;
import com.innowhite.PlayBackWebApp.model.CallBackUrlsData;
import com.innowhite.PlayBackWebApp.model.RoomData;
import com.innowhite.PlayBackWebApp.model.WhiteboardData;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    public void testSaveAndFind() throws Exception {
//	Session session = sessionFactory.getCurrentSession();
//	WhiteboardData wb = new WhiteboardData();
//
//	session.save(wb);
//	session.flush();
//	// Otherwise the query returns the existing order (and we didn't set the
//	// parent in the item)...
//	session.clear();
	assertEquals(1, 1);

    }

    @Test
    @Transactional
    public void testAudioSaveAndFind() throws Exception {
//	Session session = sessionFactory.getCurrentSession();
//	AudioData wb = new AudioData();
//	wb.setRoomName("test123");
//	wb.setFilePath("file1" + (int) (Math.random() * 10000));
//	wb.setStartTime(new Date());
//	session.save(wb);
//	session.flush();
//	// Otherwise the query returns the existing order (and we didn't set the
//	// parent in the item)...
//	session.clear();
	

	CallBackUrlsData  val = updateAudioData("room192");
	if(val != null)
	    assertEquals(1, 1);
	else
	    assertEquals(1, 0);
	
	//assertEquals(1, val);
    }

    @Transactional
    private CallBackUrlsData updateAudioData(String roomId) throws Exception {

	Session session = sessionFactory.getCurrentSession();

	Criteria crit = session.createCriteria(RoomData.class);
	@SuppressWarnings("unchecked")
	List<RoomData> list2 = crit.add(Restrictions.eq("roomName", roomId)).list();
	System.err.println(" list2 "+list2);	
	if (list2 != null && list2.size() == 1) {

	    
	    RoomData roomVo = (RoomData) list2.get(0);
	    
	    System.err.println(" roomVo:: "+roomVo);
	    String orgName = roomVo.getOrgName();
	    
	    System.err.println(" orgName:: "+orgName);
	    
	    
	    crit = session.createCriteria(CallBackUrlsData.class);
	    @SuppressWarnings("unchecked")
	    List<CallBackUrlsData> list = crit.add(Restrictions.eq("orgName", orgName)).list();
	    if(list != null && list.size() ==1)
	 	return list.get(0);
	    
	}
	session.clear();
	session.flush();

	return null;
	// /log.debug("");

    }

}
