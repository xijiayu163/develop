package org.apache.coyote.http11;

import java.io.IOException;
import java.net.Socket;

import org.apache.coyote.Adapter;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.HexUtils;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

/**
 * 每个请求都对应一个处理器实例
 * @author xijia
 *
 */
public class Http11Processor {
	 protected Adapter adapter;
	 protected JIoEndpoint endpoint;
	 protected Request request;
	 protected Response response;
	 protected SocketWrapper<Socket> socketWrapper = null;
	 protected InternalInputBuffer inputBuffer = null;
	 private InternalOutputBuffer outputBuffer;
	 /**
	     * Host name (used to avoid useless B2C conversion on the host name).
	     */
	 protected char[] hostNameC = new char[0];
	 
	private void setSocketWrapper(SocketWrapper<Socket> socketWrapper) {
		this.socketWrapper = socketWrapper;
	}
	
	private InternalInputBuffer getInputBuffer() {
		return inputBuffer;
	}
	
	private InternalOutputBuffer getOutputBuffer() {
		return outputBuffer;
	}
	 
	 public Http11Processor(JIoEndpoint endpoint){
		 this.endpoint = endpoint;
		 request = new Request();
		 response = new Response();
		 request.setResponse(response);
		 inputBuffer = new InternalInputBuffer(request,8 * 1024);
		 outputBuffer = new InternalOutputBuffer(response);
	 }


	public void process(SocketWrapper<Socket> wrapper) throws IOException {
		setSocketWrapper(wrapper);
		//初始化读IO
		getInputBuffer().init(socketWrapper, endpoint);
		//初始化写IO
		getOutputBuffer().init(socketWrapper, endpoint);
		//解析请求行,将解析的数据赋给request
		getInputBuffer().parseRequestLine(true);
		//解析请求头,将解析的数据赋给request
		getInputBuffer().parseHeaders();
		prepareRequest();
		try {
			adapter.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		endRequest();
	}
	
	/**
	 * 当读完头消息后，需要给Request准备相应的数据
	 */
	private void prepareRequest() {
		MimeHeaders headers = request.getMimeHeaders();
	
		MessageBytes valueMB = headers.getValue("host");
		parseHost(valueMB);
	}

	private void parseHost(MessageBytes valueMB) {
		 if (valueMB == null || valueMB.isNull()) {
	            // HTTP/1.0
	            // If no host header, use the port info from the endpoint
	            // The host will be obtained lazily from the socket if required
	            // using ActionCode#REQ_LOCAL_NAME_ATTRIBUTE
	            request.setServerPort(endpoint.getPort());
	            return;
	        }

	        ByteChunk valueBC = valueMB.getByteChunk();
	        byte[] valueB = valueBC.getBytes();
	        int valueL = valueBC.getLength();
	        int valueS = valueBC.getStart();
	        int colonPos = -1;
	        if (hostNameC.length < valueL) {
	            hostNameC = new char[valueL];
	        }

	        boolean ipv6 = (valueB[valueS] == '[');
	        boolean bracketClosed = false;
	        for (int i = 0; i < valueL; i++) {
	            char b = (char) valueB[i + valueS];
	            hostNameC[i] = b;
	            if (b == ']') {
	                bracketClosed = true;
	            } else if (b == ':') {
	                if (!ipv6 || bracketClosed) {
	                    colonPos = i;
	                    break;
	                }
	            }
	        }

	        if (colonPos < 0) {
//	            if (!endpoint.isSSLEnabled()) {
	                // 80 - Default HTTP port
	                request.setServerPort(80);
//	            } else {
//	                // 443 - Default HTTPS port
//	                request.setServerPort(443);
//	            }
	            request.serverName().setChars(hostNameC, 0, valueL);
	        } else {
	            request.serverName().setChars(hostNameC, 0, colonPos);

	            int port = 0;
	            int mult = 1;
	            for (int i = valueL - 1; i > colonPos; i--) {
	                int charValue = HexUtils.getDec(valueB[i + valueS]);
	                if (charValue == -1 || charValue > 9) {
	                    // Invalid character
	                    // 400 - Bad request
	                    break;
	                }
	                port = port + (charValue * mult);
	                mult = 10 * mult;
	            }
	            request.setServerPort(port);
	        }
	}

	private void endRequest() {
		
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}
	
    public Adapter getAdapter() {
        return adapter;
    }
}
