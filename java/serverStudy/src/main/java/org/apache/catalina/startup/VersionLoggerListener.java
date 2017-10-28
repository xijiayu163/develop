package org.apache.catalina.startup;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class VersionLoggerListener implements LifecycleListener{

	public void lifecycleEvent(LifecycleEvent event) {
		if (Lifecycle.BEFORE_INIT_EVENT.equals(event.getType())) {
            log();
        }
	}

	private void log() {
		
	}
}
