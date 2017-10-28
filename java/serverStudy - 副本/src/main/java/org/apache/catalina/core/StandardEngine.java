package org.apache.catalina.core;

import java.beans.PropertyChangeSupport;

import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Service;

public class StandardEngine extends ContainerBase implements Engine{
	/**
    * Host name to use when no server host, or an unknown host,
    * is specified in the request.
    */
	private String defaultHost = null;
	protected PropertyChangeSupport support = new PropertyChangeSupport(this);
	private Service service = null;
	private String baseDir = null;
	
	public StandardEngine(){
		super();
		log.debug("创建StandardEngineValve");
		pipeline.setBasic(new StandardEngineValve());
	}

	public Service getService() {
		return (this.service);
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Override
	protected void initInternal() throws LifecycleException {
		log.debug("调用父类的initInternal");
		super.initInternal();
	}

	@Override
	protected synchronized void startInternal() throws LifecycleException {
		log.debug("调用父类的startInternal");
		super.startInternal();
	}

	public void setDefaultHost(String host) {
		String oldDefaultHost = this.defaultHost;
        if (host == null) {
            this.defaultHost = null;
        } else {
            this.defaultHost = host.toLowerCase();
        }
        support.firePropertyChange("defaultHost", oldDefaultHost,
                                   this.defaultHost);
		
	}

	public String getDefaultHost() {
		return (defaultHost);
	}

    public String getBaseDir() {
        if( baseDir==null ) {
            baseDir=System.getProperty(Globals.CATALINA_BASE_PROP);
        }
        if( baseDir==null ) {
            baseDir=System.getProperty(Globals.CATALINA_HOME_PROP);
        }
        return baseDir;
    }
}
