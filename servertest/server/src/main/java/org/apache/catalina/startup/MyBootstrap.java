package org.apache.catalina.startup;

import org.apache.catalina.connector.Connector;

public class MyBootstrap {
	public static void main(String[] args){
		Connector connector = new Connector();
		connector.init();
		connector.startInternal();
	}
}
