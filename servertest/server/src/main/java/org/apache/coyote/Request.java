package org.apache.coyote;

import org.apache.tomcat.util.buf.MessageBytes;

public class Request {

	private Response response = null;
	private RequestInfo reqProcessorMX=new RequestInfo(this);
	private MessageBytes protoMB = MessageBytes.newInstance();

	public void setResponse(Response response) {
		this.response =response;
		response.setRequest(this);
	}
	
    public RequestInfo getRequestProcessor() {
        return reqProcessorMX;
    }
    
    public MessageBytes protocol() {
        return protoMB ;
    }
}
