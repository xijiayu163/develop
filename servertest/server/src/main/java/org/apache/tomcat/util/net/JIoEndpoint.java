package org.apache.tomcat.util.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

import org.apache.coyote.http11.Http11Protocol;

public class JIoEndpoint {
	private int port=10000;
	private int backlog=100;
	
	protected ServerSocket serverSocket = null;
	protected int acceptorThreadPriority = Thread.NORM_PRIORITY;
	protected DefaultServerSocketFactory serverSocketFactory = null;
	
	public void init() throws IOException{
		bind();
	}
	
	public void startInternal() throws IOException{
		createExecutor();
		startAcceptorThreads();
	}
	
	public void createExecutor() {
		
	}
	
	public void bind() throws IOException{
		serverSocketFactory = new DefaultServerSocketFactory(this);
		serverSocket = serverSocketFactory.createSocket(port,
				backlog);
	}
	
	public void startAcceptorThreads(){
		String threadName = "http-bio-8080-Acceptor-0";
		Acceptor acceptor = new Acceptor();
		Thread t = new Thread(acceptor, threadName);
		t.setPriority(acceptorThreadPriority);
		t.setDaemon(false);
		t.start();
	}
	
    protected JIoEndpoint.Acceptor createAcceptor() {
        return new Acceptor();
    }
	
	public class Acceptor implements Runnable {
		private Executor executor = null;
		public void run() {
			Socket socket = null;
			try {
				socket = serverSocketFactory.acceptSocket(serverSocket);
				System.out.println("accept message");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		protected boolean processSocket(Socket socket) {
			SocketWrapper  wrapper = new SocketWrapper(socket);
			executor.execute(new SocketProcessor(wrapper));
			return false;
		}
	}

	protected class SocketProcessor implements Runnable {

		private SocketWrapper socket = null;

		public SocketProcessor(SocketWrapper wrapper) {
			this.socket = socket;
		}

		public void run() {
			synchronized (socket) {
				handler.process(socket);
			}
		}
		
	}
}
