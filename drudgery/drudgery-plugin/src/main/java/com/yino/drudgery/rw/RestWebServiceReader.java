package com.yino.drudgery.rw;

import com.yino.drudgery.entity.JobResult;

public class RestWebServiceReader extends WebServiceReader{

	@Override
	public JobResult getData() {
		if(this.requestEnum.equals(HttpRequestEnum.get)){
			return getDataByGet();
		}else{
			return getDataByPost();
		}
	}
	
	private JobResult getDataByGet(){
		return null;
	}
	
	private JobResult getDataByPost(){
		return null;
	}
}
