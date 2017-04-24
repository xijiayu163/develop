package com.yino.drudgery.datasource.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yino.drudgery.datasource.IMultiDataSource;
import com.yino.drudgery.test.SpringTestCase;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "classpath:spring-context.xml"
//})
public class MultiDatasourceTest extends SpringTestCase{
	@Autowired
	private IMultiDataSource multiDatasource;
	
	@Test
	public void AddDataSourceTest(){
		String datasourceName = "mysql-debug";
		String driverClassName="com.mysql.jdbc.Driver";
		String hostUrl="jdbc:mysql://192.168.1.18:3306/mimsp_nw_dev?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true&amp;failOverReadOnly=false";
		String userName="root";
		String password="Szyino123";
		multiDatasource.addDataSource(datasourceName , driverClassName, hostUrl, userName, password);
		multiDatasource.switchDataSource(datasourceName);
		JdbcTemplate jdbcTemplate = multiDatasource.getJdbcTemplate(datasourceName);
		@SuppressWarnings("unused")
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList("SELECT * FROM platform_order");
		System.out.println("ok1");
		
		datasourceName = "mysql-nw_test";
		driverClassName="com.mysql.jdbc.Driver";
		hostUrl="jdbc:mysql://192.168.1.18:3306/mimsp_test?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;failOverReadOnly=false";
		userName="root";
		password="Szyino123";
		multiDatasource.addDataSource(datasourceName , driverClassName, hostUrl, userName, password);
		multiDatasource.switchDataSource(datasourceName);
		jdbcTemplate = multiDatasource.getJdbcTemplate(datasourceName);
		queryForList = jdbcTemplate.queryForList("SELECT * FROM platform_order");
		System.out.println("ok2");
		
		datasourceName = "sqlserver";
		driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		hostUrl="jdbc:sqlserver://SERVER-DB\\SQL2008;databaseName=RTPACS_SF";
		userName="yndeve";
		password="szyino123";
		multiDatasource.addDataSource(datasourceName , driverClassName, hostUrl, userName, password);
		multiDatasource.switchDataSource(datasourceName);
		jdbcTemplate = multiDatasource.getJdbcTemplate(datasourceName);
		queryForList = jdbcTemplate.queryForList("SELECT p.*,s.StudyUID from G_Studys s LEFT JOIN G_Patients p on s.PatientUID=p.PatientUID");
		System.out.println("ok3");
	}
}
