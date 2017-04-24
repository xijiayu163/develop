package com.yino.drudgery.rw.db;

import java.util.List;

import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.rw.IWrite;

public class DbWriter implements IWrite{

	private String DataSource;
	private String tableName;
	private List<String> primaryKeyFields;
	
	public JobData writeData(JobData jobData) {
		// TODO Auto-generated method stub
		return null;
	}
}
