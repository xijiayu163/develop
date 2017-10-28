package org.apache.catalina.startup;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.util.ContextName;
import org.apache.catalina.util.IOTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.util.logging.resources.logging;

public class HostConfig implements LifecycleListener{
	
	protected Host host = null;
	
	private boolean copyXML = false;
	private boolean deployXML = false;
	protected boolean unpackWARs = false;
	private String contextClass =
	        "org.apache.catalina.core.StandardContext";
	protected File appBase = null;
	protected File configBase = null;
	private boolean deployOnStartup = true;
	protected PropertyChangeSupport support = new PropertyChangeSupport(this);
	protected ArrayList<String> serviced = new ArrayList<String>();
	protected Map<String, DeployedApplication> deployed =
	        new ConcurrentHashMap<String, DeployedApplication>();
	private final Object digesterLock = new Object();
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public boolean isCopyXML() {
        return (this.copyXML);
    }
	
	public void lifecycleEvent(LifecycleEvent event) {
		log.debug("监听到Host生命周期事件:"+ event.getLifecycle().getStateName());
		try {
            host = (Host) event.getLifecycle();
            if (host instanceof StandardHost) {
                setCopyXML(((StandardHost) host).isCopyXML());
                setDeployXML(((StandardHost) host).isDeployXML());
                setUnpackWARs(((StandardHost) host).isUnpackWARs());
                setContextClass(((StandardHost) host).getContextClass());
            }
        } catch (ClassCastException e) {
            return;
        }
		
		if (event.getType().equals(Lifecycle.PERIODIC_EVENT)) {
			log.debug("监听到Host生命周期事件:periodic,调用check");
            check();
        } else if (event.getType().equals(Lifecycle.BEFORE_START_EVENT)) {
        	log.debug("监听到Host生命周期事件:before_start,调用beforeStart");
            beforeStart();
        } else if (event.getType().equals(Lifecycle.START_EVENT)) {
        	log.debug("监听到Host生命周期事件:start,调用start部署apps");
            start();
        } else if (event.getType().equals(Lifecycle.STOP_EVENT)) {
            stop();
        }
	}
	
	private void stop() {
		// TODO Auto-generated method stub
	}

	private void start() {
		if (host.getDeployOnStartup())
            deployApps();
	}
	
	protected void deployApps() {
		log.debug("开始部署...");
		//F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src\output\build\webapps
        File appBase = appBase();
        log.debug("获取appBase目录:"+appBase.getAbsolutePath());
        //F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src\output\build\conf\Catalina\localhost
        File configBase = configBase();
        log.debug("获取configBase目录:"+configBase.getAbsolutePath());
        String[] filteredAppPaths = filterAppPaths(appBase.list());
        for(String path:filteredAppPaths){
        	log.debug("获取过滤后的app目录:"+path);
        }
        // Deploy XML descriptors from configBase
        log.debug("根据configBase部署xml描述符...");
        deployDescriptors(configBase, configBase.list());
        // Deploy WARs
        log.debug("根据appBase和filteredAppPaths部署war包...");
        deployWARs(appBase, filteredAppPaths);
        // Deploy expanded folders
        log.debug("根据appBase和filteredAppPaths部署已解压的app目录");
        deployDirectories(appBase, filteredAppPaths);

    }

	private void deployDirectories(File appBase, String[] files) {
		if (files == null)
            return;

        ExecutorService es = host.getStartStopExecutor();
        List<Future<?>> results = new ArrayList<Future<?>>();

        for (int i = 0; i < files.length; i++) {

            if (files[i].equalsIgnoreCase("META-INF"))
                continue;
            if (files[i].equalsIgnoreCase("WEB-INF"))
                continue;
            File dir = new File(appBase, files[i]);
            if (dir.isDirectory()) {
                ContextName cn = new ContextName(files[i], false);

                if (isServiced(cn.getName()) || deploymentExists(cn.getName()))
                    continue;
                log.debug("使用ExecutorService多线程执行执行DeployDirectory");
                results.add(es.submit(new DeployDirectory(this, cn, dir)));
            }
        }
        
        log.debug("阻塞等待所有的目录部署完毕...");
        for (Future<?> result : results) {
            try {
                result.get();
            } catch (Exception e) {
            }
        }
	}

	private boolean deploymentExists(String contextName) {
		return (deployed.containsKey(contextName) ||
                (host.findChild(contextName) != null));
	}

