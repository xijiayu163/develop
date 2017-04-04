package org.apache.coyote.http11;

import java.io.IOException;
import java.util.Set;

import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.coyote.ActionHook;
import org.apache.coyote.Request;
import org.apache.coyote.RequestInfo;
import org.apache.coyote.Response;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class Http11Processor implements ActionHook{
    private JIoEndpoint endpoint =null;
	private Request request = null;
	private Response response = null;
	private CoyoteAdapter adapter = null;
	private SocketWrapper socketWrapper = null;
	private InternalInputBuffer inputBuffer = null;
	protected InternalOutputBuffer outputBuffer = null;
	
	public SocketWrapper getSocketWrapper() {
		return socketWrapper;
	}

	public void setSocketWrapper(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
	}
	
	public InternalInputBuffer getInputBuffer() {
		return inputBuffer;
	}

	public void setInputBuffer(InternalInputBuffer inputBuffer) {
		this.inputBuffer = inputBuffer;
	}
	
	public InternalOutputBuffer getOutputBuffer() {
		return outputBuffer;
	}

	public void setOutputBuffer(InternalOutputBuffer outputBuffer) {
		this.outputBuffer = outputBuffer;
	}

	public Http11Processor(int headerBufferSize, JIoEndpoint endpoint, int maxTrailerSize,
            Set<String> allowedTrailerHeaders, int maxExtensionSize, int maxSwallowSize) {
    	 this.endpoint  = endpoint;
         request  = new Request();
         response = new Response();
         response.setHook(this);
         request.setResponse(response);
         inputBuffer = new InternalInputBuffer(request, headerBufferSize);
         outputBuffer = new InternalOutputBuffer(response, headerBufferSize);
    }

	public Http11Processor(JIoEndpoint endpoint) {
		this.endpoint = endpoint;
        request  = new Request();
        response = new Response();
        response.setHook(this);
        request.setResponse(response);
	}

	public void setAdapter(CoyoteAdapter adapter) {
		this.adapter = adapter;
	}
	
	
	public void process(SocketWrapper socketWrapper) throws IOException{
		RequestInfo rp = request.getRequestProcessor();
		setSocketWrapper(socketWrapper);
		getInputBuffer().init(socketWrapper, endpoint);
		getOutputBuffer().init(socketWrapper, endpoint);
		prepareRequest();
		MessageBytes protocolMB = request.protocol();
	}
	
	protected void prepareRequest() {
		
	}
}
