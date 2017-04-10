package com.yino.drudgery.rw;

import com.yino.drudgery.entity.JobData;

public class RestWebServiceReader extends WebServiceReader{

	@Override
	public JobData getData() {
		if(this.requestEnum.equals(HttpRequestEnum.get)){
			return getDataByGet();
		}else{
			return getDataByPost();
		}
	}
	
	private JobData getDataByGet(){
		return null;
	}
	
	private JobData getDataByPost(){
		return null;
	}
}
