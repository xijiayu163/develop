package com.yino.drudgery.rw;

import com.yino.drudgery.entity.JobData;

public class DbReader implements IRead{
	private String DataSource;
	private String commandText;
	private CommandType commandType;
	
	public String getDataSource() {
		return DataSource;
	}

	public void setDataSource(String dataSource) {
		DataSource = dataSource;
	}

	public String getCommandText() {
		return commandText;
	}

	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}
	
	public JobData getData(){
		
		return null;
	}
}
