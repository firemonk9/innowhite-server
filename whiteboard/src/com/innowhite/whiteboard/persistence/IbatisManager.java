package com.innowhite.whiteboard.persistence;

import java.io.IOException;
import java.io.Reader;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.servlets.CreateSession;

public class IbatisManager {

	private static SqlMapClient sqlMap   = null;
	
	 private static String resource = "ibatis-mappings/SqlMap-config.xml";
	
	 private static Logger log = Red5LoggerFactory.getLogger(IbatisManager.class, "whiteboard");
	 
	public static SqlMapClient getSqlMap() {
		Reader reader;
		
			try {				
				if(sqlMap == null)
				{
				reader = Resources.getResourceAsReader(resource);
				sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
				}
			}
			 catch (IOException e) {
				e.printStackTrace();
			}
			 log.debug("sql map is loaded.");
			 return sqlMap;
		
	}
	
	
	public static void main(String[] args) {
		IbatisManager.getSqlMap();
	}
	
	
}
