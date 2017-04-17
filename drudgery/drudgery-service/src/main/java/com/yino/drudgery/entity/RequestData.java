package com.yino.drudgery.entity;

public class RequestData {
	private boolean isAsync;
	private String jobName;
	private String callbackUrl;
	public boolean isAsync() {
		return isAsync;
	}
	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
}	
