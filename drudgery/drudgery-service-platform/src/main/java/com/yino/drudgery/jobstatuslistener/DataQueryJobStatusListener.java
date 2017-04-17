package com.yino.drudgery.jobstatuslistener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yino.drudgery.entity.DataSyncRequestData;
import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.QueryRequestData;
import com.yino.drudgery.entity.RequestData;
import com.yino.drudgery.entity.RequestJob;
import com.yino.drudgery.listener.JobStatusListener;
import com.yino.drudgery.service.impl.ServiceImpls;
import com.yino.util.HttpSendUtil;

public class DataQueryJobStatusListener implements JobStatusListener {

	private final Log log = LogFactory.getLog(this.getClass());

	private ServiceImpls serviceImpls;

	public DataQueryJobStatusListener(ServiceImpls serviceImpls) {
		this.serviceImpls = serviceImpls;
		this.serviceImpls.getJobService().addJobStatusListener(this);
	}

	@Override
	public void onCreate(Job job) {
		// Do nothing
	}

	@Override
	public void onStart(Job job) {
		// Do nothing
	}

	@Override
	public void onFinish(Job job) {
		if (job == null || !(job instanceof RequestJob)) {
			return;
		}

		RequestJob requestJob = (RequestJob) job;
		if (requestJob.getRequestData() == null) {
			return;
		}

		RequestData data = requestJob.getRequestData();
		if (data instanceof QueryRequestData) {
			QueryRequestData qRequest = (QueryRequestData) data;
			if (qRequest.isAsync()) {
				HttpSendUtil.sendPost(data.getCallbackUrl(), job.getOutputJobData().getJsonData());
			} else {
				serviceImpls.getJobService().notifyJob(requestJob);
			}
		} else if (data instanceof DataSyncRequestData) {

		}

	}

}
