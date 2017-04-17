package com.yino.drudgery.mq.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.test.Apptest;
import com.yino.drudgery.entity.Job;
import com.yino.drudgery.listener.JobReceiveListener;

public class Receiver extends JobReceiveListener
{

	private static final Log log = LogFactory.getLog(Apptest.class);
	@Override
	public void doInternal(Job job) {
		// TODO Auto-generated method stub
		log.error("==>MQ context:"+ job.getJobId());
	}

	
}
