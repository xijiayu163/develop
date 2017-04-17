package com.yino.drudgery.jobrunner;

import java.util.ArrayList;
import java.util.List;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfigParam;
import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.enums.JobParamTypeEnum;
import com.yino.drudgery.enums.JobRunErrorEnum;
import com.yino.drudgery.enums.ResultEnum;
import com.yino.drudgery.factory.ConverterFactory;
import com.yino.drudgery.factory.ReaderFactory;
import com.yino.drudgery.factory.WriterFactory;
import com.yino.drudgery.rw.IConvert;
import com.yino.drudgery.rw.IRead;
import com.yino.drudgery.rw.IWrite;

/**
 * 执行作业类
 * 
 * @author Wxb
 *
 */
public class StandardJobRunner extends JobRunner {

	private IRead readImpl = null;
	private IWrite writeImpl = null;
	private List<IConvert> convertImpl = null;

	/**
	 * 构造函数
	 */
	public StandardJobRunner() {
	}

	/**
	 * 初始化读写接口及数据转换接口
	 * 
	 * @throws Exception
	 */
	@Override
	public void initParams() {

		Job job = getJob();
		try {
			List<String> list = getClassPath(JobParamTypeEnum.getClassPath.toString());
			if (list.size() > 0) {
				String[] ss = list.get(0).split(";");
				if (ss.length >= 2) {
					readImpl = ReaderFactory.createInstance(ss[0], ss[1]);
				} else {
					job.setJobRunError(JobRunErrorEnum.createReaderError);
					job.setErrorMessage(String.format("创建Read接口错误,无法解析配置：{0}", list.get(0)));
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			job.setJobRunError(JobRunErrorEnum.createReaderError);
			job.setErrorMessage(e.getMessage());
		}

		try {
			List<String> list = getClassPath(JobParamTypeEnum.setClassPath.toString());
			if (list.size() > 0) {
				String[] ss = list.get(0).split(";");
				if (ss.length >= 2) {
					writeImpl = WriterFactory.createInstance(ss[0], ss[1]);
				} else {
					job.setJobRunError(JobRunErrorEnum.createWriterError);
					job.setErrorMessage(String.format("创建Write接口错误,无法解析配置：{0}", list.get(0)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.setJobRunError(JobRunErrorEnum.createWriterError);
			job.setErrorMessage(e.getMessage());
		}

		try {
			List<String> list = getClassPath(JobParamTypeEnum.convertClassPath.toString());
			for (String s : list) {
				String[] ss = s.split(";");
				if (ss.length >= 2) {
					writeImpl = WriterFactory.createInstance(ss[0], ss[1]);
				} else {
					job.setJobRunError(JobRunErrorEnum.createConverterError);
					job.setErrorMessage(String.format("创建Convert接口错误,无法解析配置：{0}", list.get(0)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.setJobRunError(JobRunErrorEnum.createConverterError);
			job.setErrorMessage(e.getMessage());
		}
	}

	/**
	 * 执行作业主函数 流程：1.创建读取数据接口；2.创建写数据接口；3.创建数据转换接口； 4.取数据；5.数据转换；6.写数据。
	 */
	@Override
	public void doWork() {
		Job job = getJob();
		JobData value = null;

		// 如果初始化已经出现错误，则不再继续执行
		if (job.getJobRunError() != null) {
			return;
		}

		// 从取数据接口提取数据
		if (readImpl != null) {
			value = readImpl.getData();
			if (value.getStatus() == ResultEnum.failure) {
				job.setErrorMessage(value.getErrorMessage());
				job.setJobRunError(JobRunErrorEnum.getError);
				return;
			}

		} else {
			value = job.getInputJobData();
			if (value == null) {
				job.setErrorMessage("没有配置数据获取接口或提供输入数据！");
				job.setJobRunError(JobRunErrorEnum.getError);
				return;
			}
		}

		// 数据转换接口
		if (convertImpl != null || !convertImpl.isEmpty()) {
			for (IConvert converter : convertImpl) {
				value = converter.convert(value);
				if (value.getStatus() == ResultEnum.failure) {
					job.setErrorMessage(value.getErrorMessage());
					job.setJobRunError(JobRunErrorEnum.convertError);
					return;
				}
			}
		}

		// 数据写入接口
		if (writeImpl != null) {
			value = writeImpl.writeData(value);
			if (value.getStatus() == ResultEnum.failure) {
				job.setErrorMessage(value.getErrorMessage());
				job.setJobRunError(JobRunErrorEnum.setError);
				return;
			}
		}

		// 需要JobMsg返回数据

		if (getJob().isNeedOutput()) {
			job.setOutputJobData(value);
		}

	}

	/**
	 * @Description 获取各类接口路径
	 * @param jobParamType
	 * @return
	 */
	protected List<String> getClassPath(String jobParamType) {
		List<String> classList = new ArrayList<String>();
		List<JobConfigParam> pList = getJob().getJobcfg().getJobConfigParams();
		for (JobConfigParam p : pList) {
			if (p.getParamType() == jobParamType) {
				classList.add(p.getValue());
			}
		}
		return classList;
	}

}
