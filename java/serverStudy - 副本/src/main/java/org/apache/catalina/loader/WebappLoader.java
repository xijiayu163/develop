package org.apache.catalina.loader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.jar.JarFile;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.util.LifecycleBase;
import org.apache.naming.resources.DirContextURLStreamHandler;

public class WebappLoader extends LifecycleBase implements Loader, PropertyChangeListener{
	
	private ClassLoader parentClassLoader = null;
	private WebappClassLoaderBase classLoader = null;
	private Container container = null;
	protected PropertyChangeSupport support = new PropertyChangeSupport(this);
	private boolean reloadable = false;
	private boolean delegate = false;
	private String repositories[] = new String[0];
	private ArrayList<String> loaderRepositories = null;
	private String classpath = null;
	private String loaderClass =
        "org.apache.catalina.loader.WebappClassLoader";
	private boolean searchExternalFirst = false;
	private static final String info =
        "org.apache.catalina.loader.WebappLoader/1.0";
	
	public WebappLoader() {
        this(null);
    }
	
    public WebappLoader(ClassLoader parent) {
        super();
        this.parentClassLoader = parent;
    }
	
	public void propertyChange(PropertyChangeEvent event) {
		// Validate the source of this event
        if (!(event.getSource() instanceof Context))
            return;

        // Process a relevant property change
        if (event.getPropertyName().equals("reloadable")) {
            try {
                setReloadable
                    ( ((Boolean) event.getNewValue()).booleanValue() );
            } catch (NumberFormatException e) {
                log.error(e.getMessage(),e);
            }
        }
		
	}

