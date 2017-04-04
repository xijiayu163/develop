package org.apache.coyote.http11;

import java.io.IOException;
import java.io.InputStream;

import org.apache.coyote.Request;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class InternalInputBuffer {
	protected byte[] buf;
	protected int lastValid;
	protected int pos;
	protected int end;
	private InputStream inputStream;
	private Request request = null;
	private InputStreamInputBuffer inputStreamInputBuffer;
	
	
	public InternalInputBuffer(Request request, int headerBufferSize) {
		 this.request = request;
		 buf = new byte[headerBufferSize];
		 inputStreamInputBuffer = new InputStreamInputBuffer();
	}

	protected void init(SocketWrapper socketWrapper,
            JIoEndpoint endpoint) throws IOException {
        inputStream = socketWrapper.getSocket().getInputStream();
    }
	
	protected class InputStreamInputBuffer{
		
	}
}
