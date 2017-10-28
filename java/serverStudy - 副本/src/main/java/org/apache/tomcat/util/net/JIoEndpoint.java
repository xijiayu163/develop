package org.apache.tomcat.util.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.coyote.http11.Http11Protocol.Http11ConnectionHandler;

public class JIoEndpoint {
	/**
	 * serverSocket，监听来自客户端的请求
	 */
	protected ServerSocket serverSocket = null;
	/**
	 * http11处理器，来自客户端的请求会委托该对象处理
	 */
	protected Http11ConnectionHandler handler = null;
	protected ServerSocketFactory serverSocketFactory = null;
	private int port;
	private int backlog = 100;
    /**
     * java.util.concurrent下的执行器
     */
    private Executor executor = null;

	public void setHandler(Http11ConnectionHandler handler) {
		this.handler = handler;
	}

	public void setServerSocketFactory(ServerSocketFactory factory) {
		this.serverSocketFactory = factory;
	}

	public ServerSocketFactory getServerSocketFactory() {
		return serverSocketFactory;
	}
	
	public int getPort() { return port; }
	public void setPort(int port ) { this.port=port; }
	
    public void setBacklog(int backlog) { if (backlog > 0) this.backlog = backlog; }
    public int getBacklog() { return backlog; }
	
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    public Executor getExecutor() { return executor; }
    
    /**
     * 创建执行器
     */
    public void createExecutor() {
    	LinkedBlockingQueue<Runnable> taskqueue = new LinkedBlockingQueue<Runnable>();
    	executor = new ThreadPoolExecutor(1,10,60,TimeUnit.SECONDS,taskqueue);
    }
    
	public void init() throws Exception{
		bind();
	}

	/**
	 * 
	 */
	private void bind()  throws Exception {
		if (serverSocketFactory == null) {
			 serverSocketFactory = new DefaultServerSocketFactory();
		}
		if (serverSocket == null) {
			serverSocket = serverSocketFactory.createSocket(getPort(),getBacklog());
		}
	}

	/**
	 * 启动终端,开始监听连接请求
	 */
	public void start() {
		if (getExecutor() == null) {
            createExecutor();
        }
		startAcceptorThreads();
	}

	/**
	 * 启动监听线程
	 */
	private void startAcceptorThreads() {
		Acceptor acceptor = createAcceptor();
		Thread t = new Thread(acceptor, "acceptor-thread");
        t.setPriority(Thread.NORM_PRIORITY);
        t.setDaemon(false);
        t.start();
	}
	
    protected Acceptor createAcceptor() {
        return new Acceptor();
    }
	
	/**
	 * 请求接收器，接收并处理请求
	 * @author xijia
	 *
	 */
	protected class Acceptor implements Runnable{
		public void run() {
			while (true) {
				try {
					Socket socket = null;
					socket = serverSocketFactory.acceptSocket(serverSocket);
					System.out.println("接收到新的请求");
					processSocket(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void closeSocket(Socket socket) {
			try {
	            socket.close();
	        } catch (IOException e) {
	            // Ignore
	        }
		}

		/**
		 * 处理接收到到的socket
		 * 将socket包装后，交给线程处理器执行,然后返回，以便接收下一个请求
		 * @param socket
		 */
		private void processSocket(Socket socket) {
			SocketWrapper wrapper = new SocketWrapper(socket);
			getExecutor().execute(new SocketProcessor(wrapper));
		}
		
	}
	
	/**
	 * Socket处理器,内部直接给http11ProtocolHandler处理
	 * @author xijia
	 *
	 */
	protected class SocketProcessor implements Runnable {

		protected SocketWrapper socket = null;
		
		 public SocketProcessor(SocketWrapper socket) {
			 this.socket = socket;
		 }
		
		public void run() {
			synchronized (socket) {
				handler.process(socket, SocketStatus.OPEN_READ);
			}
		}
	}
}
