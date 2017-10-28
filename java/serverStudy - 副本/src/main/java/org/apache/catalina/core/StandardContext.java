package org.apache.catalina.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.naming.directory.DirContext;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.InstanceManager;
import org.apache.catalina.JarScanner;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Wrapper;
import org.apache.catalina.deploy.ApplicationListener;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.util.URLEncoder;
import org.apache.naming.resources.BaseDirContext;
import org.apache.naming.resources.DirContextURLStreamHandler;
import org.apache.naming.resources.EmptyDirContext;
import org.apache.naming.resources.FileDirContext;
import org.apache.naming.resources.ProxyDirContext;
import org.apache.naming.resources.WARDirContext;
import org.apache.tomcat.util.scan.StandardJarScanner;

public class StandardContext extends ContainerBase implements Context{

	private boolean copyXML = false;
	private String webappVersion = "";
	private URL configFile = null;
	private String docBase = null;
	private final Object watchedResourcesLock = new Object();
	private String watchedResources[] = new String[0];
	private boolean configured = false;
	private DirContext webappResources = null;
	private boolean cachingAllowed = true;
	protected int cacheTTL = 5000;
	protected int cacheMaxSize = 10240; // 10 MB
	protected int cacheObjectMaxSize = 512; // 512K
	private String aliases = null;
	protected boolean allowLinking = false;
	private boolean filesystemBased = false;
	private int effectiveMajorVersion = 3;
	private boolean addWebinfClassesResources = false;
	private boolean delegate = false;
	private String workDir = null;
	protected ApplicationContext context = null;
	private String altDDName = null;
	private String welcomeFiles[] = new String[0];
	private String path = null;
	private org.apache.tomcat.util.http.mapper.Mapper mapper =
        new org.apache.tomcat.util.http.mapper.Mapper();
	private JarScanner jarScanner = null;
	private final ConcurrentMap<String, String> parameters = new ConcurrentHashMap<String, String>();
	private Map<ServletContainerInitializer,Set<Class<?>>> initializers =
        new LinkedHashMap<ServletContainerInitializer,Set<Class<?>>>();
	private String encodedPath = null;
	private final Object applicationParametersLock = new Object();
	private ApplicationParameter applicationParameters[] =
	        new ApplicationParameter[0];
	private HashMap<String, ApplicationFilterConfig> filterConfigs =
        new HashMap<String, ApplicationFilterConfig>();
	private HashMap<String, FilterDef> filterDefs =
        new HashMap<String, FilterDef>();
	private InstanceManager instanceManager = null;
	private ApplicationListener applicationListeners[] =
            new ApplicationListener[0];
	private final Set<Object> noPluggabilityListeners = new HashSet<Object>();
	private Object applicationEventListenersObjects[] = new Object[0];
	private Object applicationLifecycleListenersObjects[] =
        new Object[0];
	private NoPluggabilityServletContext noPluggabilityServletContext = null;
	private String defaultWebXml;
	private boolean reloadable = false;
	private String compilerClasspath = null;
	private boolean antiJARLocking = false;
	private boolean clearReferencesRmiTargets = true;
	private boolean clearReferencesStatic = false;
	private boolean clearReferencesStopThreads = false;
	private boolean clearReferencesStopTimerThreads = false;
	private boolean clearReferencesHttpClientKeepAliveThread = true;
	private String sessionCookieName;
	private String sessionCookieDomain;
	private boolean useHttpOnly = true;
	private String sessionCookiePath;
	private boolean sessionCookiePathUsesTrailingSlash = false;
	private boolean cookies = true;
	private boolean crossContext = false;
	private boolean tldValidation = Globals.STRICT_SERVLET_COMPLIANCE;
	private boolean xmlBlockExternal = true;
	private HashMap<String, String> mimeMappings =
	        new HashMap<String, String>();
	protected static URLEncoder urlEncoder;
	
	static {
        urlEncoder = new URLEncoder();
        urlEncoder.addSafeCharacter('~');
        urlEncoder.addSafeCharacter('-');
        urlEncoder.addSafeCharacter('_');
        urlEncoder.addSafeCharacter('.');
        urlEncoder.addSafeCharacter('*');
        urlEncoder.addSafeCharacter('/');
    }
	
	public StandardContext(){
		super();
        pipeline.setBasic(new StandardContextValve());
	}
	
	public String getPath() {
		return (path);
	}

