package com.yino.drudgery.utils;

import com.yino.drudgery.entity.CronJobConfig;
import com.yino.drudgery.entity.DependencyJobConfig;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.entity.JobConfigParam;
import com.yino.drudgery.enums.TriggerTypeEnum;
import com.yino.drudgery.po.JobBasicInfo;
import com.yino.drudgery.po.JobParamInfo;

public class PoToBean {

	public static JobConfig ConvertToBean(JobBasicInfo jobBasicInfo) {
		JobConfig jobConfig = null;
		if (jobBasicInfo == null) {
			return null;
		}

		if (jobBasicInfo.getTriggerType() == TriggerTypeEnum.CronTime.getNum()) {
			jobConfig = new CronJobConfig();
			((CronJobConfig) jobConfig).setCronTime(jobBasicInfo.getTriggerParam());
		}
		if (jobBasicInfo.getTriggerType() == TriggerTypeEnum.CronTime.getNum()) {
			jobConfig = new DependencyJobConfig();
			((DependencyJobConfig) jobConfig).setDependantJobName(jobBasicInfo.getTriggerParam());
		} else {
			jobConfig = new JobConfig();
		}

		jobConfig.setBlAllowConcurrent(jobBasicInfo.getIsParallelExe()==1);
		jobConfig.setJobConfigID(jobBasicInfo.getJobBasicUid());
		jobConfig.setJobGroupName(jobBasicInfo.getJobGroup());
		jobConfig.setJobName(jobBasicInfo.getJobName());
		jobConfig.setUsed(jobBasicInfo.getIsEnable() == 1);
		jobConfig.setClassName("drudgery-worker.jar;com.yino.drudgery.jobrunner.StandardJobRunner");

		return jobConfig;
	}

	public static JobConfigParam ConvertToBean(JobParamInfo jobParamInfo) {
		if (jobParamInfo == null) {
			return null;
		}

		JobConfigParam jobConfigParam = new JobConfigParam();
		jobConfigParam.setJobConfigId(jobParamInfo.getJobBasicUid());
		jobConfigParam.setJobConfigParamId(jobParamInfo.getJobParamUid());
		jobConfigParam.setKey(jobParamInfo.getParamKey());
		jobConfigParam.setValue(jobParamInfo.getParamValue());
		jobConfigParam.setParamType(jobParamInfo.getParamType());

		return jobConfigParam;
	}

}
