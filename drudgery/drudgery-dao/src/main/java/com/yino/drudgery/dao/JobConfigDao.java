package com.yino.drudgery.dao;

import java.util.List;

import com.yino.drudgery.po.JobBasicInfo;

public interface JobConfigDao {
	int addJobBasicInfo(JobBasicInfo jobBasicInfo);
	
	int delJobBasicInfo(String jobBasicUid);
	
	int updateJobBasicInfo(JobBasicInfo jobBasicInfo);
	
	JobBasicInfo getJobBasicInfoById(String jobBasicUid);
	
	JobBasicInfo getJobBasicInfoByName(String jobConfigName);
	
	List<JobBasicInfo> getAllJobBasicInfos();
	
	
	
}
