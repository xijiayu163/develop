package com.yino.drudgery.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import com.yino.drudgery.entity.CronJobConfig;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.listener.JobConfigListener;
import com.yino.drudgery.service.impl.ServiceImpls;


/**
 * @Description:监听作业配置变更，动态调整定时作业
 * 初始化时，启动所有定时作业
 * 
 * 
 * @Title: QuartzJobConfigListener.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public class QuartzJobConfigListener implements JobConfigListener{

	private final Log log = LogFactory.getLog(this.getClass());	
	private SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

	private ConcurrentHashMap<String,JobKey> map = new ConcurrentHashMap<String,JobKey>();
	
	private ServiceImpls serviceImpls;
	
	public QuartzJobConfigListener(ServiceImpls serviceImpls)
	{
		this.serviceImpls=serviceImpls;
		serviceImpls.getJobConfigService().addJobConfigListener(this);
		List<JobConfig> list = serviceImpls.getJobConfigService().getAllJobConfig();
		addQuartzJobs(list);
	}
	/**
	 * 
	 * @param list
	 */
	public void addQuartzJobs(List<JobConfig> list)
	{
		for(JobConfig jobConfig:list)
		{
			addQuartzJob(jobConfig);
		}
	}
	
	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobConfig
	 *            作业配置对象
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	public void addQuartzJob(JobConfig jobConfig) {
		try {
			
			CronJobConfig cronJobConfig =null;
			if(!(jobConfig instanceof CronJobConfig))
			{
				return ;
			}
			
			cronJobConfig = (CronJobConfig)jobConfig;
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = newJob(QuartzJob.class).withIdentity(cronJobConfig.getJobName(), cronJobConfig.getJobGroupName())
					.build();// 任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put("1", cronJobConfig);
			jobDetail.getJobDataMap().put("2", serviceImpls);
			// 触发器
			CronTrigger trigger = newTrigger().withIdentity(cronJobConfig.getJobName(), cronJobConfig.getJobGroupName())
					.withSchedule(cronSchedule(cronJobConfig.getCronTime())).build();// 触发器时间设定
			sched.scheduleJob(jobDetail, trigger);
			map.put(jobConfig.getJobConfigID(), jobDetail.getKey());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobConfig
	 *            作业配置对象
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	public void updateQuartzJob(JobConfig jobConfig) {
		removeQuartzJob(jobConfig);
		addQuartzJob(jobConfig);
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobConfig
	 *            作业配置对象
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	public void removeQuartzJob(JobConfig jobConfig) {
		try {
			JobKey jobKey = map.get(jobConfig.getJobConfigID());
			if(jobKey==null)return ;
			
			TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(jobKey);// 删除任务
			map.remove(jobConfig.getJobConfigID());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:启动所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	public void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	public void shutdownJobs() {
		shutdownJobs(false);
	}

	/**
	 * @Description:关闭并清除所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	public void clearJobs() {
		shutdownJobs(true);
	}

	/**
	 * @Description:关闭并清除所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright:
	 * 
	 * @author
	 * @date
	 * @version
	 */
	private void shutdownJobs(boolean clear) {

		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
				if (clear)
					sched.clear();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void onRemove(JobConfig jobCfg) {
		removeQuartzJob(jobCfg);
	}

	@Override
	public void onUpdate(JobConfig jobCfg) {
		updateQuartzJob(jobCfg);
	}

	@Override
	public void onAdd(JobConfig jobCfg) {
		addQuartzJob(jobCfg);
	}
}
