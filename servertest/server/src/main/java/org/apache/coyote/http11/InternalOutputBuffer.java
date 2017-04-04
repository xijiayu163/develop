package org.apache.coyote.http11;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.coyote.Response;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class InternalOutputBuffer {
	private OutputStream outputStream = null;
	private Response response;
	protected byte[] buf;
	protected OutputStreamOutputBuffer outputStreamOutputBuffer;

	public InternalOutputBuffer(Response response, int headerBufferSize) {
		this.response = response;
		buf = new byte[headerBufferSize];
		outputStreamOutputBuffer = new OutputStreamOutputBuffer();
	}

	public void init(SocketWrapper socketWrapper,
            JIoEndpoint endpoint) throws IOException {
        outputStream  = socketWrapper.getSocket().getOutputStream();
    }
	
	protected class OutputStreamOutputBuffer{
		
	}
}
