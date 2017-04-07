package com.yino.drudgery.listener;

import com.yino.drudgery.entity.JobConfig;

public interface JobConfigListener {
	void onRemove(JobConfig jobCfg);
	
	void onUpdate(JobConfig jobCfg);
	
	void onAdd(JobConfig jobCfg);
}
