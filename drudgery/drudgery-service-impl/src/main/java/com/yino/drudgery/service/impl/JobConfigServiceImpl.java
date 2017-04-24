package com.yino.drudgery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yino.drudgery.dao.JobConfigDao;
import com.yino.drudgery.dao.JobParamDao;
import com.yino.drudgery.entity.CronJobConfig;
import com.yino.drudgery.entity.DependencyJobConfig;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.entity.JobConfigParam;
import com.yino.drudgery.enums.JobPriorityEnum;
import com.yino.drudgery.enums.TriggerTypeEnum;
import com.yino.drudgery.listener.JobConfigListener;
import com.yino.drudgery.po.JobBasicInfo;
import com.yino.drudgery.po.JobParamInfo;
import com.yino.drudgery.service.JobConfigService;
import com.yino.drudgery.utils.PoToBean;

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
public class JobConfigServiceImpl implements JobConfigService {
	private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private JobConfigDao jobConfigDao;
	@Autowired
	private JobParamDao jobParamDao;

	private List<JobConfigListener> jobConfigListeners;

	public JobConfigServiceImpl() {
		jobConfigListeners = new ArrayList<JobConfigListener>();
	}

	
	
	@Override
	public JobConfig getJobConfig(String jobName) {

		JobBasicInfo jobBasicInfo = jobConfigDao.getJobBasicInfoByName(jobName);
		if (jobBasicInfo == null) {
			return null;
		}

		JobConfig jobConfig = PoToBean.ConvertToBean(jobBasicInfo);
		List<JobConfigParam> paramList = new ArrayList<JobConfigParam>();
		List<JobParamInfo> paramInfoList = jobParamDao.getJobParamInfosByJobBasicUid(jobBasicInfo.getJobBasicUid());
		for (JobParamInfo jobParamInfo : paramInfoList) {
			JobConfigParam jobConfigParam = PoToBean.ConvertToBean(jobParamInfo);
			paramList.add(jobConfigParam);

		}

		jobConfig.setJobConfigParams(paramList);
		return jobConfig;

	}

	
	//新增与更新
	@Override
	public void saveJobConfig(JobConfig jobConfig) {
		
        JobBasicInfo jobBasicInfo=jobConfigDao.getJobBasicInfoById(jobConfig.getJobConfigID());
        if(jobBasicInfo!=null)
        {
        	jobConfigDao.updateJobBasicInfo(jobBasicInfo);
        	fireJobConfigEvent(jobConfig, 2);
        }
        else
        {
        	jobConfigDao.addJobBasicInfo(jobBasicInfo);
        	fireJobConfigEvent(jobConfig, 1);
        }
	}

	@Override
	public void removeJobConfig(JobConfig jobConfig) {
		if(jobConfig==null)
		{
			return ;
		}
		
		jobConfigDao.delJobBasicInfo(jobConfig.getJobConfigID());
		jobParamDao.delJobParamsByJobBasicUid(jobConfig.getJobConfigID());
				
		fireJobConfigEvent(jobConfig, 3);
	}

	@Override
	public List<JobConfig> getAllJobConfig() {
		List<JobConfig> list = new ArrayList<JobConfig>();
		List<JobBasicInfo> jobBasicInfoList = jobConfigDao.getAllJobBasicInfos();
		for(JobBasicInfo jobBasicInfo:jobBasicInfoList)
		{
			JobConfig jobConfig = PoToBean.ConvertToBean(jobBasicInfo);
			List<JobConfigParam> paramList = new ArrayList<JobConfigParam>();
			List<JobParamInfo> paramInfoList = jobParamDao.getJobParamInfosByJobBasicUid(jobBasicInfo.getJobBasicUid());
			for (JobParamInfo jobParamInfo : paramInfoList) {
				JobConfigParam jobConfigParam = PoToBean.ConvertToBean(jobParamInfo);
				paramList.add(jobConfigParam);

			}
			jobConfig.setJobConfigParams(paramList);
			list.add(jobConfig);
		}

		
		return list;
	}

	@Override
	public List<JobConfig> getDependencyJobConfigs(String dependencyName) {
		List<JobConfig> list = new ArrayList<JobConfig>();
		List<JobBasicInfo> jobBasicInfoList = jobConfigDao.getAllJobBasicInfos();
		for(JobBasicInfo jobBasicInfo:jobBasicInfoList)
		{
			JobConfig jobConfig = PoToBean.ConvertToBean(jobBasicInfo);
			if(!(jobConfig instanceof DependencyJobConfig)||
					((DependencyJobConfig)jobConfig).getDependantJobName()!=dependencyName)
			{
				continue;
			}
			
			List<JobConfigParam> paramList = new ArrayList<JobConfigParam>();
			List<JobParamInfo> paramInfoList = jobParamDao.getJobParamInfosByJobBasicUid(jobBasicInfo.getJobBasicUid());
			for (JobParamInfo jobParamInfo : paramInfoList) {
				JobConfigParam jobConfigParam = PoToBean.ConvertToBean(jobParamInfo);
				paramList.add(jobConfigParam);

			}
			jobConfig.setJobConfigParams(paramList);
			list.add(jobConfig);
		}

		
		return list;
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
