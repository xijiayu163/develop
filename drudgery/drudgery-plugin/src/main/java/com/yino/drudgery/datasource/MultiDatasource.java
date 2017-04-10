package com.yino.drudgery.datasource;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MultiDatasource {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Map<String,DrudgeryDataSource> dataSources = new HashMap<>();;
	
	public void addDataSource(String datasourceName,String driverClassName,String hostUrl,String userName,String password){
		DrudgeryDataSource dataSource = new DrudgeryDataSource();
		dataSource.setName(datasourceName);
		
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(hostUrl);
		basicDataSource.setUsername(userName);
		basicDataSource.setPassword(password);
		
		basicDataSource.setMaxTotal(200);
		basicDataSource.setInitialSize(5);
		basicDataSource.setMaxWaitMillis(60000);
		basicDataSource.setMaxIdle(20);
		basicDataSource.setMinIdle(3);
		basicDataSource.setRemoveAbandonedTimeout(180);
		
		dataSource.setBasicDataSource(basicDataSource);
		
		dataSources.put(datasourceName, dataSource);
	}
	
	public void switchDataSource(String datasourceName){
		if(dataSources.containsKey(datasourceName)){
			DrudgeryDataSource drudgeryDataSource = dataSources.get(datasourceName);
			jdbcTemplate.setDataSource(drudgeryDataSource.getBasicDataSource());
		}
	}
	
}
	