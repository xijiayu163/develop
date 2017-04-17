package com.yino.drudgery.entity;

import java.util.Date;

import com.yino.drudgery.enums.JobRunErrorEnum;
import com.yino.drudgery.enums.JobRunStatusEnum;
import com.yino.drudgery.enums.JobPriorityEnum;

public class Job {
	private String jobId;
	private JobPriorityEnum priority;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private JobConfig jobcfg;
	private String workerId;
	private boolean isNeedOutput;
	private JobRunStatusEnum jobRunStatus;
	private JobRunErrorEnum jobRunError;
	private String errorMessage ;
	private JobData inputJobData;
	private JobData outputJobData;
	
	
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public JobPriorityEnum getPriority() {
		return priority;
	}
	public void setPriority(JobPriorityEnum priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public JobConfig getJobcfg() {
		return jobcfg;
	}
	public void setJobcfg(JobConfig jobcfg) {
		this.jobcfg = jobcfg;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public boolean isNeedOutput() {
		return isNeedOutput;
	}
	public void setNeedOutput(boolean isNeedOutput) {
		this.isNeedOutput = isNeedOutput;
	}
	public JobRunStatusEnum getJobRunStatus() {
		return jobRunStatus;
	}
	public void setJobRunStatus(JobRunStatusEnum jobRunStatus) {
		this.jobRunStatus = jobRunStatus;
	}
	public JobRunErrorEnum getJobRunError() {
		return jobRunError;
	}
	public void setJobRunError(JobRunErrorEnum jobRunError) {
		this.jobRunError = jobRunError;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public JobData getInputJobData() {
		return inputJobData;
	}
	public void setInputJobData(JobData inputJobData) {
		this.inputJobData = inputJobData;
	}
	public JobData getOutputJobData() {
		return outputJobData;
	}
	public void setOutputJobData(JobData outputJobData) {
		this.outputJobData = outputJobData;
	}

	
}
