package com.innowhite.loadBalancer.domain;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ServerDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<Server> getActiveServers(String orgName) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query getByLastName = session
				.createQuery(" from Server as s where s.serverActive!=:serverActive and s.orgName =: orgName");

		getByLastName.setBoolean("serverActive", false);
		getByLastName.setString("orgName", orgName);
		
		List<Server> servers = (List<Server>) getByLastName.list();

		session.flush();
		return servers;

	}

}
