package org.apache.catalina.connector;

import java.util.HashMap;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Service;
import org.apache.catalina.tribes.demos.IntrospectionUtils;
import org.apache.catalina.util.LifecycleBase;
import org.apache.coyote.Adapter;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11Protocol;
import org.apache.tomcat.util.http.mapper.Mapper;

/**
 * ����������Ҫ���ø���Э�����ʹ�����ͬ��Э�鴦���� ��ʼ��ʱ����Э���ַ�������Э�鴦����������Ȼ�����������������Э�鴦����ʵ��
 * ��Ҫ��������Э�鴦������������,����������Э�鴦����������Э�鴦������ί����������������
 * ��������Э�鴦�����������
 * 
 * @author xijia
 *
 */
public class Connector extends LifecycleBase{
	
	
	/**
	 * ������
	 */
	protected Adapter adapter = null;
	
	/**
	 * Э�鴦����������������ͨ����������Э�鴦��������ֵ��protocolHandler
	 */
	protected String protocolHandlerClassName = "org.apache.coyote.http11.Http11Protocol";

	/**
	 * Э�鴦����
	 */
	protected ProtocolHandler protocolHandler = null;
	protected int port = -1;
	
	protected Service service = null;

	protected Mapper mapper = new Mapper();
	
	protected MapperListener mapperListener = new MapperListener(mapper, this);

	protected String scheme = "http";
	
	protected int redirectPort = 443;

	protected static HashMap<String,String> replacements =
         new HashMap<String,String>();
     static {
         replacements.put("acceptCount", "backlog");
         replacements.put("connectionLinger", "soLinger");
         replacements.put("connectionTimeout", "soTimeout");
         replacements.put("rootFile", "rootfile");
     }
	/**
	 * ���캯��������Э��������������Э�鴦����
	 */
	public Connector() {
		this(null);
	}
	
    public Connector(String protocol) {
        setProtocol(protocol);
        try {
            Class<?> clazz = Class.forName(protocolHandlerClassName);
            this.protocolHandler = (ProtocolHandler) clazz.newInstance();
        } catch (Exception e) {
        }
    }

	/**
	 * ����Э����������Э������,���ں�����������Э�鴦����
	 * @param protocol
	 */
	public void setProtocol(String protocol) {
		if ("HTTP/1.1".equals(protocol)) {
			setProtocolHandlerClassName("org.apache.coyote.http11.Http11Protocol");
		} else if ("AJP/1.3".equals(protocol)) {
			setProtocolHandlerClassName("org.apache.coyote.ajp.AjpProtocol");
		} else if (protocol != null) {
			setProtocolHandlerClassName(protocol);
		}
	}

	/**
	 * ����Э�鴦��������
	 * 
	 * @return
	 */
	public String getProtocolHandlerClassName() {
		return (this.protocolHandlerClassName);
	}

	/**
	 * ���������Զ����Э�鴦����
	 * @param protocolHandlerClassName
	 */
	public void setProtocolHandlerClassName(String protocolHandlerClassName) {
		this.protocolHandlerClassName = protocolHandlerClassName;
	}

	/**
	 * ����Э���ַ�������Ӧ�����Э�����ƣ�Ŀǰֻ֧��HTTP/1.1
	 * 
	 * @return
	 */
	public String getProtocol() {

		if ("org.apache.coyote.http11.Http11Protocol".equals(getProtocolHandlerClassName())
				|| "org.apache.coyote.http11.Http11AprProtocol".equals(getProtocolHandlerClassName())) {
			return "HTTP/1.1";
		} else if ("org.apache.coyote.ajp.AjpProtocol".equals(getProtocolHandlerClassName())
				|| "org.apache.coyote.ajp.AjpAprProtocol".equals(getProtocolHandlerClassName())) {
			return "AJP/1.3";
		}
		return getProtocolHandlerClassName();
	}

    public int getPort() {
        return (this.port);
    }
    public void setPort(int port) {
        this.port = port;
        Http11Protocol p = (Http11Protocol)protocolHandler;
        p.setPort(port);	
    }
	
	/**
	 * ��ʼ��������������������Э�鴦��������
	 * �������Э�鴦�����ĳ�ʼ������
	 * @throws LifecycleException 
	 */
    protected void initInternal() throws LifecycleException{
		adapter = new CoyoteAdapter(this);
		protocolHandler.setAdapter(adapter);
		protocolHandler.init();
		mapperListener.init();
	}
	
	
	/**
	 * ���� 
	 * @throws LifecycleException 
	 */
	public void startInternal() throws LifecycleException{
		if (getPort() < 0) {
            System.err.println("û�����ö˿�");
            return;
        }
		
		setState(LifecycleState.STARTING);
		
		log.debug("启动protocolHandler...");
		protocolHandler.start();
		log.debug("启动mapperListener...");
		mapperListener.start();
	}

	public Request createRequest() {
		Request request = new Request();
		request.setConnector(this);
		return request;
	}

	public Response createResponse() {
		Response response = new Response();
        response.setConnector(this);
        return response;
	}

    public Service getService() {
        return (this.service);
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Mapper getMapper() {
        return (mapper);
    }

	public String getScheme() {
		return (this.scheme);
	}
	
    public int getRedirectPort() {
        return (this.redirectPort);
    }
    
    public void setRedirectPort(int redirectPort) {
        this.redirectPort = redirectPort;
    }

	@Override
	protected void stopInternal() throws LifecycleException {
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
	}

	public Object getAttribute(String name) {
		return getProperty(name);
	}

	private Object getProperty(String name) {
		String repl = name;
        if (replacements.get(name) != null) {
            repl = replacements.get(name);
        }
        return IntrospectionUtils.getProperty(protocolHandler, repl);
	}
}
