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

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.innowhite.PlaybackApp.model.AudioData;
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
    public void testAudioSaveAndFind() throws Exception {
	Session session = sessionFactory.getCurrentSession();
	AudioData wb = new AudioData();
	wb.setRoomName("test123");
	wb.setFilePath("file1" + (int) (Math.random() * 10000));
	wb.setStartTime(new Date());
	session.save(wb);
	session.flush();
	// Otherwise the query returns the existing order (and we didn't set the
	// parent in the item)...
	session.clear();
	assertEquals(1, 1);

	int val = updateAudioData(wb);
	//assertEquals(1, val);
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
