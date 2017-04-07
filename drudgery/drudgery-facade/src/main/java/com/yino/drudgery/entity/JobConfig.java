package com.yino.drudgery.entity;

import com.yino.drudgery.enums.PriorityEnum;
import com.yino.drudgery.enums.TriggerTypeEnum;

public class JobConfig {
	protected String jobConfigID;
	protected String jobName;
	protected String jobGroupName;
	protected boolean blAllowConcurrent;
	protected PriorityEnum priority;
	protected boolean isUsed;
	protected String remark;
	protected TriggerTypeEnum triggerType;
	protected String className;
}
