package com.yino.drudgery.entity;

import java.util.Map;

public class QueryRequestData extends RequestData {
	private Map<String, String> queryParams;

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
}
