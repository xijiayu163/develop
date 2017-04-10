package com.yino.drudgery.entity;

import java.util.Date;

import com.yino.drudgery.enums.ResultEnum;

public class JobData {
	private String resourceId;
	private String version;
	private String jsonData;
	private ResultEnum status;
	private String ErrorMessage;

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

	public ResultEnum getStatus() {
		return status;
	}

	public void setStatus(ResultEnum status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

}
