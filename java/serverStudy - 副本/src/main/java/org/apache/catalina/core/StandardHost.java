package org.apache.catalina.core;

import java.util.concurrent.ExecutorService;

import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;

public class StandardHost extends ContainerBase implements Host{
	
	private String[] aliases = new String[0];
	 private boolean unpackWARs = true;
	 private String appBase = "webapps";
	 private boolean autoDeploy = true;
	 private boolean copyXML = false;
	 private boolean deployXML = true;
	 private String contextClass =
		        "org.apache.catalina.core.StandardContext";
	 private boolean createDirs = true;
	 private String xmlBase = null;
	 private boolean deployOnStartup = true;
	 private String configClass =
		        "org.apache.catalina.startup.ContextConfig";

    private final Object aliasesLock = new Object();
	private String workDir = null;
	
	public StandardHost(){
		super();
        log.debug("创建StandardHostValve,并设置为pepeline基本阀门");
        pipeline.setBasic(new StandardHostValve());
	}
	
	protected synchronized void startInternal() throws LifecycleException{
		 super.startInternal();
	}

	public String[] findAliases() {
		synchronized (aliasesLock) {
            return (this.aliases);
        }
	}
	
	public void setUnpackWARs(boolean unpackWARs) {
        this.unpackWARs = unpackWARs;
    }
	
    public boolean isUnpackWARs() {
        return (unpackWARs);
    }

	public void setAppBase(String appBase) {
		 String oldAppBase = this.appBase;
        this.appBase = appBase;
        support.firePropertyChange("appBase", oldAppBase, this.appBase);
	}

	public String getAppBase() {
		return (this.appBase);
	}

	public boolean getAutoDeploy() {
		return (this.autoDeploy);
	}

	public void setAutoDeploy(boolean autoDeploy) {
		boolean oldAutoDeploy = this.autoDeploy;
        this.autoDeploy = autoDeploy;
        support.firePropertyChange("autoDeploy", oldAutoDeploy,
                                   this.autoDeploy);
	}

	public boolean isCopyXML() {
		return copyXML;
	}

	public void setCopyXML(boolean copyXML) {
		this.copyXML = copyXML;
	}

	public boolean isDeployXML() {
		return deployXML;
	}

	public void setDeployXML(boolean deployXML) {
		this.deployXML = deployXML;
	}

	public String getContextClass() {
		return contextClass;
	}

	public void setContextClass(String contextClass) {
		this.contextClass = contextClass;
	}

	public boolean isCreateDirs() {
		return createDirs;
	}

	public void setCreateDirs(boolean createDirs) {
		this.createDirs = createDirs;
	}

	public boolean getCreateDirs() {
		return createDirs;
	}

	public String getXmlBase() {
		return (this.xmlBase);
	}

	public void setXmlBase(String xmlBase) {
		String oldXmlBase = this.xmlBase;
        this.xmlBase = xmlBase;
        support.firePropertyChange("xmlBase", oldXmlBase, this.xmlBase);
	}

	public boolean getDeployOnStartup() {
		return (this.deployOnStartup);
	}

	public void setDeployOnStartup(boolean deployOnStartup) {
		boolean oldDeployOnStartup = this.deployOnStartup;
        this.deployOnStartup = deployOnStartup;
        support.firePropertyChange("deployOnStartup", oldDeployOnStartup,
                                   this.deployOnStartup);
	}

	public ExecutorService getStartStopExecutor() {
		return startStopExecutor;
	}

	public String getConfigClass() {
		return (this.configClass);
	}

	public void setConfigClass(String configClass) {
		String oldConfigClass = this.configClass;
        this.configClass = configClass;
        support.firePropertyChange("configClass",
                                   oldConfigClass, this.configClass);
	}

    public String getWorkDir() {
        return (workDir);
    }
    
    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }
}
