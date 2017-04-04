package org.apache.tomcat.util.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DefaultServerSocketFactory{
	
    public DefaultServerSocketFactory(JIoEndpoint endpoint) {
    }
	
    public ServerSocket createSocket (int port, int backlog,
            InetAddress ifAddress) throws IOException {
        return new ServerSocket (port, backlog, ifAddress);
    }

	public Socket acceptSocket(ServerSocket socket) throws IOException {
		 return socket.accept();
	}

	public ServerSocket createSocket(int port, int backlog) throws IOException {
		return new ServerSocket (port, backlog);
	}
    
    
}	