	public void setPath(String path) {
		boolean invalid = false;
        if (path == null || path.equals("/")) {
            invalid = true;
            this.path = "";
        } else if ("".equals(path) || path.startsWith("/")) {
            this.path = path;
        } else {
            invalid = true;
            this.path = "/" + path;
        }
        if (this.path.endsWith("/")) {
            invalid = true;
            this.path = this.path.substring(0, this.path.length() - 1);
        }
        if (invalid) {
        }
        encodedPath = urlEncoder.encode(this.path, "UTF-8");
        if (getName() == null) {
            setName(this.path);
        }
	}

	public String[] findWelcomeFiles() {
		return null;
	}

	public boolean isResourceOnlyServlet(String wrapperName) {
		return false;
	}

	public boolean getMapperContextRootRedirectEnabled() {
		return false;
	}

	public boolean getMapperDirectoryRedirectEnabled() {
		return false;
	}

	public boolean fireRequestInitEvent(HttpServletRequest request) {
		return false;
	}
	
	public boolean getCopyXML() {
        return copyXML;
    }


    public void setCopyXML(boolean copyXML) {
        this.copyXML = copyXML;
    }

	public void setWebappVersion(String webappVersion) {
		if (null == webappVersion) {
            this.webappVersion = "";
        } else {
            this.webappVersion = webappVersion;
        }
	}
	
	public String getWebappVersion() {
		return webappVersion;
	}

	public void setConfigFile(URL configFile) {
		this.configFile = configFile;
	}

	public URL getConfigFile() {
		return (this.configFile);
	}

	public void setDocBase(String docBase) {
		this.docBase = docBase;
	}

	public String getDocBase() {
		return (this.docBase);
	}

	public String[] findWatchedResources() {
		synchronized (watchedResourcesLock) {
            return watchedResources;
        }
	}
	
    @Override
    protected void initInternal() throws LifecycleException {
        super.initInternal();
    }
	
	protected synchronized void startInternal() throws LifecycleException {
		setConfigured(false);
		boolean ok = true;
		if (webappResources == null){
			try {
                String docBase = getDocBase();
                if (docBase == null) {
                    setResources(new EmptyDirContext());
                } else if (docBase.endsWith(".war")	
                        && !(new File(getBasePath())).isDirectory()) {
                    setResources(new WARDirContext());
                } else {
                    setResources(new FileDirContext());
                }
            } catch (IllegalArgumentException e) {
                ok = false;
            }
		}
		if (ok) {
            if (!resourcesStart()) {
                throw new LifecycleException("Error in resourceStart()");
            }
        }
		
		if (getLoader() == null) {
            WebappLoader webappLoader = new WebappLoader(getParentClassLoader());
            webappLoader.setDelegate(getDelegate());
            setLoader(webappLoader);
        }
		
		postWorkDirectory();
		
		ClassLoader oldCCL = bindThread();
		
		try {
			if (ok) {
				if ((loader != null) && (loader instanceof Lifecycle))
                    ((Lifecycle) loader).start();
				unbindThread(oldCCL);
				oldCCL = bindThread();
				
				if ((resources != null) && (resources instanceof Lifecycle))
	                ((Lifecycle) resources).start();
				
				fireLifecycleEvent(Lifecycle.CONFIGURE_START_EVENT, null);
				
				for (Container child : findChildren()) {
	                if (!child.getState().isAvailable()) {
	                    child.start();
	                }
	            }
				
				if (pipeline instanceof Lifecycle) {
	                ((Lifecycle) pipeline).start();
	            }
			}
		} finally {
			// Unbinding thread
			unbindThread(oldCCL);
		}
		
		if (!getConfigured()) {
            ok = false;
        }
		
		if (ok)
            getServletContext().setAttribute
                (Globals.RESOURCES_ATTR, getResources());
		
		mapper.setContext(getPath(), welcomeFiles, resources);
		
		oldCCL = bindThread();
		
		try{
			if (ok) {
                getServletContext().setAttribute(
                        JarScanner.class.getName(), getJarScanner());
            }
			
			mergeParameters();
			
			for (Map.Entry<ServletContainerInitializer, Set<Class<?>>> entry :
                initializers.entrySet()) {
				try {
                    entry.getKey().onStartup(entry.getValue(),
                            getServletContext());
                } catch (ServletException e) {
                    ok = false;
                    break;
                }
			}
			
			if (ok) {
                if (!listenerStart()) {
                    ok = false;
                }
            }
			
			if (ok) {
                if (!filterStart()) {
                    ok = false;
                }
            }
			
			if (ok) {
                if (!loadOnStartup(findChildren())){
                    ok = false;
                }
            }
			
			super.threadStart();
		}finally {
            unbindThread(oldCCL);
        }
		
		if (getLoader() instanceof WebappLoader) {
            ((WebappLoader) getLoader()).closeJARs(true);
        }
		
		if (!ok) {
            setState(LifecycleState.FAILED);
        } else {
            setState(LifecycleState.STARTING);
        }
	}
	
