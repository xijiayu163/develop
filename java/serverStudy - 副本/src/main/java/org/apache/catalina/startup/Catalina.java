package org.apache.catalina.startup;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.JasperListener;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.util.logging.resources.logging;

public class Catalina {
	
	protected ClassLoader parentClassLoader = Catalina.class.getClassLoader();
	private Server server = null;
	
	private Log log = LogFactory.getLog(this.getClass());
	
    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
	
    public void setParentClassLoader(ClassLoader parentClassLoader) {
        this.parentClassLoader = parentClassLoader;
    }
	
	public ClassLoader getParentClassLoader() {
        if (parentClassLoader != null) {
            return (parentClassLoader);
        }
        return ClassLoader.getSystemClassLoader();
    }
	
	public void load(){
		log.debug("Catalina 加载资源");
		initDirs();
		Server server = new StandardServer();
		server.setShutdown("SHUTDOWN");
		setServer(server);
		log.debug("创建StandardServer,关联到catalina");
		
		server.addLifecycleListener(new VersionLoggerListener());
		AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
		aprLifecycleListener.setSSLEngine("on");
		server.addLifecycleListener(aprLifecycleListener);
		server.addLifecycleListener(new JasperListener());
		server.addLifecycleListener(new JreMemoryLeakPreventionListener());
		server.addLifecycleListener(new ThreadLocalLeakPreventionListener());
		log.debug("设置server生命周期监听器:VersionLoggerListener,AprLifecycleListener,JasperListener,JreMemoryLeakPreventionListener,ThreadLocalLeakPreventionListener");
		
		Service service = new StandardService();
		service.setName("Catalina");
		server.addService(service);
		log.debug("创建StandardService,将StandardService添加到StandardServer的services中，并将StandardServer关联到该service");
		
		Connector connector = new Connector("HTTP/1.1");
		connector.setPort(8081);
		connector.setRedirectPort(8443);
		connector.setProtocol("HTTP/1.1");
		service.addConnector(connector);
		log.debug("创建connector,设置端口8080，重定向端口8443，协议为HTTP/1.1,并添加到StandardService的connectors数组中");
		
		Engine engine = new StandardEngine();
		engine.setName("Catalina");
		engine.setDefaultHost("localhost");
		EngineConfig engineConfig = new EngineConfig();
		engine.addLifecycleListener(engineConfig);
		engine.setParentClassLoader(this.getParentClassLoader());
		engine.setBackgroundProcessorDelay(10);
		service.setContainer(engine);
		log.debug("创建StandardEngine,设置默认主机localhost,创建EngineConfig，并作为生命周期监听器添加到StandardEngine");
		log.debug("设置StandardService容器为StandardEngine");
		
		StandardHost standardHost=new StandardHost();
		standardHost.setName("localhost");
		standardHost.setAppBase("webapps");
		standardHost.setUnpackWARs(true);
		standardHost.setAutoDeploy(true);
		HostConfig hostConfig = new HostConfig();
		standardHost.addLifecycleListener(hostConfig);
		engine.addChild(standardHost);
		standardHost.setParentClassLoader(standardHost.getParentClassLoader());
		log.debug("创建StandardHost,设置名字为localhost,appbase为webapps,unpackWARs为false,autoDeploy为true,父加载器为standardHost的父加载器,这里为shareLoader,创建HostConfig，并作为生命周期监听器添加到standardHost");
		
		AccessLogValve accessLogValve = new AccessLogValve();
		accessLogValve.setDirectory("logs");
		accessLogValve.setPrefix("localhost_access_log");
		accessLogValve.setSuffix(".txt");
		accessLogValve.setPattern("%h %l %u %t &quot;%r&quot; %s %b");
		standardHost.addValve(accessLogValve);
		log.debug("创建AccessLogValve，并添加到standardHost的valves中");
		
		getServer().setCatalina(this);
		log.debug("将catalina关联到server");
		
		try {
			log.debug("servr开始初始化");
			getServer().init();
			log.debug("servr初始化完毕");
		} catch (LifecycleException e) {
		}
	}
	

	private void initDirs() {
		log.debug("初始化目录");
		String catalinaHome = System.getProperty(Globals.CATALINA_HOME_PROP);
		if (catalinaHome != null) {
            File home = new File(catalinaHome);
            if (!home.isAbsolute()) {
                try {
                    catalinaHome = home.getCanonicalPath();
                } catch (IOException e) {
                    catalinaHome = home.getAbsolutePath();
                }
            }
            System.setProperty(Globals.CATALINA_HOME_PROP, catalinaHome);
        }
		log.debug("初始化目录,修正catalinaHome路径为绝对路径,"+catalinaHome);
	}
	
	public void start() {
		try {
			getServer().start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		await();
	}
	
    public void await() {
        getServer().await();
    }
}
