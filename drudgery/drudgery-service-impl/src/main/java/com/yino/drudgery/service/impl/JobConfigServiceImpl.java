package com.yino.drudgery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yino.drudgery.dao.JobConfigDao;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.listener.JobConfigListener;
import com.yino.drudgery.service.JobConfigService;

/**
 * @Description:作业配置服务实现
 * 
 * 
 * @Title: JobConfigServiceImpl.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
@Service
public class JobConfigServiceImpl implements JobConfigService {
	
	@Autowired
	private JobConfigDao jobConfigDao;
	
	private final Log log = LogFactory.getLog(this.getClass());
	private List<JobConfig> jobConfigList;
	
	private List<JobConfigListener> jobConfigListeners;

	public JobConfigServiceImpl() {
		jobConfigListeners = new ArrayList<JobConfigListener>();
		jobConfigList = new ArrayList<JobConfig>();
	}

	@Override
	public JobConfig getJobConfig(String jobName) {
		for (JobConfig jobConfig : jobConfigList) {
			if (jobConfig.getJobName() == jobName) {
				return jobConfig;
			}
		}
		return null;
	}

	@Override
	public void saveJobConfig(JobConfig jobConfig) {
		jobConfigList.add(jobConfig);
		fireJobConfigEvent(jobConfig,1);
	}

	@Override
	public void removeJobConfig(JobConfig jobConfig) {
		JobConfig jobConfig1 = null;
		for (JobConfig jobConfig2 : jobConfigList) {
			if (jobConfig.getJobConfigID() == jobConfig2.getJobConfigID()) {
				jobConfig1 = jobConfig2;
			}
		}

		jobConfigList.remove(jobConfig1);
		fireJobConfigEvent(jobConfig,3);
	}

	@Override
	public List<JobConfig> getAllJobConfig() {
		List<JobConfig> list = new ArrayList<JobConfig>();
		list.addAll(jobConfigList);
		return list;
	}

	@Override
	public List<JobConfig> getDependencyJobConfigs(String dependencyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addJobConfigListener(JobConfigListener listener) {
		jobConfigListeners.add(listener);
	}

	@Override
	public void removeJobConfigListener(JobConfigListener listener) {
		jobConfigListeners.remove(listener);
	}

	private void fireJobConfigEvent(JobConfig jobConfig, int action) {
		for (JobConfigListener linstener : jobConfigListeners) {
			switch (action) {
			case 1:
				linstener.onAdd(jobConfig);
				break;
			case 2:
				linstener.onUpdate(jobConfig);
				break;
			case 3:
				linstener.onRemove(jobConfig);
				break;
			default:
				break;

			}
		}
	}
}