    private boolean loadOnStartup(Container[] children) {
    	TreeMap<Integer, ArrayList<Wrapper>> map =
                new TreeMap<Integer, ArrayList<Wrapper>>();
    	for (int i = 0; i < children.length; i++) {
    		Wrapper wrapper = (Wrapper) children[i];
    		 int loadOnStartup = wrapper.getLoadOnStartup();
    		 if (loadOnStartup < 0)
                 continue;
    		 Integer key = Integer.valueOf(loadOnStartup);
    		 ArrayList<Wrapper> list = map.get(key);
    		 if (list == null) {
                 list = new ArrayList<Wrapper>();
                 map.put(key, list);
    		 }
    		 list.add(wrapper);
    	}
    	
    	 for (ArrayList<Wrapper> list : map.values()) {
    		 for (Wrapper wrapper : list) {
    			 try {
                     wrapper.load();
                 } catch (ServletException e) {
                     
                 }
    		 }
    	 }
    	
    	 return true;
	}

	private boolean filterStart() {
		 boolean ok = true;
		 
		 synchronized (filterConfigs) {
			 filterConfigs.clear();
			 for (Entry<String, FilterDef> entry : filterDefs.entrySet()) {
				 String name = entry.getKey();
				 ApplicationFilterConfig filterConfig = null;
				 try {
	                    filterConfig =
	                        new ApplicationFilterConfig(this, entry.getValue());
	                    filterConfigs.put(name, filterConfig);
	                } catch (Throwable t) {
	                    ok = false;
	                }
			 }
		 }
		 
		 return ok;
	}

	private boolean listenerStart() {
		ApplicationListener listeners[] = applicationListeners;
		Object results[] = new Object[listeners.length];
		boolean ok = true;
		for (int i = 0; i < results.length; i++) {
			try {
                ApplicationListener listener = listeners[i];
                results[i] = getInstanceManager().newInstance(
                        listener.getClassName());
                if (listener.isPluggabilityBlocked()) {
                    noPluggabilityListeners.add(results[i]);
                }
            } catch (Throwable t) {
            	ok = false;
            }
		}
		
		if (!ok) {
			return false;
		}
		
		ArrayList<Object> eventListeners = new ArrayList<Object>();
		ArrayList<Object> lifecycleListeners = new ArrayList<Object>();
		for (int i = 0; i < results.length; i++) {
			if ((results[i] instanceof ServletContextAttributeListener)
				|| (results[i] instanceof ServletRequestAttributeListener)
				|| (results[i] instanceof ServletRequestListener)
				|| (results[i] instanceof HttpSessionAttributeListener)) {
				eventListeners.add(results[i]);
			}
			if ((results[i] instanceof ServletContextListener)
	                || (results[i] instanceof HttpSessionListener)) {
	            lifecycleListeners.add(results[i]);
	        }
		}
		
		for (Object eventListener: getApplicationEventListeners()) {
            eventListeners.add(eventListener);
        }
		setApplicationEventListeners(eventListeners.toArray());
		for (Object lifecycleListener: getApplicationLifecycleListeners()) {
			lifecycleListeners.add(lifecycleListener);
			if (lifecycleListener instanceof ServletContextListener) {
                noPluggabilityListeners.add(lifecycleListener);
            }
		}
		setApplicationLifecycleListeners(lifecycleListeners.toArray()); 
		
		getServletContext();
		context.setNewServletContextListenerAllowed(false);
		
		Object instances[] = getApplicationLifecycleListeners();
		if (instances == null || instances.length == 0) {
            return ok;
        }
		
		ServletContextEvent event = new ServletContextEvent(getServletContext());
		ServletContextEvent tldEvent = null;
		if (noPluggabilityListeners.size() > 0) {
			 noPluggabilityServletContext = new NoPluggabilityServletContext(getServletContext());
			 tldEvent = new ServletContextEvent(noPluggabilityServletContext);
		}
		for (int i = 0; i < instances.length; i++) {
			if (instances[i] == null)
                continue;
			if (!(instances[i] instanceof ServletContextListener))
                continue;
			
			ServletContextListener listener =
	                (ServletContextListener) instances[i];
			try {
                fireContainerEvent("beforeContextInitialized", listener);
                if (noPluggabilityListeners.contains(listener)) {
                    listener.contextInitialized(tldEvent);
                } else {
                    listener.contextInitialized(event);
                }
                fireContainerEvent("afterContextInitialized", listener);
            } catch (Throwable t) {
                ok = false;
            }
		}
		return (ok);
	}

