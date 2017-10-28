package org.apache.coyote.http11;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.coyote.Response;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class InternalOutputBuffer {

	private Response response;
	protected OutputStream outputStream;

	public InternalOutputBuffer(Response response) {
		this.response = response;
	}

	public void init(SocketWrapper<Socket> socketWrapper, JIoEndpoint endpoint) throws IOException {
		outputStream = socketWrapper.getSocket().getOutputStream();
	}
	
}
