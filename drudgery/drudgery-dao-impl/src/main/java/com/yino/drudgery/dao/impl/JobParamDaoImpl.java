package com.yino.drudgery.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yino.drudgery.dao.JobParamDao;
import com.yino.drudgery.mapper.JobParamInfoMapper;
import com.yino.drudgery.po.JobParamInfo;
import com.yino.drudgery.po.JobParamInfoExample;
import com.yino.util.StringUtil;

@Repository
public class JobParamDaoImpl implements JobParamDao{
	
	@Autowired
	private JobParamInfoMapper JobParamInfoMapper;

	@Override
	public int addJobParamInfo(JobParamInfo jobParamInfo) {
		if(jobParamInfo==null || StringUtil.isEmpty(jobParamInfo.getJobParamUid()) 
				|| StringUtil.isEmpty(jobParamInfo.getJobBasicUid())){
			return 0;
		}
		
		return JobParamInfoMapper.insertSelective(jobParamInfo);
	}

	@Override
	public int addJobParams(List<JobParamInfo> jobParams) {
		for(JobParamInfo jobParamInfo:jobParams){
			addJobParamInfo(jobParamInfo);
		}
		return 1;
	}

	@Override
	public int delJobParam(String jobParamUid) {
		if(StringUtil.isEmpty(jobParamUid)){
			return 0;
		}
		
		return JobParamInfoMapper.deleteByPrimaryKey(jobParamUid);
	}

	@Override
	public int delJobParamsByJobBasicUid(String jobBasicUid) {
		if(StringUtil.isEmpty(jobBasicUid)){
			return 0;
		}
		
		JobParamInfoExample example = new JobParamInfoExample();
		example.createCriteria().andJobBasicUidEqualTo(jobBasicUid);
		
		return JobParamInfoMapper.deleteByExample(example);
	}

	@Override
	public int updateJobParamInfo(JobParamInfo jobParamInfo) {
		if(jobParamInfo==null || StringUtil.isEmpty(jobParamInfo.getJobParamUid()) 
				|| StringUtil.isEmpty(jobParamInfo.getJobBasicUid())){
			return 0;
		}
		
		return JobParamInfoMapper.updateByPrimaryKeySelective(jobParamInfo);
	}

	@Override
	public JobParamInfo getJobParamInfo(String jobParamUid) {
		if(StringUtil.isEmpty(jobParamUid)){
			return null;
		}
		
		return JobParamInfoMapper.selectByPrimaryKey(jobParamUid);
	}

	@Override
	public List<JobParamInfo> getJobParamInfosByJobBasicUid(String jobBasicUid) {
		if(StringUtil.isEmpty(jobBasicUid)){
			return new ArrayList<>();
		}
		
		JobParamInfoExample example = new JobParamInfoExample();
		example.createCriteria().andJobBasicUidEqualTo(jobBasicUid);
		
		return JobParamInfoMapper.selectByExample(example);
	}
}