	private void mergeParameters() {
    	Map<String,String> mergedParams = new HashMap<String,String>();
    	String names[] = findParameters();
        for (int i = 0; i < names.length; i++) {
            mergedParams.put(names[i], findParameter(names[i]));
        }
    	
        ApplicationParameter params[] = findApplicationParameters();
        for (int i = 0; i < params.length; i++) {
            if (params[i].getOverride()) {
                if (mergedParams.get(params[i].getName()) == null) {
                    mergedParams.put(params[i].getName(),
                            params[i].getValue());
                }
            } else {
                mergedParams.put(params[i].getName(), params[i].getValue());
            }
        }
        
        ServletContext sc = getServletContext();
        for (Map.Entry<String,String> entry : mergedParams.entrySet()) {
            sc.setInitParameter(entry.getKey(), entry.getValue());
        }
	}
    
    public String findParameter(String name) {
        synchronized (parameters) {
            return (parameters.get(name));
        }
    }
    
    public String[] findParameters() {
        synchronized (parameters) {
            String results[] = new String[parameters.size()];
            return (parameters.keySet().toArray(results));
        }
    }

	public JarScanner getJarScanner() {
        if (jarScanner == null) {
            jarScanner = new StandardJarScanner();
        }
        return jarScanner;
    }


    public void setJarScanner(JarScanner jarScanner) {
        this.jarScanner = jarScanner;
    }
	
	private void unbindThread(ClassLoader oldContextClassLoader) {
		DirContextURLStreamHandler.unbindThread();

        Thread.currentThread().setContextClassLoader(oldContextClassLoader);
	}

	protected ClassLoader bindThread() {
		ClassLoader oldContextClassLoader =
	            Thread.currentThread().getContextClassLoader();
		if (getResources() == null)
            return oldContextClassLoader;
		
		if (getLoader() != null && getLoader().getClassLoader() != null) {
            Thread.currentThread().setContextClassLoader
                (getLoader().getClassLoader());
        }
		
		DirContextURLStreamHandler.bindThread(getResources());
		
		return oldContextClassLoader;
	}

	private void postWorkDirectory() {
    	String workDir = getWorkDir();
    	if (workDir == null || workDir.length() == 0) {
    		 String hostName = null;
    		 String engineName = null;
             String hostWorkDir = null;
             Container parentHost = getParent();
             if (parentHost != null) {
            	 hostName = parentHost.getName();
            	 if (parentHost instanceof StandardHost) {
                     hostWorkDir = ((StandardHost)parentHost).getWorkDir();
                 }
            	 Container parentEngine = parentHost.getParent();
            	 if (parentEngine != null) {
                     engineName = parentEngine.getName();
                  }
             }
             if ((hostName == null) || (hostName.length() < 1))
                 hostName = "_";
             if ((engineName == null) || (engineName.length() < 1))
                 engineName = "_";
             String temp = getName();
             if (temp.startsWith("/"))
                 temp = temp.substring(1);
             temp = temp.replace('/', '_');
             temp = temp.replace('\\', '_');
             if (temp.length() < 1)
                 temp = "_";
             if (hostWorkDir != null ) {
                 workDir = hostWorkDir + File.separator + temp;
             } else {
                 workDir = "work" + File.separator + engineName +
                     File.separator + hostName + File.separator + temp;
             }
             setWorkDir(workDir);
             log.debug("设置工作目录:"+workDir);
    	}
    	
    	File dir = new File(workDir);
    	if (!dir.isAbsolute()) {
            File catalinaHome = engineBase();
            String catalinaHomePath = null;
            try {
                catalinaHomePath = catalinaHome.getCanonicalPath();
                dir = new File(catalinaHomePath, workDir);
            } catch (IOException e) {
            }
        }
    	if (!dir.mkdirs() && !dir.isDirectory()) {
        }
    	
    	if (context == null) {
            getServletContext();
        }
    	context.setAttribute(ServletContext.TEMPDIR, dir);
    	log.debug("设置javax.servlet.context.tempdir目录:"+dir.getAbsolutePath());
        context.setAttributeReadOnly(ServletContext.TEMPDIR);
	}
   
    
    public String getWorkDir() {
        return (this.workDir);
    }
    
