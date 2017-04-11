package com.yino.drudgery.datasource;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IMultiDataSource {
	public void addDataSource(String dataSourceName, String driverClassName, String hostUrl, String userName, String password);
	
	public void switchDataSource(String dataSourceName);
	
	public JdbcTemplate getJdbcTemplate(String dataSourceName);
	
	public DrudgeryDataSource getDataSource(String dataSourceName);
	
}
