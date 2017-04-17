package com.yino.drudgery.service;

import java.util.Map;

public interface DataQueryService {
	
	String Query(String jobName, Map<String, String> map);
	
	String AsyncQuery(String jobName, String callbackUrl, Map<String, String> map);
	
}
