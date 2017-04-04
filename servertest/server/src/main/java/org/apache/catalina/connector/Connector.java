package org.apache.catalina.connector;

import java.io.IOException;

import org.apache.coyote.http11.Http11Protocol;

public class Connector {
	protected CoyoteAdapter adapter = null;
	private Http11Protocol protocolHandler;

	public Connector(){
		protocolHandler = new Http11Protocol();
	}
	
	public void init(){
		adapter = new CoyoteAdapter(this);
        protocolHandler.setAdapter(adapter);
        try {
			protocolHandler.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startInternal(){
		protocolHandler.start();
	}
}
