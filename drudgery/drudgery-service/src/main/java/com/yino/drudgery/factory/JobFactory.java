package com.yino.drudgery.factory;

import java.util.Date;
import java.util.UUID;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.enums.JobRunErrorEnum;
import com.yino.drudgery.enums.JobRunStatusEnum;

public class JobFactory {
	public static Job createJob(JobConfig jobConfig){
		Job job = new Job();
		job.setJobcfg(jobConfig);
		job.setJobId(getUUID());
		job.setCreateTime(new Date());
		job.setJobRunError(JobRunErrorEnum.none);
		job.setJobRunStatus(JobRunStatusEnum.create);
		return job;
	}
	
	/** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
}
