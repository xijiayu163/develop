package org.apache.catalina.core;

import java.util.concurrent.Executor;

import javax.management.ObjectName;

import org.apache.catalina.Container;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.util.LifecycleBase;

public class StandardService extends LifecycleBase implements Service{
	
	private String name = null;
	private Server server = null;
	
	protected Container container = null;
	protected Connector connectors[] = new Connector[0];
	private final Object connectorsLock = new Object();
	
	public Container getContainer() {
		return this.container;
	}
	
	public void setContainer(Container container){
		this.container = container;
	}

	public void addConnector(Connector connector) {
		synchronized (connectorsLock) {
			connector.setService(this);
			Connector results[] = new Connector[connectors.length + 1];
            System.arraycopy(connectors, 0, results, 0, connectors.length);
            results[connectors.length] = connector;
            connectors = results;
		}
	}

	@Override
	protected void initInternal() throws LifecycleException {
		if (container != null) {
			log.debug("容器不为空，调用其init方法,容器类:"+container.getClass().getName());
            container.init();
        }
		
		log.debug("遍历connectors，逐个调用其init方法");
		synchronized (connectorsLock) {
			for (Connector connector : connectors) {
				connector.init();
			}
		}
	}

	@Override
	protected void startInternal() throws LifecycleException {
		log.debug("设置当前状态为STARTING");
		setState(LifecycleState.STARTING);
		if (container != null) {
            synchronized (container) {
            	log.debug("容器不为空，调用其start方法,容器类:"+container.getClass().getName());
                container.start();
            }
        }
		
		log.debug("遍历connectors，逐个调用其start方法");
		synchronized (connectorsLock) {
			for (Connector connector: connectors) {
				connector.start();
			}
		}
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
    public Server getServer() {
        return (this.server);
    }

	public String getName() {
		return (this.name);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	protected void stopInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Connector[] findConnectors() {
		return (connectors);
	}

	public ObjectName getObjectName() {
		// TODO Auto-generated method stub
		return null;
	}
}
