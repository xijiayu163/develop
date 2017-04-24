package com.yino.drudgery.utils;

import java.util.Date;

import com.yino.drudgery.entity.CronJobConfig;
import com.yino.drudgery.entity.DependencyJobConfig;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.entity.JobConfigParam;
import com.yino.drudgery.po.JobBasicInfo;
import com.yino.drudgery.po.JobParamInfo;

public class BeanToPo {

	public static JobBasicInfo ConvertToPo(JobConfig jobConfig) {
		JobBasicInfo jobBasicInfo = null;
		if (jobConfig == null) {
			return null;
		}

		String triggerParam = null;
		if (jobConfig instanceof CronJobConfig) {
			triggerParam = ((CronJobConfig) jobConfig).getCronTime();
		} else if (jobConfig instanceof DependencyJobConfig) {
			triggerParam = ((DependencyJobConfig) jobConfig).getDependantJobName();
		}

		jobBasicInfo = new JobBasicInfo();
		jobBasicInfo.setIsEnable((byte) (jobConfig.isUsed() ? 1 : 0));
		jobBasicInfo.setIsParallelExe((byte)(jobConfig.isBlAllowConcurrent()?1:0));
		jobBasicInfo.setJobBasicUid(jobConfig.getJobConfigID());
		jobBasicInfo.setJobGroup(jobConfig.getJobGroupName());
		jobBasicInfo.setJobName(jobConfig.getJobName());
		jobBasicInfo.setJobType((byte) 0);
		jobBasicInfo.setPriority((byte) 0);
		jobBasicInfo.setTriggerType((byte) (jobConfig.getTriggerType().getNum()));
		jobBasicInfo.setTriggerParam(triggerParam);
		jobBasicInfo.setUpdateTime(new Date());
		return jobBasicInfo;
	}

	public static JobParamInfo ConvertToPo(JobConfigParam jobParam) {
		if (jobParam == null) {
			return null;
		}

		JobParamInfo jobParamInfo = new JobParamInfo();
		jobParamInfo.setJobBasicUid(jobParam.getJobConfigId());
		jobParamInfo.setJobParamUid(jobParam.getJobConfigParamId());
		jobParamInfo.setParamKey(jobParam.getKey());
		jobParamInfo.setParamType(jobParam.getParamType());
		jobParamInfo.setParamValue(jobParam.getValue());
		jobParamInfo.setUpdateTime(new Date());

		return jobParamInfo;
	}

}
