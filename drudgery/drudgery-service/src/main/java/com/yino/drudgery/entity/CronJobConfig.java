package com.yino.drudgery.entity;

public class CronJobConfig extends JobConfig{
	private String cronTime;

	public String getCronTime() {
		return cronTime;
	}

	public void setCronTime(String cronTime) {
		this.cronTime = cronTime;
	}
}
