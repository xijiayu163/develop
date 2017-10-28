package org.apache.catalina.startup;

import org.apache.catalina.Engine;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EngineConfig implements LifecycleListener{
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	protected Engine engine = null;
	
	public void lifecycleEvent(LifecycleEvent event) {
		log.debug("监听到Engine生命周期事件:"+ event.getLifecycle().getStateName());
		try {
            engine = (Engine) event.getLifecycle();
        } catch (ClassCastException e) {
            return;
        }
		
		if (event.getType().equals(Lifecycle.START_EVENT))
            start();
		else if (event.getType().equals(Lifecycle.STOP_EVENT))
            stop();
	}

	protected void stop() {
		//��־��ӡ
	}

	protected void start() {
		//��־��ӡ
	}
}
