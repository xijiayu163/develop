package com.yino.drudgery.service.impl.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yino.drudgery.dao.JobConfigDao;
import com.yino.drudgery.po.JobBasicInfo;
import com.yino.util.PrimaryKeyGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring-context.xml"
})
public class JobConfigServiceTest {
	@Autowired
	private JobConfigDao jobConfigDao;
	
	@Test
	public void testAddJobBasicInfo(){
		
//		JobBasicInfo jobBasicInfo = new JobBasicInfo();
//		jobBasicInfo.setJobBasicUid(PrimaryKeyGenerator.generatePrimaryKey());
//		jobBasicInfo.setJobName("jobName1");
//		jobBasicInfo.setIsEnable((byte)0);
//		jobBasicInfo.setIsParallelExe((byte)1);
//		jobBasicInfo.setJobGroup("jobgroup");
//		jobBasicInfo.setJobStatus((byte)1);
//		jobBasicInfo.setJobType((byte)1);
//		jobBasicInfo.setPriority((byte)1);
//		jobBasicInfo.setTriggerParam("xxx");
//		jobBasicInfo.setTriggerType((byte)1);
//		jobBasicInfo.setUpdateTime(new Date());
//		
//		jobConfigDao.addJobBasicInfo(jobBasicInfo );
		
		JobBasicInfo jobBasicInfo = jobConfigDao.getJobBasicInfoById("20170420134747308-f7bffbcfb9d846eea771138bd356a36a");
		System.out.println("ok");
	}
	
}
