package com.yino.drudgery.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.entity.RequestData;
import com.yino.drudgery.factory.JobFactory;
import com.yino.drudgery.factory.RequestDataFactory;
import com.yino.drudgery.mq.service.MessageService;
import com.yino.drudgery.service.JobConfigService;
import com.yino.drudgery.service.JobService;
import com.yino.drudgery.service.SyncDataService;

public class SyncDataServiceImpl implements SyncDataService {
	private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private JobConfigService jobConfigService;
	@Autowired
	private MessageService msgService;
	@Autowired
	private JobService jobService;

	@Override
	public String syncData(String jobName) {
		return syncData(jobName, null);
	}

	@Override
	public String syncData(String jobName, String inputData) {
		JobConfig jobConfig = jobConfigService.getJobConfig(jobName);
		if (jobConfig == null) {
			return String.format("请求错误，没有名为【{0}】的作业配置！", jobName);
		}

		RequestData requestData = RequestDataFactory.createRequestData(jobName, inputData);
		Job job = JobFactory.createJob(jobConfig, requestData);
		msgService.sendMsg(job);
		jobService.addJob(job);

		return "请求成功！";
	}

}