    public void setWorkDir(String workDir) {
        this.workDir = workDir;
        if (getState().isAvailable()) {
            postWorkDirectory();
        }
    }

	public boolean getDelegate() {
        return (this.delegate);
    }
    
    public void setDelegate(boolean delegate) {
        boolean oldDelegate = this.delegate;
        this.delegate = delegate;
        support.firePropertyChange("delegate", oldDelegate,
                                   this.delegate);
    }
	
	private boolean resourcesStart() {
		boolean ok = true;
		Hashtable<String, String> env = new Hashtable<String, String>();
		if (getParent() != null)
            env.put(ProxyDirContext.HOST, getParent().getName());
		env.put(ProxyDirContext.CONTEXT, getName());
		try {
			ProxyDirContext proxyDirContext =
	                new ProxyDirContext(env, webappResources);
			if (webappResources instanceof FileDirContext) {
                filesystemBased = true;
                ((FileDirContext) webappResources).setAllowLinking(isAllowLinking());
            }
			if (webappResources instanceof BaseDirContext) {
				((BaseDirContext) webappResources).setDocBase(getBasePath());
                ((BaseDirContext) webappResources).setCached
                    (isCachingAllowed());
                ((BaseDirContext) webappResources).setCacheTTL(getCacheTTL());
                ((BaseDirContext) webappResources).setCacheMaxSize
                    (getCacheMaxSize());
                ((BaseDirContext) webappResources).allocate();
			}
			
			this.resources = proxyDirContext;
			
		}catch (Throwable t) {
			ok = false;
		}
		
		return ok;
	}

	protected String getBasePath() {
        String docBase = null;
        Container container = this;
        while (container != null) {
            if (container instanceof Host)
                break;
            container = container.getParent();
        }
        File file = new File(getDocBase());
        if (!file.isAbsolute()) {
            if (container == null) {
                docBase = (new File(engineBase(), getDocBase())).getPath();
            } else {
                // Use the "appBase" property of this container
                String appBase = ((Host) container).getAppBase();
                file = new File(appBase);
                if (!file.isAbsolute())
                    file = new File(engineBase(), appBase);
                docBase = (new File(file, getDocBase())).getPath();
            }
        } else {
            docBase = file.getPath();
        }
        return docBase;
    }
	
    protected File engineBase() {
        String base=System.getProperty(Globals.CATALINA_BASE_PROP);
        if( base == null ) {
            StandardEngine eng=(StandardEngine)this.getParent().getParent();
            base=eng.getBaseDir();
        }
        return (new File(base));
    }
	
	public synchronized void setResources(DirContext resources) {
		DirContext oldResources = this.webappResources;
		if (oldResources == resources)
            return;
		
		if (resources instanceof BaseDirContext) {
			((BaseDirContext) resources).setCached(isCachingAllowed());
			((BaseDirContext) resources).setCacheTTL(getCacheTTL());
			((BaseDirContext) resources).setCacheMaxSize(getCacheMaxSize());
			((BaseDirContext) resources).setCacheObjectMaxSize(getCacheObjectMaxSize());
		}
		if (resources instanceof FileDirContext) {
            filesystemBased = true;
            ((FileDirContext) resources).setAllowLinking(isAllowLinking());
        }
		this.webappResources = resources;
		this.resources = null;
		support.firePropertyChange("resources", oldResources,
                this.webappResources);
	}
	
    public boolean isAllowLinking() {
        return allowLinking;
    }
    
    public void setAllowLinking(boolean allowLinking) {
        this.allowLinking = allowLinking;
    }
	
    public String getAliases() {
        return this.aliases;
    }
    
    public void setAliases(String aliases) {
        this.aliases = aliases;
    }
	
    public int getCacheObjectMaxSize() {
        return cacheObjectMaxSize;
    }
    
    public void setCacheObjectMaxSize(int cacheObjectMaxSize) {
        this.cacheObjectMaxSize = cacheObjectMaxSize;
    }
	
    public int getCacheMaxSize() {
        return cacheMaxSize;
    }
    
