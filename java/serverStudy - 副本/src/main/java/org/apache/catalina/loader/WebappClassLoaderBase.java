package org.apache.catalina.loader;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.naming.directory.DirContext;

import org.apache.catalina.InstrumentableClassLoader;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.naming.resources.ProxyDirContext;

public abstract class WebappClassLoaderBase extends URLClassLoader
	implements Lifecycle, InstrumentableClassLoader{
	
	protected JarFile[] jarFiles = new JarFile[0];
	protected long lastJarAccessed = 0L;
	protected boolean hasExternalRepositories = false;
	protected URL[] repositoryURLs = null;
	protected DirContext resources = null;
	private String contextName = "unknown";
	protected boolean delegate = false;
	protected boolean searchExternalFirst = false;
	boolean antiJARLocking = false;
	private boolean clearReferencesRmiTargets = true;
	private boolean clearReferencesStatic = false;
	private boolean clearReferencesStopThreads = false;
	private boolean clearReferencesStopTimerThreads = false;
	private boolean clearReferencesLogFactoryRelease = true;
	private boolean clearReferencesHttpClientKeepAliveThread = true;
	protected File loaderDir = null;
	protected String canonicalLoaderDir = null;
	
	protected Log log = LogFactory.getLog(this.getClass());
	protected String[] repositories = new String[0];
	protected File[] files = new File[0];
	protected String jarPath = null;
	protected String[] jarNames = new String[0];
	protected File[] jarRealFiles = new File[0];
	protected ClassLoader parent = null;
	protected SecurityManager securityManager = null;
	protected ArrayList<Permission> permissionList =
        new ArrayList<Permission>();
	protected long[] lastModifiedDates = new long[0];
	protected String[] paths = new String[0];
	protected HashMap<String, String> notFoundResources =
        new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        @Override
        protected boolean removeEldestEntry(
                Map.Entry<String, String> eldest) {
            return size() > 1000;
        }
    };
	protected HashMap<String, PermissionCollection> loaderPC = new HashMap<String, PermissionCollection>();
	protected boolean started = false;
	protected boolean needConvert = false;
	private URL webInfClassesCodeBase = null;
	
	private static final String CLASS_FILE_SUFFIX = ".class";
	protected static final String[] triggers = {
        "javax.servlet.Servlet", "javax.el.Expression"       // Servlet API
    };
	
	public WebappClassLoaderBase() {
		super(new URL[0]);
	}
	
	 public WebappClassLoaderBase(ClassLoader parent) {
		 super(new URL[0], parent);
	 }
	 
	public void closeJARs(boolean force) {
		if (jarFiles.length > 0) {
			synchronized (jarFiles) {
				if (force || (System.currentTimeMillis() > (lastJarAccessed + 90000))) {
					for (int i = 0; i < jarFiles.length; i++) {
						try {
							if (jarFiles[i] != null) {
								jarFiles[i].close();
								jarFiles[i] = null;
							}
						} catch (IOException e) {
						}
					}
				}
			}
		}
	}

	public void addRepository(String repository) {
		// Ignore any of the standard repositories, as they are set up using
        // either addJar or addRepository
        if (repository.startsWith("/WEB-INF/lib")
            || repository.startsWith("/WEB-INF/classes"))
            return;

        // Add this repository to our underlying class loader
        try {
            URL url = new URL(repository);
            super.addURL(url);
            hasExternalRepositories = true;
            repositoryURLs = null;
        } catch (MalformedURLException e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Invalid repository: " + repository);
            iae.initCause(e);
            throw iae;
        }
	}
	
    public DirContext getResources() {

        return this.resources;

    }

	public void setResources(DirContext resources) {
		 this.resources = resources;

	        if (resources instanceof ProxyDirContext) {
	            contextName = ((ProxyDirContext) resources).getContextName();
	        }
	}
	
    public String getContextName() {
        return (this.contextName);
    }

	public void setDelegate(boolean delegate) {
		this.delegate = delegate;
	}
	
    public boolean getDelegate() {
        return (this.delegate);

    }

	public void setSearchExternalFirst(boolean searchExternalFirst) {
		this.searchExternalFirst = searchExternalFirst;
	}
	
    public boolean getSearchExternalFirst() {
        return searchExternalFirst;
    }

    public boolean getAntiJARLocking() {
        return antiJARLocking;
    }
    
	public void setAntiJARLocking(boolean antiJARLocking) {
		this.antiJARLocking = antiJARLocking;
	}

    public boolean getClearReferencesRmiTargets() {
        return this.clearReferencesRmiTargets;
    }


    public void setClearReferencesRmiTargets(boolean clearReferencesRmiTargets) {
        this.clearReferencesRmiTargets = clearReferencesRmiTargets;
    }

	public void setClearReferencesStatic(boolean clearReferencesStatic) {
		this.clearReferencesStatic = clearReferencesStatic;
	}
	
    public boolean getClearReferencesStatic() {
        return (this.clearReferencesStatic);
    }

    public boolean getClearReferencesStopThreads() {
        return (this.clearReferencesStopThreads);
    }
	
    public void setClearReferencesStopThreads(
            boolean clearReferencesStopThreads) {
        this.clearReferencesStopThreads = clearReferencesStopThreads;
    }

	public void setClearReferencesStopTimerThreads(boolean clearReferencesStopTimerThreads) {
		this.clearReferencesStopTimerThreads = clearReferencesStopTimerThreads;
	}
	
    public boolean getClearReferencesStopTimerThreads() {
        return (this.clearReferencesStopTimerThreads);
    }
	
    public boolean getClearReferencesLogFactoryRelease() {
        return (this.clearReferencesLogFactoryRelease);
    }
    
    public void setClearReferencesLogFactoryRelease(
            boolean clearReferencesLogFactoryRelease) {
        this.clearReferencesLogFactoryRelease =
            clearReferencesLogFactoryRelease;
    }
    
    public boolean getClearReferencesHttpClientKeepAliveThread() {
        return (this.clearReferencesHttpClientKeepAliveThread);
    }

    public void setClearReferencesHttpClientKeepAliveThread(
            boolean clearReferencesHttpClientKeepAliveThread) {
        this.clearReferencesHttpClientKeepAliveThread =
            clearReferencesHttpClientKeepAliveThread;
    }

	public void setWorkDir(File workDir) {
		this.loaderDir = new File(workDir, "loader");
        try {
            canonicalLoaderDir = loaderDir.getCanonicalPath();
            if (!canonicalLoaderDir.endsWith(File.separator)) {
                canonicalLoaderDir += File.separator;
            }
        } catch (IOException ioe) {
            canonicalLoaderDir = null;
        }
	}

	synchronized void addRepository(String repository, File file) {

        // Note : There should be only one (of course), but I think we should
        // keep this a bit generic

        if (repository == null)
            return;

        log.debug("addRepository(" + repository + ")");

        int i;

        // Add this repository to our internal list
        String[] result = new String[repositories.length + 1];
        for (i = 0; i < repositories.length; i++) {
            result[i] = repositories[i];
        }
        result[repositories.length] = repository;
        repositories = result;

        // Add the file to the list
        File[] result2 = new File[files.length + 1];
        for (i = 0; i < files.length; i++) {
            result2[i] = files[i];
        }
        result2[files.length] = file;
        files = result2;

    }
	
    public String getJarPath() {

        return this.jarPath;

    }

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	synchronized void addJar(String jar, JarFile jarFile, File file)
	        throws IOException {

	        if (jar == null)
	            return;
	        if (jarFile == null)
	            return;
	        if (file == null)
	            return;

	        if (log.isDebugEnabled())
	            log.debug("addJar(" + jar + ")");

	        int i;

	        if ((jarPath != null) && (jar.startsWith(jarPath))) {

	            String jarName = jar.substring(jarPath.length());
	            while (jarName.startsWith("/"))
	                jarName = jarName.substring(1);

	            String[] result = new String[jarNames.length + 1];
	            for (i = 0; i < jarNames.length; i++) {
	                result[i] = jarNames[i];
	            }
	            result[jarNames.length] = jarName;
	            jarNames = result;

	        }

	        // If the JAR currently contains invalid classes, don't actually use it
	        // for classloading
	        if (!validateJarFile(file))
	            return;

	        JarFile[] result2 = new JarFile[jarFiles.length + 1];
	        for (i = 0; i < jarFiles.length; i++) {
	            result2[i] = jarFiles[i];
	        }
	        result2[jarFiles.length] = jarFile;
	        jarFiles = result2;

	        // Add the file to the list
	        File[] result4 = new File[jarRealFiles.length + 1];
	        for (i = 0; i < jarRealFiles.length; i++) {
	            result4[i] = jarRealFiles[i];
	        }
	        result4[jarRealFiles.length] = file;
	        jarRealFiles = result4;
	    }
	
	protected boolean validateJarFile(File file)
	        throws IOException {

	        if (triggers == null)
	            return (true);

	        JarFile jarFile = null;
	        try {
	            jarFile = new JarFile(file);
	            for (int i = 0; i < triggers.length; i++) {
	                Class<?> clazz = null;
	                try {
	                    if (parent != null) {
	                        clazz = parent.loadClass(triggers[i]);
	                    } else {
	                        clazz = Class.forName(triggers[i]);
	                    }
	                } catch (Exception e) {
	                    clazz = null;
	                }
	                if (clazz == null)
	                    continue;
	                String name = triggers[i].replace('.', '/') + CLASS_FILE_SUFFIX;
	                if (log.isDebugEnabled())
	                    log.debug(" Checking for " + name);
	                JarEntry jarEntry = jarFile.getJarEntry(name);
	                if (jarEntry != null) {
	                    log.info("validateJarFile(" + file +
	                        ") - jar not loaded. See Servlet Spec 3.0, "
	                        + "section 10.7.2. Offending class: " + name);
	                    return false;
	                }
	            }
	            return true;
	        } finally {
	            if (jarFile != null) {
	                try {
	                    jarFile.close();
	                } catch (IOException ioe) {
	                    // Ignore
	                }
	            }
	        }
	    }

    public void addPermission(Permission permission) {
        if ((securityManager != null) && (permission != null)) {
            permissionList.add(permission);
        }
    }
    
    public void addPermission(URL url) {
        if (url != null) {
            addPermission(url.toString());
        }
    }
    
    public void addPermission(String filepath) {
        if (filepath == null) {
            return;
        }

        String path = filepath;

        if (securityManager != null) {
            Permission permission = null;
            if (path.startsWith("jndi:") || path.startsWith("jar:jndi:")) {

            } else {
                if (!path.endsWith(File.separator)) {
                    permission = new FilePermission(path, "read");
                    addPermission(permission);
                    path = path + File.separator;
                }
                permission = new FilePermission(path + "-", "read");
                addPermission(permission);
            }
        }
    }

    protected void copyStateWithoutTransformers(WebappClassLoaderBase base) {
        base.antiJARLocking = this.antiJARLocking;
        base.resources = this.resources;
        base.files = this.files;
        base.delegate = this.delegate;
        base.lastJarAccessed = this.lastJarAccessed;
        base.repositories = this.repositories;
        base.jarPath = this.jarPath;
        base.loaderDir = this.loaderDir;
        base.canonicalLoaderDir = this.canonicalLoaderDir;
        base.clearReferencesStatic = this.clearReferencesStatic;
        base.clearReferencesStopThreads = this.clearReferencesStopThreads;
        base.clearReferencesStopTimerThreads = this.clearReferencesStopTimerThreads;
        base.clearReferencesLogFactoryRelease = this.clearReferencesLogFactoryRelease;
        base.clearReferencesHttpClientKeepAliveThread = this.clearReferencesHttpClientKeepAliveThread;
        base.repositoryURLs = this.repositoryURLs.clone();
        base.jarFiles = this.jarFiles.clone();
        base.jarRealFiles = this.jarRealFiles.clone();
        base.jarNames = this.jarNames.clone();
        base.lastModifiedDates = this.lastModifiedDates.clone();
        base.paths = this.paths.clone();
        base.notFoundResources.putAll(this.notFoundResources);
        base.permissionList.addAll(this.permissionList);
        base.loaderPC.putAll(this.loaderPC);
        base.contextName = this.contextName;
        base.hasExternalRepositories = this.hasExternalRepositories;
        base.searchExternalFirst = this.searchExternalFirst;
    }
    
    @Override
	public void addLifecycleListener(LifecycleListener listener) {
    	// NOOP
	}


	@Override
	public LifecycleListener[] findLifecycleListeners() {
		return new LifecycleListener[0];
	}


	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// NOOP
	}


	@Override
	public void init() throws LifecycleException {
		// NOOP
	}


	@Override
	public void start() throws LifecycleException {
		started = true;
        String encoding = null;
        try {
            encoding = System.getProperty("file.encoding");
        } catch (SecurityException e) {
            return;
        }
        if (encoding.indexOf("EBCDIC")!=-1) {
            needConvert = true;
        }

        for (int i = 0; i < repositories.length; i++) {
            if (repositories[i].equals("/WEB-INF/classes/")) {
                try {
                    webInfClassesCodeBase = files[i].toURI().toURL();
                } catch (MalformedURLException e) {
                    // Ignore - leave it as null
                }
                break;
            }
        }
	}


	@Override
	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void destroy() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public LifecycleState getState() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addTransformer(ClassFileTransformer transformer) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeTransformer(ClassFileTransformer transformer) {
		// TODO Auto-generated method stub
		
	}
}
