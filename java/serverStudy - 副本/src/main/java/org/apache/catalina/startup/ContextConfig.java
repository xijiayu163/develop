package org.apache.catalina.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.JarScanner;
import org.apache.catalina.JarScannerCallback;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.deploy.WebXml;
import org.apache.tomcat.util.descriptor.InputSourceUtil;
import org.xml.sax.InputSource;

public class ContextConfig implements LifecycleListener{

	protected Context context = null;
	protected String defaultWebXml = null;
	protected boolean ok = false;
	protected final Map<Class<?>, Set<ServletContainerInitializer>> typeInitializerMap =
            new HashMap<Class<?>, Set<ServletContainerInitializer>>();
	private static final Set<String> pluggabilityJarsToSkip =
            new HashSet<String>();
	protected static final Map<Host,DefaultWebXmlCacheEntry> hostWebXmlCache =
        new ConcurrentHashMap<Host,DefaultWebXmlCacheEntry>();

	public void lifecycleEvent(LifecycleEvent event) {
		try {
            context = (Context) event.getLifecycle();
        } catch (ClassCastException e) {
            return;
        }
		
		 if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
	            configureStart();
	        } else if (event.getType().equals(Lifecycle.BEFORE_START_EVENT)) {
	            beforeStart();
	        } else if (event.getType().equals(Lifecycle.AFTER_START_EVENT)) {
	            // Restore docBase for management tools
//	            if (originalDocBase != null) {
//	                context.setDocBase(originalDocBase);
//	            }
	        } else if (event.getType().equals(Lifecycle.CONFIGURE_STOP_EVENT)) {
	            //configureStop();
	        } else if (event.getType().equals(Lifecycle.AFTER_INIT_EVENT)) {
	            init();
	        } else if (event.getType().equals(Lifecycle.AFTER_DESTROY_EVENT)) {
//	            destroy();
	        }
	}
	
    private void init() {
    	// Called from StandardContext.init()

        context.setConfigured(false);
        ok = true;

        contextConfig();
	}
    
    protected void contextConfig() {
    	context.addWatchedResource("WEB-INF/web.xml");
    }

	protected synchronized void beforeStart() {
    	
    }
    
    

	private void configureStart() {
		webConfig();
	}

	private void webConfig() {
		Set<WebXml> defaults = new HashSet<WebXml>();
		defaults.add(getDefaultWebXmlFragment());
		WebXml webXml = createWebXml();
		
		InputSource contextWebXml = getContextWebXmlSource();
		parseWebXml(contextWebXml, webXml, false);
		
		ServletContext sContext = context.getServletContext();
		Map<String,WebXml> fragments = processJarsForWebFragments(webXml);
		Set<WebXml> orderedFragments = null;
		orderedFragments =
                WebXml.orderWebFragments(webXml, fragments, sContext);
		if (ok) {
            processServletContainerInitializers();
        }
		
		if (ok) {
            processAnnotations(
                    orderedFragments, webXml.isMetadataComplete());
        }
	}
	
	protected void processAnnotations(Set<WebXml> fragments,
            boolean handlesTypesOnly) {
    }
	
	protected void processAnnotationsUrl(URL url, WebXml fragment,
            boolean handlesTypesOnly) {
        
    }
	
	protected void processAnnotationsJar(URL url, WebXml fragment,
            boolean handlesTypesOnly) {

    }
	
	private void processServletContainerInitializers() {
		 
	}

	protected Map<String,WebXml> processJarsForWebFragments(WebXml application) {
		JarScanner jarScanner = context.getJarScanner();
		boolean parseRequired = true;
		Set<String> absoluteOrder = application.getAbsoluteOrdering();
		if (absoluteOrder != null && absoluteOrder.isEmpty() &&
                !context.getXmlValidation()) {
			parseRequired = false;
		}
		
		FragmentJarScannerCallback callback =
                new FragmentJarScannerCallback(parseRequired);
		jarScanner.scan(context.getServletContext(),
                context.getLoader().getClassLoader(), callback,
                pluggabilityJarsToSkip);
		
		return callback.getFragments();
	}

	protected InputSource getContextWebXmlSource() {
		InputStream stream = null;
		InputSource source = null;
		URL url = null;

		String altDDName = null;
		ServletContext servletContext = context.getServletContext();
		try {
			if (servletContext != null) {
				altDDName = (String)servletContext.getAttribute(Globals.ALT_DD_ATTR);
				if (altDDName != null) {
					try {
						stream = new FileInputStream(altDDName);
						url = new File(altDDName).toURI().toURL();
					} catch (FileNotFoundException e) {
					} catch (MalformedURLException e) {
					}
				}else {
					stream = servletContext.getResourceAsStream
	                        (Constants.ApplicationWebXml);
				}
				
				if (stream == null || url == null) {
	            } else {
	                source = new InputSource(url.toExternalForm());
	                source.setByteStream(stream);
	            }
			}
		}finally {
            if (source == null && stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
		
		return source;
	 }

	private WebXml getDefaultWebXmlFragment() {
		Host host = (Host) context.getParent();
		DefaultWebXmlCacheEntry entry = hostWebXmlCache.get(host);
		InputSource globalWebXml = getGlobalWebXmlSource();
		InputSource hostWebXml = getHostWebXmlSource();
		
		long globalTimeStamp = 0;
        long hostTimeStamp = 0;
        
        if (globalWebXml != null) {
        	URLConnection uc = null;
        	try {
                URL url = new URL(globalWebXml.getSystemId());
                uc = url.openConnection();
                globalTimeStamp = uc.getLastModified();
            } catch (IOException e) {
                globalTimeStamp = -1;
            } finally {
                if (uc != null) {
                    try {
                        uc.getInputStream().close();
                    } catch (IOException e) {
                        globalTimeStamp = -1;
                    }
                }
            }
        }
        
        if (hostWebXml != null) {
        	URLConnection uc = null;
        	try {
                URL url = new URL(hostWebXml.getSystemId());
                uc = url.openConnection();
                hostTimeStamp = uc.getLastModified();
            } catch (IOException e) {
                hostTimeStamp = -1;
            } finally {
                if (uc != null) {
                    try {
                        uc.getInputStream().close();
                    } catch (IOException e) {
                        hostTimeStamp = -1;
                    }
                }
            }
        }
        
        if (entry != null && entry.getGlobalTimeStamp() == globalTimeStamp &&
                entry.getHostTimeStamp() == hostTimeStamp) {
        	InputSourceUtil.close(globalWebXml);
            InputSourceUtil.close(hostWebXml);
            return entry.getWebXml();
        }
        
        synchronized (host.getPipeline()) {
        	entry = hostWebXmlCache.get(host);
        	if (entry != null && entry.getGlobalTimeStamp() == globalTimeStamp &&
                    entry.getHostTimeStamp() == hostTimeStamp) {
                return entry.getWebXml();
            }
        	
        	WebXml webXmlDefaultFragment = createWebXml();
            webXmlDefaultFragment.setOverridable(true);
            webXmlDefaultFragment.setDistributable(true);
            webXmlDefaultFragment.setAlwaysAddWelcomeFiles(false);
            
            if (globalWebXml == null) {
            } else {
                parseWebXml(globalWebXml, webXmlDefaultFragment, false);
            }
            
            webXmlDefaultFragment.setReplaceWelcomeFiles(true);
            parseWebXml(hostWebXml, webXmlDefaultFragment, false);
            
            if (globalTimeStamp != -1 && hostTimeStamp != -1) {
                entry = new DefaultWebXmlCacheEntry(webXmlDefaultFragment,
                        globalTimeStamp, hostTimeStamp);
                hostWebXmlCache.put(host, entry);
            }

            return webXmlDefaultFragment;
        }
	}
	
	 protected void parseWebXml(InputSource source, WebXml dest,
	            boolean fragment) {
		 if (source == null) return;
		 
		 //根据Webxml创建对象
		 dest.getSessionConfig().setSessionTimeout("30");
		 
	 }

	private WebXml createWebXml() {
		 return new WebXml();
	}

	private InputSource getHostWebXmlSource() {
		 File hostConfigBase = getHostConfigBase();
		 if (!hostConfigBase.exists())
	            return null;
		 return getWebXmlSource(Constants.HostWebXml, hostConfigBase.getPath());
	}
	
	protected File getHostConfigBase() {
        File file = null;
        Container container = context;
        Host host = null;
        Engine engine = null;
        while (container != null) {
            if (container instanceof Host) {
                host = (Host)container;
            }
            if (container instanceof Engine) {
                engine = (Engine)container;
            }
            container = container.getParent();
        }
        if (host != null && host.getXmlBase()!=null) {
            String xmlBase = host.getXmlBase();
            file = new File(xmlBase);
            if (!file.isAbsolute())
                file = new File(getBaseDir(), xmlBase);
        } else {
            StringBuilder result = new StringBuilder();
            if (engine != null) {
                result.append(engine.getName()).append('/');
            }
            if (host != null) {
                result.append(host.getName()).append('/');
            }
            file = new File (getConfigBase(), result.toString());
        }
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            return file;
        }
    }
	
    protected File getConfigBase() {
        File configBase = new File(getBaseDir(), "conf");
        if (!configBase.exists()) {
            return null;
        }
        return configBase;
    }

	protected InputSource getGlobalWebXmlSource() {
		 if (defaultWebXml == null && context instanceof StandardContext) {
	            defaultWebXml = ((StandardContext) context).getDefaultWebXml();
	        }
		 
		 if (defaultWebXml == null) getDefaultWebXml();
		 if (Constants.NoDefaultWebXml.equals(defaultWebXml)) {
	            return null;
	        }
		 
		 return getWebXmlSource(defaultWebXml, getBaseDir());
	}
	
	protected InputSource getWebXmlSource(String filename, String path) {
		File file = new File(filename);
		if (!file.isAbsolute()) {
            file = new File(path, filename);
        }
		
		InputStream stream = null;
        InputSource source = null;
        
        try {
        	if (!file.exists()) {
        		stream = getClass().getClassLoader().getResourceAsStream(filename);
        		if(stream != null) {
                    source =
                        new InputSource(getClass().getClassLoader().getResource(
                                filename).toURI().toString());
                }
        	}else {
                source = new InputSource(file.getAbsoluteFile().toURI().toString());
                stream = new FileInputStream(file);
            }
        	
        	if (stream != null && source != null) {
                source.setByteStream(stream);
            }
        }catch (Exception e) {
        } finally {
        	if (source == null && stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
        
        return source;
	}
	
    protected String getBaseDir() {
        Container engineC=context.getParent().getParent();
        if( engineC instanceof StandardEngine ) {
            return ((StandardEngine)engineC).getBaseDir();
        }
        return System.getProperty(Globals.CATALINA_BASE_PROP);
    }
	
    public String getDefaultWebXml() {
        if( defaultWebXml == null ) {
            defaultWebXml=Constants.DefaultWebXml;
        }

        return (this.defaultWebXml);
    }
	
	private static class DefaultWebXmlCacheEntry {

		private final WebXml webXml;
		private final long globalTimeStamp;
		private final long hostTimeStamp;
		
		public DefaultWebXmlCacheEntry(WebXml webXml, long globalTimeStamp,
                long hostTimeStamp) {
            this.webXml = webXml;
            this.globalTimeStamp = globalTimeStamp;
            this.hostTimeStamp = hostTimeStamp;
        }
		
		 public WebXml getWebXml() {
            return webXml;
        }

		public long getGlobalTimeStamp() {
            return globalTimeStamp;
        }

        public long getHostTimeStamp() {
            return hostTimeStamp;
        }
		
	}
	
	private class FragmentJarScannerCallback implements JarScannerCallback {

		private final boolean parseRequired;
		private Map<String,WebXml> fragments = new HashMap<String,WebXml>();
		
		public FragmentJarScannerCallback(boolean parseRequired) {
            this.parseRequired = parseRequired;
        }

		public Map<String,WebXml> getFragments() {
            return fragments;
        }

		@Override
		public void scan(JarURLConnection urlConn) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void scan(File file) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
	}
}