    public void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

	public int getCacheTTL() {
        return cacheTTL;
    }
    
    public void setCacheTTL(int cacheTTL) {
        this.cacheTTL = cacheTTL;
    }
	
    public boolean isCachingAllowed() {
        return cachingAllowed;
    }
    
    public void setCachingAllowed(boolean cachingAllowed) {
        this.cachingAllowed = cachingAllowed;
    }

	public boolean getConfigured() {
		return (this.configured);
	}

	public void setConfigured(boolean configured) {
		 boolean oldConfigured = this.configured;
	        this.configured = configured;
	        support.firePropertyChange("configured",
	                                   oldConfigured,
	                                   this.configured);
	}

	public ServletContext getServletContext() {
		if (context == null) {
            context = new ApplicationContext(this);
            if (altDDName != null)
                context.setAttribute(Globals.ALT_DD_ATTR,altDDName);
        }
        return (context.getFacade());
	}

	public ApplicationParameter[] findApplicationParameters() {
		synchronized (applicationParametersLock) {
            return (applicationParameters);
        }
	}

	public InstanceManager getInstanceManager() {
		 return instanceManager;
	}
	
    public void setInstanceManager(InstanceManager instanceManager) {
        this.instanceManager = instanceManager;
     }

	public Object[] getApplicationEventListeners() {
		return (applicationEventListenersObjects);
	}

	public void setApplicationEventListeners(Object[] listeners) {
		applicationEventListenersObjects = listeners;
	}
	
    public Object[] getApplicationLifecycleListeners() {
        return (applicationLifecycleListenersObjects);
    }
    
    public void setApplicationLifecycleListeners(Object listeners[]) {
        applicationLifecycleListenersObjects = listeners;
    }
    
    private static class NoPluggabilityServletContext implements ServletContext {

    	private final ServletContext sc;
    	
		public NoPluggabilityServletContext(ServletContext sc) {
			 this.sc = sc;
		}

		public String getContextPath() {
			// TODO Auto-generated method stub
			return null;
		}

		public ServletContext getContext(String uripath) {
			// TODO Auto-generated method stub
			return null;
		}

		public int getMajorVersion() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getMinorVersion() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getEffectiveMajorVersion() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getEffectiveMinorVersion() {
			// TODO Auto-generated method stub
			return 0;
		}

		public String getMimeType(String file) {
			// TODO Auto-generated method stub
			return null;
		}

		public Set<String> getResourcePaths(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		public URL getResource(String path) throws MalformedURLException {
			// TODO Auto-generated method stub
			return null;
		}

		public InputStream getResourceAsStream(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		public RequestDispatcher getRequestDispatcher(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		public RequestDispatcher getNamedDispatcher(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		public Servlet getServlet(String name) throws ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		public Enumeration<Servlet> getServlets() {
			// TODO Auto-generated method stub
			return null;
		}

		public Enumeration<String> getServletNames() {
			// TODO Auto-generated method stub
			return null;
		}

		public void log(String msg) {
			// TODO Auto-generated method stub
			
		}

		public void log(Exception exception, String msg) {
			// TODO Auto-generated method stub
			
		}

		public void log(String message, Throwable throwable) {
			// TODO Auto-generated method stub
			
		}

		public String getRealPath(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getServerInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getInitParameter(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		public Enumeration<String> getInitParameterNames() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean setInitParameter(String name, String value) {
			// TODO Auto-generated method stub
			return false;
		}

		public Object getAttribute(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		public Enumeration<String> getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setAttribute(String name, Object object) {
			// TODO Auto-generated method stub
			
		}

		public void removeAttribute(String name) {
			// TODO Auto-generated method stub
			
		}

		public String getServletContextName() {
			// TODO Auto-generated method stub
			return null;
		}

		public Dynamic addServlet(String servletName, String className) {
			// TODO Auto-generated method stub
			return null;
		}

		public Dynamic addServlet(String servletName, Servlet servlet) {
			// TODO Auto-generated method stub
			return null;
		}

		public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
			// TODO Auto-generated method stub
			return null;
		}

		public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		public ServletRegistration getServletRegistration(String servletName) {
			// TODO Auto-generated method stub
			return null;
		}

		public Map<String, ? extends ServletRegistration> getServletRegistrations() {
			// TODO Auto-generated method stub
			return null;
		}

		public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
			// TODO Auto-generated method stub
			return null;
		}

		public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
			// TODO Auto-generated method stub
			return null;
		}

		public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName,
				Class<? extends Filter> filterClass) {
			// TODO Auto-generated method stub
			return null;
		}

		public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		public FilterRegistration getFilterRegistration(String filterName) {
			// TODO Auto-generated method stub
			return null;
		}

		public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
			// TODO Auto-generated method stub
			return null;
		}

		public SessionCookieConfig getSessionCookieConfig() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
			// TODO Auto-generated method stub
			
		}

		public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
			// TODO Auto-generated method stub
			return null;
		}

