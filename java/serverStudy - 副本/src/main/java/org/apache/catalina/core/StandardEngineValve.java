package org.apache.catalina.core;

import java.io.IOException;

import org.apache.catalina.Host;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class StandardEngineValve extends ValveBase{
	private static final String info =
	        "org.apache.catalina.core.StandardEngineValve/1.0";
	
	public String getInfo() {
		return (info);
	}

	@Override
	public void invoke(Request request, Response response) throws IOException {
		//要获取host，先要加载mappingData
		//分析mappingData加载过程待分析,是在coyoteAdapter的postParseRequest中
		//的connector.getMapper().map中映射得到
		Host host = request.getHost();
		host.getPipeline().getFirst().invoke(request, response);
	}
	
	
}
