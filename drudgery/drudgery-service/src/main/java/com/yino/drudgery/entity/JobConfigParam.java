package com.yino.drudgery.entity;

public class JobConfigParam {

	private String jobConfigId;
	private String jobConfigParamId;
	private String paramType;
	private String key;
	private String value;
	public String getJobConfigId() {
		return jobConfigId;
	}
	public void setJobConfigId(String jobConfigId) {
		this.jobConfigId = jobConfigId;
	}
	public String getJobConfigParamId() {
		return jobConfigParamId;
	}
	public void setJobConfigParamId(String jobConfigParamId) {
		this.jobConfigParamId = jobConfigParamId;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