		public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
			// TODO Auto-generated method stub
			return null;
		}

		public void addListener(String className) {
			// TODO Auto-generated method stub
			
		}

		public <T extends EventListener> void addListener(T t) {
			// TODO Auto-generated method stub
			
		}

		public void addListener(Class<? extends EventListener> listenerClass) {
			// TODO Auto-generated method stub
			
		}

		public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		public JspConfigDescriptor getJspConfigDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		public ClassLoader getClassLoader() {
			// TODO Auto-generated method stub
			return null;
		}

		public void declareRoles(String... roleNames) {
			// TODO Auto-generated method stub
			
		}
    	
    }

    public String getDefaultWebXml() {
        return defaultWebXml;
    }
    
    public void setDefaultWebXml(String defaultWebXml) {
        this.defaultWebXml = defaultWebXml;
    }

	@Override
	public void setXmlValidation(boolean xmlValidation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getXmlValidation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setContainerSciFilter(String containerSciFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getContainerSciFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addWatchedResource(String name) {
		synchronized (watchedResourcesLock) {
            String results[] = new String[watchedResources.length + 1];
            for (int i = 0; i < watchedResources.length; i++)
                results[i] = watchedResources[i];
            results[watchedResources.length] = name;
            watchedResources = results;
        }
        fireContainerEvent("addWatchedResource", name);
	}

	@Override
    public void setReloadable(boolean reloadable) {

        boolean oldReloadable = this.reloadable;
        this.reloadable = reloadable;
        support.firePropertyChange("reloadable",
                                   oldReloadable,
                                   this.reloadable);

    }

	@Override
	public boolean getReloadable() {
		return (this.reloadable);
	}

    @Override
    public synchronized void reload() {
    	
	}

	public String getCompilerClasspath() {
		return compilerClasspath;
	}
	
    public void setCompilerClasspath(String compilerClasspath) {
        this.compilerClasspath = compilerClasspath;
    }

	public boolean getAntiJARLocking() {
		return (this.antiJARLocking);
	}
	
    public void setAntiJARLocking(boolean antiJARLocking) {
        boolean oldAntiJARLocking = this.antiJARLocking;
        this.antiJARLocking = antiJARLocking;
        support.firePropertyChange("antiJARLocking",
                                   oldAntiJARLocking,
                                   this.antiJARLocking);

    }

    public boolean getClearReferencesRmiTargets() {
        return this.clearReferencesRmiTargets;
    }


    public void setClearReferencesRmiTargets(boolean clearReferencesRmiTargets) {
        boolean oldClearReferencesRmiTargets = this.clearReferencesRmiTargets;
        this.clearReferencesRmiTargets = clearReferencesRmiTargets;
        support.firePropertyChange("clearReferencesRmiTargets",
                oldClearReferencesRmiTargets, this.clearReferencesRmiTargets);
    }

    public boolean getClearReferencesStatic() {

        return (this.clearReferencesStatic);

    }
    
    public void setClearReferencesStatic(boolean clearReferencesStatic) {

        boolean oldClearReferencesStatic = this.clearReferencesStatic;
        this.clearReferencesStatic = clearReferencesStatic;
        support.firePropertyChange("clearReferencesStatic",
                                   oldClearReferencesStatic,
                                   this.clearReferencesStatic);

    }

    public boolean getClearReferencesStopThreads() {

        return (this.clearReferencesStopThreads);

    }
    
    public void setClearReferencesStopThreads(
            boolean clearReferencesStopThreads) {

        boolean oldClearReferencesStopThreads = this.clearReferencesStopThreads;
        this.clearReferencesStopThreads = clearReferencesStopThreads;
        support.firePropertyChange("clearReferencesStopThreads",
                                   oldClearReferencesStopThreads,
                                   this.clearReferencesStopThreads);

    }

    public boolean getClearReferencesStopTimerThreads() {
        return (this.clearReferencesStopTimerThreads);
    }
    
    public void setClearReferencesStopTimerThreads(
            boolean clearReferencesStopTimerThreads) {

        boolean oldClearReferencesStopTimerThreads =
            this.clearReferencesStopTimerThreads;
        this.clearReferencesStopTimerThreads = clearReferencesStopTimerThreads;
        support.firePropertyChange("clearReferencesStopTimerThreads",
                                   oldClearReferencesStopTimerThreads,
                                   this.clearReferencesStopTimerThreads);
    }

    public boolean getClearReferencesHttpClientKeepAliveThread() {
        return (this.clearReferencesHttpClientKeepAliveThread);
    }
    
    public void setClearReferencesHttpClientKeepAliveThread(
            boolean clearReferencesHttpClientKeepAliveThread) {
        this.clearReferencesHttpClientKeepAliveThread =
            clearReferencesHttpClientKeepAliveThread;
    }

	@Override
	public String getSessionCookieName() {
		return sessionCookieName;
	}
	
    @Override
    public void setSessionCookieName(String sessionCookieName) {
        String oldSessionCookieName = this.sessionCookieName;
        this.sessionCookieName = sessionCookieName;
        support.firePropertyChange("sessionCookieName",
                oldSessionCookieName, sessionCookieName);
    }

	@Override
	public String getSessionCookieDomain() {
		return sessionCookieDomain;
	}

	@Override
	public void setSessionCookieDomain(String sessionCookieDomain) {
		 String oldSessionCookieDomain = this.sessionCookieDomain;
	        this.sessionCookieDomain = sessionCookieDomain;
	        support.firePropertyChange("sessionCookieDomain",
	                oldSessionCookieDomain, sessionCookieDomain);
	}


	@Override
	public boolean getUseHttpOnly() {
		return useHttpOnly;
	}

	@Override
	public void setUseHttpOnly(boolean useHttpOnly) {
		 boolean oldUseHttpOnly = this.useHttpOnly;
	        this.useHttpOnly = useHttpOnly;
	        support.firePropertyChange("useHttpOnly",
	                oldUseHttpOnly,
	                this.useHttpOnly);
	}

	@Override
	public String getSessionCookiePath() {
		return sessionCookiePath;
	}

	@Override
	public void setSessionCookiePath(String sessionCookiePath) {
		String oldSessionCookiePath = this.sessionCookiePath;
        this.sessionCookiePath = sessionCookiePath;
        support.firePropertyChange("sessionCookiePath",
                oldSessionCookiePath, sessionCookiePath);
	}

	@Override
	public String getEncodedPath() {
		return encodedPath;
	}

	@Override
	public boolean getSessionCookiePathUsesTrailingSlash() {
		return sessionCookiePathUsesTrailingSlash;
	}

	@Override
	public void setSessionCookiePathUsesTrailingSlash(boolean sessionCookiePathUsesTrailingSlash) {
		this.sessionCookiePathUsesTrailingSlash =
	            sessionCookiePathUsesTrailingSlash;
	}

	@Override
	public boolean getCookies() {
		 return (this.cookies);
	}

	@Override
	public void setCookies(boolean cookies) {
		boolean oldCookies = this.cookies;
        this.cookies = cookies;
        support.firePropertyChange("cookies",
                                   oldCookies,
                                   this.cookies);
	}

	@Override
	public boolean getCrossContext() {
		return (this.crossContext);
	}
	
	@Override
    public void setCrossContext(boolean crossContext) {

        boolean oldCrossContext = this.crossContext;
        this.crossContext = crossContext;
        support.firePropertyChange("crossContext",
                                   oldCrossContext,
                                   this.crossContext);

    }

	@Override
	public boolean getTldValidation() {
		return tldValidation;
	}

	@Override
	public void setTldValidation(boolean tldValidation) {
		this.tldValidation = tldValidation;
	}

	@Override
	public boolean getXmlBlockExternal() {
		return xmlBlockExternal;
	}

	@Override
	public void setXmlBlockExternal(boolean xmlBlockExternal) {
		this.xmlBlockExternal = xmlBlockExternal;
	}

	@Override
	public String findMimeMapping(String extension) {
		return (mimeMappings.get(extension));
	}

	@Override
	public String[] findMimeMappings() {
		synchronized (mimeMappings) {
            String results[] = new String[mimeMappings.size()];
            return
                (mimeMappings.keySet().toArray(results));
        }

	}
}
