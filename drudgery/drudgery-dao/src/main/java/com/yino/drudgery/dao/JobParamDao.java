package com.yino.drudgery.dao;

import java.util.List;

import com.yino.drudgery.po.JobParamInfo;

public interface JobParamDao {
	int addJobParamInfo(JobParamInfo jobParamInfo);
	
	int addJobParams(List<JobParamInfo> jobParams);
	
	int delJobParam(String jobParamUid);
	
	int delJobParamsByJobBasicUid(String jobBasicUid);
	
	int updateJobParamInfo(JobParamInfo jobParamInfo);
	
	JobParamInfo getJobParamInfo(String jobParamUid);
	
	List<JobParamInfo> getJobParamInfosByJobBasicUid(String jobBasicUid);
}
