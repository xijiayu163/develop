package com.yino.drudgery.jobrunner;

import java.util.ArrayList;
import java.util.List;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobData;
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
		try {
			readImpl = ReaderFactory.createInstance("jarFile", "className");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			writeImpl = WriterFactory.createInstance("jarFile", "className");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		convertImpl = new ArrayList<IConvert>();
		for (String s : getWriterClassPath()) {
			IConvert c;
			try {
				c = ConverterFactory.createInstance("jarFile", "className");
				convertImpl.add(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 执行作业主函数 流程：1.创建读取数据接口；2.创建写数据接口；3.创建数据转换接口； 4.取数据；5.数据转换；6.写数据。
	 */
	@Override
	public void doWork() {
		Job job = getJob();
		JobData value = null;

		// 从取数据接口提取数据
		if (readImpl != null) {
			value = readImpl.getData();
			if (value.getStatus() == ResultEnum.failed) {
				job.setErrorMessage(value.getErrorMessage());
				job.setJobRunError(JobRunErrorEnum.error);
				return;
			}

		} else {
			value = job.getInputJobData();
			if(value==null)
			{
				job.setErrorMessage("没有配置数据获取接口或提供输入数据！");
				job.setJobRunError(JobRunErrorEnum.error);
				return;
			}
		}

		// 数据转换接口
		if (convertImpl != null || !convertImpl.isEmpty()) {
			for (IConvert converter : convertImpl) {
				value = converter.convert(value);
				if (value.getStatus() == ResultEnum.failed) {
					job.setErrorMessage(value.getErrorMessage());
					job.setJobRunError(JobRunErrorEnum.error);
					return;
				}
			}
		}

		// 数据写入接口
		if (writeImpl != null) {
			value = writeImpl.writeData(value);
			if (value.getStatus() == ResultEnum.failed) {
				job.setErrorMessage(value.getErrorMessage());
				job.setJobRunError(JobRunErrorEnum.error);
				return;
			}
		}

		// 需要JobMsg返回数据

		if (getJob().isNeedOutput()) {
			job.setOutputJobData(value);
		}

	}


	protected String getReaderClassPath() {

		return "";
	}

	protected String getConverterClassPaths() {
		return "";
	}

	protected List<String> getWriterClassPath() {
		List<String> list = new ArrayList<String>();
		return list;
	}

}
