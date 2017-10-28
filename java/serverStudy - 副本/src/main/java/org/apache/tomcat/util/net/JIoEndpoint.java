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
	 * serverSocket���������Կͻ��˵�����
	 */
	protected ServerSocket serverSocket = null;
	/**
	 * http11�����������Կͻ��˵������ί�иö�����
	 */
	protected Http11ConnectionHandler handler = null;
	protected ServerSocketFactory serverSocketFactory = null;
	private int port;
	private int backlog = 100;
    /**
     * java.util.concurrent�µ�ִ����
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
     * ����ִ����
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
	 * �����ն�,��ʼ������������
	 */
	public void start() {
		if (getExecutor() == null) {
            createExecutor();
        }
		startAcceptorThreads();
	}

	/**
	 * ���������߳�
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
	 * ��������������ղ���������
	 * @author xijia
	 *
	 */
	protected class Acceptor implements Runnable{
		public void run() {
			while (true) {
				try {
					Socket socket = null;
					socket = serverSocketFactory.acceptSocket(serverSocket);
					System.out.println("���յ��µ�����");
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
		 * ������յ�����socket
		 * ��socket��װ�󣬽����̴߳�����ִ��,Ȼ�󷵻أ��Ա������һ������
		 * @param socket
		 */
		private void processSocket(Socket socket) {
			SocketWrapper wrapper = new SocketWrapper(socket);
			getExecutor().execute(new SocketProcessor(wrapper));
		}
		
	}
	
	/**
	 * Socket������,�ڲ�ֱ�Ӹ�http11ProtocolHandler����
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
