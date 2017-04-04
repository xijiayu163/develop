package org.apache.coyote.http11;

import java.io.IOException;
import java.net.Socket;

import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class Http11Protocol {
	private CoyoteAdapter adapter = null;
	private JIoEndpoint endpoint =null;

	public void setAdapter(CoyoteAdapter adapter) { 
		this.adapter  = adapter; 
	}

	public Http11Protocol() {
		endpoint = new JIoEndpoint();
	}
	
	public void init() throws IOException{
		endpoint.init();
	}
	
	public void start(){
		try {
			endpoint.startInternal();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	protected static class Http11ConnectionHandler{
		
		protected Http11Protocol proto;
		
		public void process(SocketWrapper wrapper){
			Socket socket = wrapper.getSocket();
			Http11Processor processor = createProcessor();
		}
		
		protected Http11Processor createProcessor() {
			Http11Processor processor = new Http11Processor(proto.endpoint);
			processor.setAdapter(proto.adapter);
			return processor;
		}
	}
}
