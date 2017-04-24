package com.yino.drudgery.rw.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yino.drudgery.datasource.IMultiDataSource;
import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.enums.ResultEnum;
import com.yino.drudgery.rw.IRead;
import com.yino.util.JsonUtil;

public class DbReader implements IRead{

	@Autowired
	private  IMultiDataSource multiDataSource;
	
	private String dataSourceName;
	private String commandText;
	private CommandType commandType;
	private List<String> paramValues;
	

	public List<String> getParamValues() {
		return paramValues;
	}

	public void setParamValues(List<String> paramValues) {
		this.paramValues = paramValues;
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
	
	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	
	public JobData getData(){
		JdbcTemplate jdbcTemplate = multiDataSource.getJdbcTemplate(dataSourceName);
		if(commandType.equals(CommandType.sql)){
			List<Map<String, Object>> result = jdbcTemplate.queryForList(commandText);
			return getJobDataByResult(result);  
		}else{
			return getDataByProcedure();
		}
	}
	
	private JobData getDataByProcedure() {
		JdbcTemplate jdbcTemplate = multiDataSource.getJdbcTemplate(dataSourceName);
		Object obj = jdbcTemplate.execute(   
			     new CallableStatementCreator() {   
			    	 @Override
			        public CallableStatement createCallableStatement(Connection con) throws SQLException {   
			    	   String storedProc = getProcStr();  
			           CallableStatement cs = con.prepareCall(storedProc);   
			           cs.setString(1, "p1");// 设置输入参数的值   
			           cs.registerOutParameter(2,1);// 注册输出参数的类型   
			           return cs;   
			        }  
			     }, new CallableStatementCallback<Object>() {   
			        public Object doInCallableStatement(CallableStatement cs) throws SQLException,DataAccessException {   
			           List<Map<String,String>> resultsMap = new ArrayList<>();   
			           cs.execute();   
			           ResultSet rs = (ResultSet) cs.getObject(2);// 获取游标一行的值   
			           while (rs.next()) {// 转换每行的返回值到Map中   
			              Map<String,String> rowMap = new HashMap<>();   
			              rowMap.put("id", rs.getString("id"));   
			              rowMap.put("name", rs.getString("name"));   
			              resultsMap.add(rowMap);   
			           }   
			           rs.close();   
			           return resultsMap;   
			        }   
			  });   
		
		return getJobDataByResult(obj);
	}
	
	/**
	 * 返回{call sp_list_table(?,?)}格式的字符串
	 * @return
	 */
	private String getProcStr(){
		StringBuilder sb = new StringBuilder();
		sb.append("{call ");
		sb.append(commandText);
		if(paramValues!=null && paramValues.size()>0){
			sb.append("(");
		}
		for (String paramValue : paramValues) {
			sb.append(paramValue);
			sb.append(",");
		}
		sb.setLength(sb.length()-1);
		if(paramValues!=null && paramValues.size()>0){
			sb.append(")");
		}
		sb.append("}");
		String storedProc = sb.toString();
		return storedProc;
	}
	
	private JobData getJobDataByResult(Object obj){
		JobData jobData = new JobData();
		try {
			String jsonData = JsonUtil.obj2Json(obj);
			jobData.setJsonData(jsonData);
			jobData.setStatus(ResultEnum.success);
		} catch (JsonProcessingException e) {
			jobData.setStatus(ResultEnum.failure);
			jobData.setErrorMessage(e.getMessage());
		}
		return jobData;
	}
}
