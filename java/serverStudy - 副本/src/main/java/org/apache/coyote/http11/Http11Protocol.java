package org.apache.coyote.http11;

import java.io.IOException;

import org.apache.coyote.Adapter;
import org.apache.coyote.ProtocolHandler;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;

public class Http11Protocol implements ProtocolHandler{

	protected Adapter adapter;
	protected JIoEndpoint endpoint = null;
	protected Http11ConnectionHandler cHandler;
	
	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;	
	}

	public Adapter getAdapter() {
		return this.adapter;
	}
	
	public int getPort() { return endpoint.getPort(); }
    public void setPort(int port) {
        endpoint.setPort(port);
    }
	
	/**
	 * 初始化终端，用于监听请求
	 * 初始化请求处理器，请求会被委托给处理器处理
	 */
	public Http11Protocol() {
		endpoint = new JIoEndpoint();
		cHandler = new Http11ConnectionHandler(this);
		endpoint.setHandler(cHandler);
	}
	
	public void init() {
		//初始化终端
		try {
			endpoint.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		endpoint.start();
	}
	
	public static class Http11ConnectionHandler{
		protected Http11Protocol proto;
		
		/**
		 * 将请求处理器与协议处理器关联
		 * @param proto
		 */
		Http11ConnectionHandler(Http11Protocol proto) {
            this.proto = proto;
        }
		
		public void process(SocketWrapper wrapper, SocketStatus status) {
			try{
				Http11Processor processor  = createProcessor();
				processor.process(wrapper);
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
		public Http11Processor createProcessor(){
			Http11Processor processor = new Http11Processor(proto.endpoint);
			processor.setAdapter(proto.adapter);
			return processor;
		}
	}

	public void stop() throws Exception {
		
	}

	public void destroy() throws Exception {
		
	}
}
