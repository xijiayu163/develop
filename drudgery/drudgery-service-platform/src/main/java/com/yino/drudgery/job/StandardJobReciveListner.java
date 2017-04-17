package com.yino.drudgery.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.listener.JobReceiveListener;
import com.yino.drudgery.service.impl.ServiceImpls;


/**
 * @Description:平台接收Job更新监听
 * 
 * 
 * @Title: StandardJobReciveListner.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public class StandardJobReciveListner extends JobReceiveListener {

	private final Log log = LogFactory.getLog(this.getClass());
	
	private ServiceImpls serviceImpls;

	public StandardJobReciveListner(ServiceImpls serviceImpls) {
		this.serviceImpls = serviceImpls;
		serviceImpls.getMsgService().addListener(this);
	}

	@Override
	public void doInternal(Job job) {
		// TODO Auto-generated method stub
		serviceImpls.getJobService().updateJob(job);
	}

}
