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
package com.innowhite.loadBalancer.domain;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Session session = sessionFactory.getCurrentSession();

		Server ser = new Server();

		session.save(ser);
		session.flush();
		assertNotNull(ser.getId());

	}

	@Test
	@Transactional
	public void testServersList() throws Exception {
		Session session = sessionFactory.getCurrentSession();

		Query getByLastName = session
				.createQuery(" from Server as s where s.serverActive!=:var and s.orgName =: orgName");

		getByLastName.setBoolean("var", false);
		getByLastName.setString("orgName", null);

		List<Server> servers = (List<Server>) getByLastName.list();

		session.flush();

		assertNotNull(servers);

	}

}
