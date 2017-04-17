package com.yino.drudgery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.enums.JobRunStatusEnum;
import com.yino.drudgery.listener.JobStatusListener;
import com.yino.drudgery.service.JobService;

/**
 * @Description Job服务实现，缓存Job，同时更新Job
 * 
 * @Title: JobServiceImpl.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public class JobServiceImpl implements JobService {
	private final Log log = LogFactory.getLog(this.getClass());
	private ConcurrentHashMap<String, Job> map = new ConcurrentHashMap<String, Job>();

	private List<JobStatusListener> jobStatusListeners;

	public JobServiceImpl() {
		jobStatusListeners = new ArrayList<JobStatusListener>();
	}

	/**
	 * 
	 */
	@Override
	public void addJob(Job job) {
		map.put(job.getJobId(), job);
		fireJobStatusChanged(job);
	}

	@Override
	public void removeJob(Job job) {
		map.remove(job.getJobId());
	}

	@Override
	public void updateJob(Job job) {
		Job oldJob = map.get(job.getJobId());
		if (oldJob == null)
			return;
		Boolean statusChanged = oldJob.getJobRunStatus() != job.getJobRunStatus();
		oldJob.setEndTime(job.getEndTime());
		oldJob.setErrorMessage(job.getErrorMessage());
		oldJob.setJobRunError(job.getJobRunError());
		oldJob.setJobRunStatus(job.getJobRunStatus());
		oldJob.setWorkerId(job.getWorkerId());
		oldJob.setOutputJobData(job.getOutputJobData());
		if (statusChanged) {
			fireJobStatusChanged(oldJob);
		}
	}

	private void fireJobStatusChanged(Job job) {
		for (JobStatusListener listener : jobStatusListeners) {
			if (job.getJobRunStatus() == JobRunStatusEnum.create) {
				listener.onCreate(job);
			} else if (job.getJobRunStatus() == JobRunStatusEnum.start) {
				listener.onStart(job);
			} else if (job.getJobRunStatus() == JobRunStatusEnum.finish) {
				listener.onFinish(job);
			}
		}
	}

	@Override
	public Job getJob(String jobId) {
		return map.get(jobId);
	}

	@Override
	public void addJobStatusListener(JobStatusListener listener) {
		jobStatusListeners.add(listener);
	}

	@Override
	public void removeJobStatusListener(JobStatusListener listener) {
		jobStatusListeners.remove(listener);
	}

	@Override
	public void waitJob(Job job) {
		if (!map.containsKey(job.getJobId())) {
			map.put(job.getJobId(), job);
		}

		synchronized (job) {
			try {
				job.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void notifyJob(Job job) {
		if (job == null)
			return;

		Job job1 = map.get(job.getJobId());
		if (job1 == null) {
			job1 = job;
		}

		synchronized (job1) {
			job1.notify();
		}
	}

}
