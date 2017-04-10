package com.yino.drudgery.datasource.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yino.drudgery.datasource.MultiDatasource;




public class MultiDatasourceTest{
	@Autowired
	private MultiDatasource multiDatasource;
	
	
	@Test
	public void AddDataSourceTest(){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		context.start();
		
		String datasourceName = "mysql";
		String driverClassName="com.mysql.jdbc.Driver";
		String hostUrl="jdbc:mysql://192.168.1.18:3306/mimsp_nw_dev?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true&amp;failOverReadOnly=false";
		String userName="root";
		String password="Szyino123";
		System.out.println("xxxx");
//		multiDatasource.addDataSource(datasourceName , driverClassName, hostUrl, userName, password);
	}
}
