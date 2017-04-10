package com.yino.drudgery.entity;

public class DependencyJobConfig extends JobConfig{
	private String dependantJobName;

	public String getDependantJobName() {
		return dependantJobName;
	}

	public void setDependantJobName(String dependantJobName) {
		this.dependantJobName = dependantJobName;
	}
}
