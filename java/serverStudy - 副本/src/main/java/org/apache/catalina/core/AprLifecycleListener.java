package org.apache.catalina.core;

import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class AprLifecycleListener implements LifecycleListener{

	protected static String SSLEngine = "on";
	
    public String getSSLEngine() {
        return SSLEngine;
    }

    public void setSSLEngine(String SSLEngine) {
        if (!SSLEngine.equals(AprLifecycleListener.SSLEngine)) {
            AprLifecycleListener.SSLEngine = SSLEngine;
        }
    }
	
	public void lifecycleEvent(LifecycleEvent event) {
		
	}

}
