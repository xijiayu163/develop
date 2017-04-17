package com.yino.drudgery.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yino.drudgery.mq.service.MessageService;
import com.yino.drudgery.service.JobConfigService;
import com.yino.drudgery.service.JobService;

/**
 * @Description 服务实现集合
 * @Title: ServiceImpls.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public class ServiceImpls {

	private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private JobConfigService  jobConfigService ;
	@Autowired
	private MessageService msgService;
	@Autowired
	private JobService jobService;
	
	
	public JobConfigService getJobConfigService() {
		return jobConfigService;
	}
	public MessageService getMsgService() {
		return msgService;
	}
	public JobService getJobService() {
		return jobService;
	}
}
