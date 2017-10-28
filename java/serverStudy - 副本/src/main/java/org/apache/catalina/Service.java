package org.apache.catalina;

import org.apache.catalina.connector.Connector;

public interface Service extends Lifecycle{
	public Container getContainer();
	
	//һ����Engine
	public void setContainer(Container container);
	
	public void addConnector(Connector connector);

	public void setServer(Server server);
	
	public Server getServer();
	
	public String getName();
	
	public void setName(String name);

	public Connector[] findConnectors();
	
}
