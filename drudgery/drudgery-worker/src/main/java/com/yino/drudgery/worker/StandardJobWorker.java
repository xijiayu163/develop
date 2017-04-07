package com.yino.drudgery.worker;

import com.yino.drudgery.Factory.JobRunnerFactory;
import com.yino.drudgery.entity.Job;
import com.yino.drudgery.jobrunner.JobRunner;
import com.yino.drudgery.listener.JobReceiveListener;

public class StandardJobWorker extends JobReceiveListener{
	@Override
	public void doInternal(Job job) {
		JobRunner runner = JobRunnerFactory.createJobRunner(job);
		runner.run();
	}
}
