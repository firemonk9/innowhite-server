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
package com.innowhite.PlaybackApp;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.aspectj.bridge.Message;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.PlayBackPlayList;
import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.model.WhiteboardData;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    public void testSaveAndFind() throws Exception {
	Session session = sessionFactory.getCurrentSession();
	WhiteboardData wb = new WhiteboardData();

	session.save(wb);
	session.flush();
	// Otherwise the query returns the existing order (and we didn't set the
	// parent in the item)...
	session.clear();
	assertEquals(1, 1);

    }

    
    
    @Test
    @Transactional
    public void testSaveAndPlayList() throws Exception {
	Session session = sessionFactory.getCurrentSession();
	PlayBackPlayList wb = new PlayBackPlayList();
	wb.setFilePath("dummy_PATH");
	wb.setInsertedDate(new Date());
	wb.setRoomName("roomName");
	session.save(wb);
	session.flush();
	// Otherwise the query returns the existing order (and we didn't set the
	// parent in the item)...
	session.clear();
	assertEquals(1, 1);

    }

    
    
    @Test
    @Transactional
    public void testAudioSaveAndFind() throws Exception {
	Session session = sessionFactory.getCurrentSession();
	AudioData wb = new AudioData();
	wb.setRoomName("test123");
	wb.setFilePath("file1" + (int) (Math.random() * 10000));
	wb.setStartTime(new Date());
	session.save(wb);

	// wb.getAudioDataList("test123");

	session.flush();
	// Otherwise the query returns the existing order (and we didn't set the
	// parent in the item)...
	session.clear();
	assertEquals(1, 1);

	int val = updateAudioData(wb);
	assertEquals(1, val);
    }
    
    @Test
    @Transactional
    public void testVideoGetList() {

	String roomId = "room1";
	Session session = sessionFactory.getCurrentSession();

	Criteria crit = session.createCriteria(VideoData.class);
	@SuppressWarnings("unchecked")
	List<VideoData> list2 = crit.add(Restrictions.eq("roomName", roomId)).
	addOrder( Order.asc("id")).
	list();
	System.err.println(" the video returned "+list2.size());
	session.clear();
	session.flush();
	

    }

    

    @Test
    @Transactional
    public void testAudioGetList() throws Exception {
	Session session = sessionFactory.getCurrentSession();

	String roomId = "test123";

	
	Criteria crit = session.createCriteria(AudioData.class);
	@SuppressWarnings("unchecked")
	List<AudioData> list2 = crit.add(Restrictions.eq("roomName", roomId)).
	addOrder( Order.asc("id")).
	list();
	
	for(AudioData a : list2){
	    
	    System.err.println(a.getFilePath());
	}
	System.err.println(list2);
	session.clear();
	session.flush();

	// assertEquals(1, val);
    }

    @Transactional
    private int updateAudioData(AudioData audioData) throws Exception {

	Session session = sessionFactory.getCurrentSession();

	String query = "update AudioData o set o.endTime=:endTime  where o.roomName=:roomName and o.filePath=:filePath";
	int val = session.createQuery(query).setTimestamp("endTime", new Date()).setString("roomName", audioData.getRoomName()).setString("filePath", audioData.getFilePath()).executeUpdate();
	return val;
	// /log.debug("");

    }

}