	private boolean isServiced(String name) {
		return (serviced.contains(name));
	}

	private void deployWARs(File appBase, String[] files) {
		//����war��
	}

	private void deployDescriptors(File configBase2, String[] list) {
		//����xml�����ļ�F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src\output\build\conf\Catalina\localhost
	}

	private String[] filterAppPaths(String[] unfilteredAppPaths) {
	     return unfilteredAppPaths;
	}

	private void check() {
		
	}
	
	public void beforeStart() {
		
	}
	
	protected File appBase() {
        if (appBase != null) {
            return appBase;
        }

        appBase = returnCanonicalPath(host.getAppBase());
        return appBase;
    }
	
	protected File configBase() {
        if (configBase != null) {
            return configBase;
        }

        if (host.getXmlBase()!=null) {
            configBase = returnCanonicalPath(host.getXmlBase());
        } else {
            StringBuilder xmlDir = new StringBuilder("conf");
            Container parent = host.getParent();
            if (parent instanceof Engine) {
                xmlDir.append('/');
                xmlDir.append(parent.getName());
            }
            xmlDir.append('/');
            xmlDir.append(host.getName());
            configBase = returnCanonicalPath(xmlDir.toString());
        }
        return (configBase);
    }

	private File returnCanonicalPath(String path) {
		File file = new File(path);
        File base = new File(System.getProperty(Globals.CATALINA_BASE_PROP));
        if (!file.isAbsolute())
            file = new File(base,path);
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            return file;
        }
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public void setCopyXML(boolean copyXML) {
		this.copyXML = copyXML;
	}

	public boolean isDeployXML() {
		return deployXML;
	}

	public void setDeployXML(boolean deployXML) {
		this.deployXML = deployXML;
	}

	public boolean isUnpackWARs() {
		return unpackWARs;
	}

	public void setUnpackWARs(boolean unpackWARs) {
		this.unpackWARs = unpackWARs;
	}

	public String getContextClass() {
		return contextClass;
	}

	public void setContextClass(String contextClass) {
		this.contextClass = contextClass;
	}
	
    public boolean getDeployOnStartup() {

        return (this.deployOnStartup);

    }
    
    public void setDeployOnStartup(boolean deployOnStartup) {

        boolean oldDeployOnStartup = this.deployOnStartup;
        this.deployOnStartup = deployOnStartup;
        support.firePropertyChange("deployOnStartup", oldDeployOnStartup,
                                   this.deployOnStartup);
    }
    
    protected void deployDirectory(ContextName cn, File dir) {
    	log.debug("开始部署APP目录,cn.name:"+cn.getBaseName()+" 目录路径："+dir.getAbsolutePath());
        Context context = null;
        File xml = new File(dir, Constants.ApplicationContextXml);
        File xmlCopy = new File(configBase(), cn.getBaseName() + ".xml");

        DeployedApplication deployedApp;
        boolean copyThisXml = copyXML;

        try {
            if (deployXML && xml.exists()) {
                synchronized (digesterLock) {
                    try {
                        context = new StandardContext();
                    } catch (Exception e) {
                    }
                }

                if (copyThisXml == false && context instanceof StandardContext) {
                    // Host is using default value. Context may override it.
                    copyThisXml = ((StandardContext) context).getCopyXML();
                }

                if (copyThisXml) {
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = new FileInputStream(xml);
                        os = new FileOutputStream(xmlCopy);
                        IOTools.flow(is, os);
                        // Don't catch IOE - let the outer try/catch handle it
                    } finally {
                        try {
                            if (is != null) is.close();
                        } catch (IOException e){
                            // Ignore
                        }
                        try {
                            if (os != null) os.close();
                        } catch (IOException e){
                            // Ignore
                        }
                    }
                    context.setConfigFile(xmlCopy.toURI().toURL());
                } else {
                    context.setConfigFile(xml.toURI().toURL());
                }
            } else {
                context = (Context) Class.forName(contextClass).newInstance();
            }

            Class<?> clazz = Class.forName(host.getConfigClass());
            LifecycleListener listener =
                (LifecycleListener) clazz.newInstance();
            context.addLifecycleListener(listener);

            context.setName(cn.getName());
            context.setPath(cn.getPath());
            context.setWebappVersion(cn.getVersion());
            context.setDocBase(cn.getBaseName());
            host.addChild(context);
        } catch (Throwable t) {
        	log.error("部署目录时发生异常",t);
        } finally {
            deployedApp = new DeployedApplication(cn.getName(),
                    xml.exists() && deployXML && copyThisXml);

            // Fake re-deploy resource to detect if a WAR is added at a later
            // point
            deployedApp.redeployResources.put(dir.getAbsolutePath() + ".war",
                    Long.valueOf(0));
            deployedApp.redeployResources.put(dir.getAbsolutePath(),
                    Long.valueOf(dir.lastModified()));
            if (deployXML && xml.exists()) {
                if (copyThisXml) {
                    deployedApp.redeployResources.put(
                            xmlCopy.getAbsolutePath(),
                            Long.valueOf(xmlCopy.lastModified()));
                } else {
                    deployedApp.redeployResources.put(
                            xml.getAbsolutePath(),
                            Long.valueOf(xml.lastModified()));
                    // Fake re-deploy resource to detect if a context.xml file is
                    // added at a later point
                    deployedApp.redeployResources.put(
                            xmlCopy.getAbsolutePath(),
                            Long.valueOf(0));
                }
            } else {
                // Fake re-deploy resource to detect if a context.xml file is
                // added at a later point
                deployedApp.redeployResources.put(
                        xmlCopy.getAbsolutePath(),
                        Long.valueOf(0));
                if (!xml.exists()) {
                    deployedApp.redeployResources.put(
                            xml.getAbsolutePath(),
                            Long.valueOf(0));
                }
            }
            addWatchedResources(deployedApp, dir.getAbsolutePath(), context);
            // Add the global redeploy resources (which are never deleted) at
            // the end so they don't interfere with the deletion process
            addGlobalRedeployResources(deployedApp);
        }

