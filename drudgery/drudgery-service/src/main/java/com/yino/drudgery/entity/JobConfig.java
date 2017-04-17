package com.yino.drudgery.entity;

import java.util.List;

import com.yino.drudgery.enums.JobPriorityEnum;
import com.yino.drudgery.enums.TriggerTypeEnum;

public class JobConfig {
	private  String jobConfigID;
	private String jobName;
	private String jobGroupName;
	private boolean blAllowConcurrent;
	private JobPriorityEnum priority;
	private boolean isUsed;
	private String remark;
	private TriggerTypeEnum triggerType;
	private String className;
	private List<JobConfigParam> jobConfigParams;
	public String getJobConfigID() {
		return jobConfigID;
	}
	public void setJobConfigID(String jobConfigID) {
		this.jobConfigID = jobConfigID;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public boolean isBlAllowConcurrent() {
		return blAllowConcurrent;
	}
	public void setBlAllowConcurrent(boolean blAllowConcurrent) {
		this.blAllowConcurrent = blAllowConcurrent;
	}
	public JobPriorityEnum getPriority() {
		return priority;
	}
	public void setPriority(JobPriorityEnum priority) {
		this.priority = priority;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TriggerTypeEnum getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(TriggerTypeEnum triggerType) {
		this.triggerType = triggerType;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<JobConfigParam> getJobConfigParams() {
		return jobConfigParams;
	}
	public void setJobConfigParams(List<JobConfigParam> jobConfigParams) {
		this.jobConfigParams = jobConfigParams;
	}
	
	
}
