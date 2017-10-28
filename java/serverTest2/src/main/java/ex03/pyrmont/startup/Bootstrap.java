package ex03.pyrmont.startup;

import org.apache.catalina.Engine;
import org.apache.catalina.Service;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardService;

import ex03.pyrmont.connector.http.HttpConnector;

public final class Bootstrap {
	public static void main(String[] args) {
		Service service = new StandardService();
		Engine engine = new StandardEngine();
		service.setContainer(engine);
		
		
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}