package com.yino.drudgery.entity;

import java.util.Date;

import com.yino.drudgery.enums.JobExecuteErrorStatusEnum;
import com.yino.drudgery.enums.JobExecuteStatusEnum;

public class JobResult {
	private String resourceId;
	private String version;
	private String jsonData;
	private Job job;
	private JobExecuteStatusEnum status;
	private JobExecuteErrorStatusEnum errorStatus;
	private String errorMsg;
	private Date createTime;
	private Date startTime;
	private Date finishTime;
	private String workId;
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public JobExecuteStatusEnum getStatus() {
		return status;
	}
	public void setStatus(JobExecuteStatusEnum status) {
		this.status = status;
	}
	public JobExecuteErrorStatusEnum getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(JobExecuteErrorStatusEnum errorStatus) {
		this.errorStatus = errorStatus;
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
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
