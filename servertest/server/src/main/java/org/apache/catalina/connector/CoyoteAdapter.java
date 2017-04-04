package org.apache.catalina.connector;

public class CoyoteAdapter {
	private Connector connector = null;
	 
	public CoyoteAdapter(Connector connector) {
        this.connector = connector;

    }
}