        deployed.put(cn.getName(), deployedApp);
    }
    
    protected void addWatchedResources(DeployedApplication app, String docBase,
            Context context) {
    	 File docBaseFile = null;
         if (docBase != null) {
             docBaseFile = new File(docBase);
             if (!docBaseFile.isAbsolute()) {
                 docBaseFile = new File(appBase(), docBase);
             }
         }
         String[] watchedResources = context.findWatchedResources();
         for (int i = 0; i < watchedResources.length; i++) {
             File resource = new File(watchedResources[i]);
             if (!resource.isAbsolute()) {
                 if (docBase != null) {
                     resource = new File(docBaseFile, watchedResources[i]);
                 } else {
                     continue;
                 }
             }
             app.reloadResources.put(resource.getAbsolutePath(),
                     Long.valueOf(resource.lastModified()));
         }
    }
    
    protected void addGlobalRedeployResources(DeployedApplication app) {
        // Redeploy resources processing is hard-coded to never delete this file
        File hostContextXml =
                new File(getConfigBaseName(), Constants.HostContextXml);
        if (hostContextXml.isFile()) {
            app.redeployResources.put(hostContextXml.getAbsolutePath(),
                    Long.valueOf(hostContextXml.lastModified()));
        }

        // Redeploy resources in CATALINA_BASE/conf are never deleted
        File globalContextXml =
                returnCanonicalPath(Constants.DefaultContextXml);
        if (globalContextXml.isFile()) {
            app.redeployResources.put(globalContextXml.getAbsolutePath(),
                    Long.valueOf(globalContextXml.lastModified()));
        }
    }
    
    public String getConfigBaseName() {
        return configBase().getAbsolutePath();
    }
    
    protected static class DeployedApplication {
    	
    	 public String name;
    	 public final boolean hasDescriptor;
    	 public LinkedHashMap<String, Long> redeployResources =
    	            new LinkedHashMap<String, Long>();
    	 
    	 public HashMap<String, Long> reloadResources =
    	            new HashMap<String, Long>();
    	
    	 public DeployedApplication(String name, boolean hasDescriptor) {
    		 this.name = name;
             this.hasDescriptor = hasDescriptor;
    	 }
    }
    
    private static class DeployDirectory implements Runnable {
    	
    	 private HostConfig config;
         private ContextName cn;
         private File dir;
    	
    	public DeployDirectory(HostConfig config, ContextName cn, File dir) {
            this.config = config;
            this.cn = cn;
            this.dir = dir;
        }
    	
		public void run() {
			config.deployDirectory(cn, dir);
		}
    }
}
