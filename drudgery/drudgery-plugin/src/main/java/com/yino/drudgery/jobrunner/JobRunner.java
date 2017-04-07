package com.yino.drudgery.jobrunner;

import java.util.Date;
import java.util.Timer;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobResult;
import com.yino.drudgery.enums.JobExecuteErrorStatusEnum;
import com.yino.drudgery.mq.service.MessageService;

/**
 * @Description ��ҵִ���࣬������
 * 
 * @Copyright:
 * 
 * @author Wxb
 * @date
 * @version
 *
 */
public abstract class JobRunner implements Runnable {

	protected Timer timer = null;

	protected Job job = null;
	
	protected MessageService msgService;
	
	protected JobResult jobResult;

	/**
	 * 
	 * @param job
	 */
	public JobRunner(Job job) {
		this.job = job;
		timer = new Timer();
		timer.schedule(new JobRunnerTimerTask(this), 1, 5 * 1000);
	}

	/**
	 * @Description ִ��ǰ��ʼ��
	 * @throws Exception
	 */
	public abstract void initParams() throws Exception;

	/**
	 * @Description ��ҵ����
	 * @throws Exception
	 */
	public abstract void doWork() throws Exception;

	
	/**
	 * @Description ������ҵ����
	 */
	public void sendJobMsg() {
		JobResult jobResult = getJobResult();
		msgService.sendMsg(jobResult);
	}

	
	public JobResult getJobResult(){
		return null;
	}
	
	/**
	 * 
	 */
	public void run() {
		try {
			initParams();
			doWork();
		} catch (Exception ex) {
			jobResult.setErrorStatus(JobExecuteErrorStatusEnum.error);
			jobResult.setErrorMsg(ex.getMessage());
		} finally {
			timer.cancel();
			jobResult.setFinishTime(new Date());
			sendJobMsg();
		}
	}
}