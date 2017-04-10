package com.yino.drudgery.jobrunner;

import java.util.Date;
import java.util.Timer;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.enums.JobRunErrorEnum;
import com.yino.drudgery.enums.JobRunStatusEnum;
import com.yino.drudgery.mq.service.MessageService;

/**
 * @Description 作业执行类，抽象类
 * 
 * @Copyright:
 * 
 * @author Wxb
 * @date
 * @version
 *
 */
public abstract class JobRunner implements Runnable {

	protected Timer timer = new Timer();;

	private Job job = null;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	protected MessageService msgService;

	/**
	 * 
	 * @param job
	 */
	public JobRunner() {

	}

	/**
	 * @Description 执行前初始化
	 * @throws Exception
	 */
	public abstract void initParams() ;

	/**
	 * @Description 作业处理
	 * @throws Exception
	 */
	public abstract void doWork();

	/**
	 * @Description 发送作业更新
	 */
	public void sendJobMsg() {
		msgService.sendMsg(job);
	}


	public MessageService getMsgService() {
		return msgService;
	}

	public void setMsgService(MessageService msgService) {
		this.msgService = msgService;
	}

	/**
	 * 
	 */
	public void run() {
		try {
			timer.schedule(new JobRunnerTimerTask(this), 1, 5 * 1000);
			initParams();
			doWork();
		} catch (Exception ex) {
			job.setJobRunError(JobRunErrorEnum.error);
			job.setErrorMessage(ex.getMessage());
		} finally {
			timer.cancel();
			job.setJobRunStatus(JobRunStatusEnum.finish);
			job.setEndTime(new Date());
			sendJobMsg();
		}
	}
}