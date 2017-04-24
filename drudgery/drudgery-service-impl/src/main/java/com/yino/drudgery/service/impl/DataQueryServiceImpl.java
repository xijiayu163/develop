package com.yino.drudgery.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.entity.RequestData;
import com.yino.drudgery.enums.JobRunStatusEnum;
import com.yino.drudgery.factory.JobFactory;
import com.yino.drudgery.factory.RequestDataFactory;
import com.yino.drudgery.mq.service.MessageService;
import com.yino.drudgery.service.DataQueryService;
import com.yino.drudgery.service.JobConfigService;
import com.yino.drudgery.service.JobService;

/**
 * @Description:数据查询服务实现
 * 
 * 
 * @Title: DataQueryServiceImpl.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public class DataQueryServiceImpl implements DataQueryService {
	private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private JobConfigService jobConfigService;
	@Autowired
	private MessageService msgService;
	@Autowired
	private JobService jobService;

	public synchronized String Query(String jobName, Map<String, String> map) {
		JobConfig jobConfig = jobConfigService.getJobConfig(jobName);
		if (jobConfig == null ||!jobConfig.isUsed()) {
			return String.format("请求错误，没有名为【{0}】的作业配置！", jobName);
		}

		RequestData requestData = RequestDataFactory.createRequestData(jobName, null, map);

		Job job = JobFactory.createJob(jobConfig, requestData);
		msgService.sendMsg(job);
		jobService.addJob(job);
		
		
		jobService.waitJob(job);//同步等待此job被执行完成

		if (job.getJobRunStatus() != JobRunStatusEnum.finish) {
			return "请求发生异常，异常信息：" + job.getErrorMessage();
		}

		if (job.getJobRunError() == null) {
			return job.getOutputJobData().getJsonData();
		} else {
			return job.getErrorMessage();
		}
	}

	public String AsyncQuery(String jobName, String callbackUrl, Map<String, String> map) {
		JobConfig jobConfig = jobConfigService.getJobConfig(jobName);
		if (jobConfig == null || !jobConfig.isUsed()) {
			return String.format("请求错误，没有名为【{0}】的作业配置！", jobName);
		}

		RequestData requestData = RequestDataFactory.createRequestData(jobName, callbackUrl, map);
		Job job = JobFactory.createJob(jobConfig, requestData);
		msgService.sendMsg(job);
		jobService.addJob(job);
		
		return "请求成功！";
	}

}
