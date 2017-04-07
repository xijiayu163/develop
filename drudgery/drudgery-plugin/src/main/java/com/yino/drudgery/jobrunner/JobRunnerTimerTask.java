package com.yino.drudgery.jobrunner;

import java.util.TimerTask;


/**
 * 
 * @author Wxb
 *
 */
public class JobRunnerTimerTask extends TimerTask {

	private JobRunner runner =null;
	/**
	 * 
	 * @param runner
	 */
	public JobRunnerTimerTask(JobRunner runner)
	{
		setRunner(runner);
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		if(runner!=null)
		{
			runner.sendJobMsg();
		}
	}

	/**
	 * @return the runner
	 */
	public JobRunner getRunner() {
		return runner;
	}

	/**
	 * @param runner the runner to set
	 */
	public void setRunner(JobRunner runner) {
		this.runner = runner;
	}

}
