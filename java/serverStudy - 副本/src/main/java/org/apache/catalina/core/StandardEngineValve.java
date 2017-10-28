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
		//Ҫ��ȡhost����Ҫ����mappingData
		//����mappingData���ع��̴�����,����coyoteAdapter��postParseRequest��
		//��connector.getMapper().map��ӳ��õ�
		Host host = request.getHost();
		host.getPipeline().getFirst().invoke(request, response);
	}
	
	
}