	public void backgroundProcess() {
		if (reloadable && modified()) {
            try {
                Thread.currentThread().setContextClassLoader
                    (WebappLoader.class.getClassLoader());
                if (container instanceof StandardContext) {
                    ((StandardContext) container).reload();
                }
            } finally {
                if (container.getLoader() != null) {
                    Thread.currentThread().setContextClassLoader
                        (container.getLoader().getClassLoader());
                }
            }
        } else {
            closeJARs(false);
        }
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Container getContainer() {
		return (container);
	}

	public void setContainer(Container container) {
		// Deregister from the old Container (if any)
        if ((this.container != null) && (this.container instanceof Context))
            ((Context) this.container).removePropertyChangeListener(this);

        // Process this property change
        Container oldContainer = this.container;
        this.container = container;
        support.firePropertyChange("container", oldContainer, this.container);

        // Register with the new Container (if any)
        if ((this.container != null) && (this.container instanceof Context)) {
            setReloadable( ((Context) this.container).getReloadable() );
            ((Context) this.container).addPropertyChangeListener(this);
        }
	}

	public boolean getDelegate() {
		return (this.delegate);
	}

	public void setDelegate(boolean delegate) {
		boolean oldDelegate = this.delegate;
        this.delegate = delegate;
        support.firePropertyChange("delegate", Boolean.valueOf(oldDelegate),
                                   Boolean.valueOf(this.delegate));
	}

	public String getInfo() {
		return (info);
	}

	public boolean getReloadable() {
		return (this.reloadable);
	}

	public void setReloadable(boolean reloadable) {
		// Process this property change
        boolean oldReloadable = this.reloadable;
        this.reloadable = reloadable;
        support.firePropertyChange("reloadable",
                                   Boolean.valueOf(oldReloadable),
                                   Boolean.valueOf(this.reloadable));
		
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addRepository(String repository) {
        log.debug("webappLoader.addRepository:"+repository);

        for (int i = 0; i < repositories.length; i++) {
            if (repository.equals(repositories[i]))
                return;
        }
        String results[] = new String[repositories.length + 1];
        for (int i = 0; i < repositories.length; i++)
            results[i] = repositories[i];
        results[repositories.length] = repository;
        repositories = results;

        if (getState().isAvailable() && (classLoader != null)) {
            classLoader.addRepository(repository);
            if( loaderRepositories != null ) loaderRepositories.add(repository);
            setClassPath();
        }
	}

	private String getClasspath(ClassLoader loader) {
		 try {
	            Method m=loader.getClass().getMethod("getClasspath", new Class[] {});
	            if( log.isTraceEnabled())
	                log.trace("getClasspath " + m );
	            Object o=m.invoke( loader, new Object[] {} );
	            if( log.isDebugEnabled() )
	                log.debug("gotClasspath " + o);
	            if( o instanceof String )
	                return (String)o;
	            return null;
	        } catch( Exception ex ) {
	             log.error("getClasspath ", ex);
	        }
	        return null;
	}
	
	private void setClassPath() {
		// Validate our current state information
        if (!(container instanceof Context))
            return;
        ServletContext servletContext =
            ((Context) container).getServletContext();
        if (servletContext == null)
            return;

        if (container instanceof StandardContext) {
            String baseClasspath =
                ((StandardContext) container).getCompilerClasspath();
            if (baseClasspath != null) {
                servletContext.setAttribute(Globals.CLASS_PATH_ATTR,
                                            baseClasspath);
                return;
            }
        }

        StringBuilder classpath = new StringBuilder();

        // Assemble the class path information from our class loader chain
        ClassLoader loader = getClassLoader();

        if (delegate && loader != null) {
            // Skip the webapp loader for now as delegation is enabled
            loader = loader.getParent();
        }

        while (loader != null) {
            if (!buildClassPath(servletContext, classpath, loader)) {
                break;
            }
            loader = loader.getParent();
        }

        if (delegate) {
            // Delegation was enabled, go back and add the webapp paths
            loader = getClassLoader();
            if (loader != null) {
                buildClassPath(servletContext, classpath, loader);
            }
        }

        this.classpath=classpath.toString();

        // Store the assembled class path as a servlet context attribute
        servletContext.setAttribute(Globals.CLASS_PATH_ATTR,
                                    classpath.toString());
		
	}

	private boolean buildClassPath(ServletContext servletContext,
            StringBuilder classpath, ClassLoader loader) {
        if (loader instanceof URLClassLoader) {
            URL repositories[] =
                    ((URLClassLoader) loader).getURLs();
                for (int i = 0; i < repositories.length; i++) {
                    String repository = repositories[i].toString();
                    if (repository.startsWith("file://"))
                        repository = utf8Decode(repository.substring(7));
                    else if (repository.startsWith("file:"))
                        repository = utf8Decode(repository.substring(5));
                    else if (repository.startsWith("jndi:"))
                        repository =
                            servletContext.getRealPath(repository.substring(5));
                    else
                        continue;
                    if (repository == null)
                        continue;
                    if (classpath.length() > 0)
                        classpath.append(File.pathSeparator);
                    classpath.append(repository);
                }
        } else {
            String cp = getClasspath(loader);
            if (cp == null) {
                log.info( "Unknown loader " + loader + " " + loader.getClass());
            } else {
                if (classpath.length() > 0)
                    classpath.append(File.pathSeparator);
                classpath.append(cp);
            }
            return false;
        }
        return true;
    }

	private String utf8Decode(String input) {
		String result = null;
        try {
            result = URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            // Impossible. All JVMs are required to support UTF-8.
        }
        return result;
	}

	public String[] findRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean modified() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void startInternal() throws LifecycleException {
		if (container.getResources() == null) {
            log.info("No resources for " + container);
            setState(LifecycleState.STARTING);
            return;
        }

        // Construct a class loader based on our current repositories list
        try {

            classLoader = createClassLoader();
            classLoader.setResources(container.getResources());
            classLoader.setDelegate(this.delegate);
            classLoader.setSearchExternalFirst(searchExternalFirst);
            if (container instanceof StandardContext) {
                classLoader.setAntiJARLocking(
                        ((StandardContext) container).getAntiJARLocking());
                classLoader.setClearReferencesRmiTargets(
                        ((StandardContext) container).getClearReferencesRmiTargets());
                classLoader.setClearReferencesStatic(
                        ((StandardContext) container).getClearReferencesStatic());
                classLoader.setClearReferencesStopThreads(
                        ((StandardContext) container).getClearReferencesStopThreads());
                classLoader.setClearReferencesStopTimerThreads(
                        ((StandardContext) container).getClearReferencesStopTimerThreads());
                classLoader.setClearReferencesHttpClientKeepAliveThread(
                        ((StandardContext) container).getClearReferencesHttpClientKeepAliveThread());
            }

            for (int i = 0; i < repositories.length; i++) {
                classLoader.addRepository(repositories[i]);
            }

            // Configure our repositories
            setRepositories();
            setClassPath();

            setPermissions();

            ((Lifecycle) classLoader).start();

            // Binding the Webapp class loader to the directory context
            DirContextURLStreamHandler.bind(classLoader,
                    this.container.getResources());

            StandardContext ctx=(StandardContext)container;
            String contextName = ctx.getName();
            if (!contextName.startsWith("/")) {
                contextName = "/" + contextName;
            }
        } catch (Throwable t) {
            log.error( "LifecycleException ", t );
            throw new LifecycleException("start: ", t);
        }

        setState(LifecycleState.STARTING);
	}

	 private void setPermissions() {

	        if (!Globals.IS_SECURITY_ENABLED)
	            return;
	        if (!(container instanceof Context))
	            return;

	        // Tell the class loader the root of the context
	        ServletContext servletContext =
	            ((Context) container).getServletContext();

	        // Assigning permissions for the work directory
	        File workDir =
	            (File) servletContext.getAttribute(ServletContext.TEMPDIR);
	        if (workDir != null) {
	            try {
	                String workDirPath = workDir.getCanonicalPath();
	                classLoader.addPermission
	                    (new FilePermission(workDirPath, "read,write"));
	                classLoader.addPermission
	                    (new FilePermission(workDirPath + File.separator + "-",
	                                        "read,write,delete"));
	            } catch (IOException e) {
	                // Ignore
	            }
	        }

	        try {

	            URL rootURL = servletContext.getResource("/");
	            classLoader.addPermission(rootURL);

	            String contextRoot = servletContext.getRealPath("/");
	            if (contextRoot != null) {
	                try {
	                    contextRoot = (new File(contextRoot)).getCanonicalPath();
	                    classLoader.addPermission(contextRoot);
	                } catch (IOException e) {
	                    // Ignore
	                }
	            }

	            URL classesURL = servletContext.getResource("/WEB-INF/classes/");
	            classLoader.addPermission(classesURL);
	            URL libURL = servletContext.getResource("/WEB-INF/lib/");
	            classLoader.addPermission(libURL);

	            if (contextRoot != null) {

	                if (libURL != null) {
	                    File rootDir = new File(contextRoot);
	                    File libDir = new File(rootDir, "WEB-INF/lib/");
	                    try {
	                        String path = libDir.getCanonicalPath();
	                        classLoader.addPermission(path);
	                    } catch (IOException e) {
	                        // Ignore
	                    }
	                }

	            } else {

	                if (workDir != null) {
	                    if (libURL != null) {
	                        File libDir = new File(workDir, "WEB-INF/lib/");
	                        try {
	                            String path = libDir.getCanonicalPath();
	                            classLoader.addPermission(path);
	                        } catch (IOException e) {
	                            // Ignore
	                        }
	                    }
	                    if (classesURL != null) {
	                        File classesDir = new File(workDir, "WEB-INF/classes/");
	                        try {
	                            String path = classesDir.getCanonicalPath();
	                            classLoader.addPermission(path);
	                        } catch (IOException e) {
	                            // Ignore
	                        }
	                    }
	                }

	            }

	        } catch (MalformedURLException e) {
	            // Ignore
	        }

	    }

	private void setRepositories() throws IOException {

	        if (!(container instanceof Context))
	            return;
	        ServletContext servletContext =
	            ((Context) container).getServletContext();
	        if (servletContext == null)
	            return;

	        loaderRepositories=new ArrayList<String>();
	        // Loading the work directory
	        File workDir =
	            (File) servletContext.getAttribute(ServletContext.TEMPDIR);
	        if (workDir == null) {
	            log.info("No work dir for " + servletContext);
	        }

	        log.debug("webappLoader.deploy" + workDir.getAbsolutePath());

	        classLoader.setWorkDir(workDir);

	        DirContext resources = container.getResources();

	        // Setting up the class repository (/WEB-INF/classes), if it exists

	        String classesPath = "/WEB-INF/classes";
	        DirContext classes = null;

	        if (classes != null) {

	            File classRepository = null;

	            String absoluteClassesPath =
	                servletContext.getRealPath(classesPath);

	            if (absoluteClassesPath != null) {

	                classRepository = new File(absoluteClassesPath);

	            } else {

	                classRepository = new File(workDir, classesPath);
	                if (!classRepository.mkdirs() &&
	                        !classRepository.isDirectory()) {
	                    throw new IOException("webappLoader.mkdirFailure");
	                }
	                if (!copyDir(classes, classRepository)) {
	                    throw new IOException("webappLoader.copyFailure");
	                }

	            }

                log.debug("webappLoader.classDeploy："+
                             classRepository.getAbsolutePath());


	            // Adding the repository to the class loader
	            classLoader.addRepository(classesPath + "/", classRepository);
	            loaderRepositories.add(classesPath + "/" );

	        }

	        // Setting up the JAR repository (/WEB-INF/lib), if it exists

	        String libPath = "/WEB-INF/lib";

	        classLoader.setJarPath(libPath);

	        DirContext libDir = null;
	        // Looking up directory /WEB-INF/lib in the context
	        try {
	            Object object = resources.lookup(libPath);
	            if (object instanceof DirContext)
	                libDir = (DirContext) object;
	        } catch(NamingException e) {
	            // Silent catch: it's valid that no /WEB-INF/lib collection
	            // exists
	        }

	        if (libDir != null) {

	            boolean copyJars = false;
	            String absoluteLibPath = servletContext.getRealPath(libPath);

	            File destDir = null;

	            if (absoluteLibPath != null) {
	                destDir = new File(absoluteLibPath);
	            } else {
	                copyJars = true;
	                destDir = new File(workDir, libPath);
	                if (!destDir.mkdirs() && !destDir.isDirectory()) {
	                    throw new IOException("webappLoader.mkdirFailure");
	                }
	            }

	            // Looking up directory /WEB-INF/lib in the context
	            NamingEnumeration<NameClassPair> enumeration = null;
	            try {
	                enumeration = libDir.list("");
	            } catch (NamingException e) {
	                IOException ioe = new IOException("webappLoader.namingFailure:"+libPath);
	                ioe.initCause(e);
	                throw ioe;
	            }
	            while (enumeration.hasMoreElements()) {
	                NameClassPair ncPair = enumeration.nextElement();
	                String filename = libPath + "/" + ncPair.getName();
	                if (!filename.endsWith(".jar"))
	                    continue;

	                // Copy JAR in the work directory, always (the JAR file
	                // would get locked otherwise, which would make it
	                // impossible to update it or remove it at runtime)
	                File destFile = new File(destDir, ncPair.getName());

	                if( log.isDebugEnabled())
	                log.debug("webappLoader.jarDeploy:"+filename+" "+destFile.getAbsolutePath());

	                // Bug 45403 - Explicitly call lookup() on the name to check
	                // that the resource is readable. We cannot use resources
	                // returned by listBindings(), because that lists all of them,
	                // but does not perform the necessary checks on each.
	                Object obj = null;
	                try {
	                    obj = libDir.lookup(ncPair.getName());
	                } catch (NamingException e) {
	                    IOException ioe = new IOException("webappLoader.namingFailure:"+filename);
	                    ioe.initCause(e);
	                    throw ioe;
	                }

	                try {
	                    JarFile jarFile = new JarFile(destFile);
	                    classLoader.addJar(filename, jarFile, destFile);
	                } catch (Exception ex) {
	                    // Catch the exception if there is an empty jar file
	                    // Should ignore and continue loading other jar files
	                    // in the dir
	                }

	                loaderRepositories.add( filename );
	            }
	        }
	    }

	 private boolean copyDir(DirContext srcDir, File destDir) {
		return true; 	
	 }
	 
	private WebappClassLoaderBase createClassLoader() throws Exception {
		 Class<?> clazz = Class.forName(loaderClass);
	        WebappClassLoaderBase classLoader = null;

	        if (parentClassLoader == null) {
	            parentClassLoader = container.getParentClassLoader();
	        }
	        Class<?>[] argTypes = { ClassLoader.class };
	        Object[] args = { parentClassLoader };
	        Constructor<?> constr = clazz.getConstructor(argTypes);
	        classLoader = (WebappClassLoaderBase) constr.newInstance(args);

	        return classLoader;
	}

	@Override
	protected void stopInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

    public void closeJARs(boolean force) {
        if (classLoader !=null) {
            classLoader.closeJARs(force);
        }
    }

	@Override
	protected void initInternal() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}
	
}
