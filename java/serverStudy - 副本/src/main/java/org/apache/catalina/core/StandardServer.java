package org.apache.catalina.core;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.startup.Catalina;
import org.apache.catalina.util.ExtensionValidator;
import org.apache.catalina.util.LifecycleBase;

import sun.util.logging.resources.logging;

public class StandardServer extends LifecycleBase implements Server{
	
	private static final String info =
	        "org.apache.catalina.core.StandardServer/1.0";
	
	//�ر�ʹ�õĶ˿�
	private int port = 8005;
	//�ر�ʹ�õĵ�ַ
	private String address = "localhost";
	//�ر�ʹ�õ�����
	private String shutdown = "SHUTDOWN";
	private ClassLoader parentClassLoader = null;
	PropertyChangeSupport support = new PropertyChangeSupport(this);
	private Service services[] = new Service[0];
    private final Object servicesLock = new Object();
    private Catalina catalina = null;
	
	public String getInfo() {
		return info;
	}

	public int getPort() {
		return (this.port);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAddress() {
		return (this.address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShutdown() {
		return (this.shutdown);
	}

	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}

	public ClassLoader getParentClassLoader() {
		if (parentClassLoader != null)
            return (parentClassLoader);
        return (ClassLoader.getSystemClassLoader());
	}

	public void setParentClassLoader(ClassLoader parent) {
		ClassLoader oldParentClassLoader = this.parentClassLoader;
        this.parentClassLoader = parent;
        support.firePropertyChange("parentClassLoader", oldParentClassLoader,
                                   this.parentClassLoader);
	}

	public void addService(Service service) {
		service.setServer(this);

        synchronized (servicesLock) {
            Service results[] = new Service[services.length + 1];
            System.arraycopy(services, 0, results, 0, services.length);
            results[services.length] = service;
            services = results;

            if (getState().isAvailable()) {
                try {
                    service.start();
                } catch (LifecycleException e) {
                    // Ignore
                }
            }

            // Report this property change to interested listeners
            support.firePropertyChange("service", null, service);
        }
	}

	public void await() {
		try {
			Thread.sleep(1000*1000*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Service findService(String name) {
		if (name == null) {
            return (null);
        }
        synchronized (servicesLock) {
            for (int i = 0; i < services.length; i++) {
                if (name.equals(services[i].getName())) {
                    return (services[i]);
                }
            }
        }
        return (null);
	}

	public Service[] findServices() {
		return (services);
	}

	public void removeService(Service service) {
		synchronized (servicesLock) {
            int j = -1;
            for (int i = 0; i < services.length; i++) {
                if (service == services[i]) {
                    j = i;
                    break;
                }
            }
            if (j < 0)
                return;
            try {
                services[j].stop();
            } catch (LifecycleException e) {
                // Ignore
            }
            int k = 0;
            Service results[] = new Service[services.length - 1];
            for (int i = 0; i < services.length; i++) {
                if (i != j)
                    results[k++] = services[i];
            }
            services = results;

            // Report this property change to interested listeners
            support.firePropertyChange("service", service, null);
        }
	}

	@Override
	/**
	 * ��urlClassLoader�ж�ȡjar���е�manifest��Ϣ����������
	 * ��urlClassLoader������commonLoader��ָ��Ŀ¼��(build/output)
	 */
	protected void initInternal() throws LifecycleException {
		 if (getCatalina() != null) {
			 ClassLoader cl = getCatalina().getParentClassLoader();
			 while (cl != null && cl != ClassLoader.getSystemClassLoader()) {
				 if (cl instanceof URLClassLoader) {
					 URL[] urls = ((URLClassLoader) cl).getURLs();
					 for (URL url : urls) {
						 if (url.getProtocol().equals("file")) {
							try {
								File f = new File(url.toURI());
								if (f.isFile() && f.getName().endsWith(".jar")) {
									ExtensionValidator.addSystemResource(f);
									log.debug("jar资源添加到系统资源,"+ f.getAbsolutePath());
								}
							} catch (URISyntaxException e) {
								// Ignore
							} catch (IOException e) {
								// Ignore
							}
						 }
					 }
				 }
				 cl = cl.getParent();
			 }
		 }
		
		 log.debug("遍历services集合，逐个调用其init方法");
		for (int i = 0; i < services.length; i++) {
			services[i].init();
		}
	}

	@Override
	protected void startInternal() throws LifecycleException {
		log.debug("触发CONFIGURE_START_EVENT事件");
		fireLifecycleEvent(CONFIGURE_START_EVENT, null);
		log.debug("设置当前状态为STARTING");
		setState(LifecycleState.STARTING);
		
		log.debug("遍历services，逐个启动...");
		synchronized (servicesLock) {
            for (int i = 0; i < services.length; i++) {
                services[i].start();
            }
        }
	}

	@Override
	protected void stopInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	public Catalina getCatalina() {
		return catalina;
	}

	public void setCatalina(Catalina catalina) {
		this.catalina = catalina;
	}
	
}
