package com.yino.drudgery.entity;

import java.util.Date;

import com.yino.drudgery.enums.PriorityEnum;

public class Job {
	private String jobId;
	private PriorityEnum priority;
	private Date createTime;
	private JobConfig jobcfg;
	private boolean isNeedOutput;
	private RequestData requestData;
}
