package org.apache.catalina;

public interface Engine extends Container{
	 public Service getService();
	 
	 public void setService(Service service);
	 
	 public void setDefaultHost(String defaultHost);
	 
	 public String getDefaultHost();
}
