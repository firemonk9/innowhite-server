package com.innowhite.whiteboard.persistence;

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisManager {

	private static SqlMapClient sqlMap   = null;
	
	 private static String resource = "ibatis-mappings/SqlMap-config.xml";
	
	 private static final Logger log = LoggerFactory.getLogger(SqlMapClient.class);
	 
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
