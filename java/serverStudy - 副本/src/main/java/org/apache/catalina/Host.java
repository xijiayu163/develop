package org.apache.catalina;

import java.util.concurrent.ExecutorService;

public interface Host extends Container {
	public String[] findAliases();

	public void setAppBase(String appBase);

	public String getAppBase();

	public boolean getAutoDeploy();

	public void setAutoDeploy(boolean autoDeploy);
	
	public boolean getCreateDirs();
	
	public String getXmlBase();
	
	public void setXmlBase(String xmlBase);
	
	public boolean getDeployOnStartup();
	
	public void setDeployOnStartup(boolean deployOnStartup);
	
	public ExecutorService getStartStopExecutor();
	
	public String getConfigClass();
	
	public void setConfigClass(String configClass);
}
