package com.yino.drudgery.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.yino.drudgery.dao.JobConfigDao;
import com.yino.drudgery.mapper.JobBasicInfoMapper;
import com.yino.drudgery.po.JobBasicInfo;
import com.yino.drudgery.po.JobBasicInfoExample;
import com.yino.util.StringUtil;

@Repository
public class JobConfigDaoImpl implements JobConfigDao{

	@Autowired
	private JobBasicInfoMapper JobBasicInfoMapper;
	
	@Override
	public int addJobBasicInfo(JobBasicInfo jobBasicInfo) {
		if(jobBasicInfo==null || StringUtil.isEmpty(jobBasicInfo.getJobBasicUid())){
			return 0;
		}
		
		return JobBasicInfoMapper.insertSelective(jobBasicInfo);
	}

	@Override
	public int delJobBasicInfo(String jobBasicUid) {
		if(StringUtil.isEmpty(jobBasicUid)){
			return 0;
		}
		
		return JobBasicInfoMapper.deleteByPrimaryKey(jobBasicUid);
	}

	@Override
	public int updateJobBasicInfo(JobBasicInfo jobBasicInfo) {
		if(jobBasicInfo==null || StringUtil.isEmpty(jobBasicInfo.getJobBasicUid())){
			return 0;
		}
		
		return JobBasicInfoMapper.updateByPrimaryKeySelective(jobBasicInfo);
	}

	@Override
	public JobBasicInfo getJobBasicInfoById(String jobBasicUid) {
		if(StringUtil.isEmpty(jobBasicUid)){
			return null;
		}
		
		return JobBasicInfoMapper.selectByPrimaryKey(jobBasicUid);
	}

	@Override
	public List<JobBasicInfo> getAllJobBasicInfos() {
		return  JobBasicInfoMapper.selectByExample(null);
	}

	@Override
	public JobBasicInfo getJobBasicInfoByName(String jobConfigName) {
		if(StringUtil.isEmpty(jobConfigName)){
			return null;
		}
		
		JobBasicInfoExample example = new JobBasicInfoExample();
		example.createCriteria().andJobNameEqualTo(jobConfigName);
		
		 List<JobBasicInfo> jobBasicInfos = JobBasicInfoMapper.selectByExample(example);
		 if(CollectionUtils.isEmpty(jobBasicInfos)){
			 return null;
		 }
		 
		 return jobBasicInfos.get(0);
	}
}
