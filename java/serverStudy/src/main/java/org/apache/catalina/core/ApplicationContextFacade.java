package org.apache.catalina.core;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

public class ApplicationContextFacade implements ServletContext{

	private ApplicationContext context = null;
	private final Map<String,Class<?>[]> classCache;
	private final Map<String,Method> objectCache;
	
	public ApplicationContextFacade(ApplicationContext context) {
		super();
        this.context = context;
        
        classCache = new HashMap<String,Class<?>[]>();
        objectCache = new ConcurrentHashMap<String,Method>();
        initClassCache();
	}
	
	private void initClassCache(){
        Class<?>[] clazz = new Class[]{String.class};
        classCache.put("getContext", clazz);
        classCache.put("getMimeType", clazz);
        classCache.put("getResourcePaths", clazz);
        classCache.put("getResource", clazz);
        classCache.put("getResourceAsStream", clazz);
        classCache.put("getRequestDispatcher", clazz);
        classCache.put("getNamedDispatcher", clazz);
        classCache.put("getServlet", clazz);
        classCache.put("setInitParameter", new Class[]{String.class, String.class});
        classCache.put("createServlet", new Class[]{Class.class});
        classCache.put("addServlet", new Class[]{String.class, String.class});
        classCache.put("createFilter", new Class[]{Class.class});
        classCache.put("addFilter", new Class[]{String.class, String.class});
        classCache.put("createListener", new Class[]{Class.class});
        classCache.put("addListener", clazz);
        classCache.put("getFilterRegistration", clazz);
        classCache.put("getServletRegistration", clazz);
        classCache.put("getInitParameter", clazz);
        classCache.put("setAttribute", new Class[]{String.class, Object.class});
        classCache.put("removeAttribute", clazz);
        classCache.put("getRealPath", clazz);
        classCache.put("getAttribute", clazz);
        classCache.put("log", clazz);
        classCache.put("setSessionTrackingModes", new Class[]{Set.class} );
    }

	public String getContextPath() {
        return context.getContextPath();
	}

	public ServletContext getContext(String uripath) {
		ServletContext theContext = null;
        theContext = context.getContext(uripath);
        if ((theContext != null) &&
            (theContext instanceof ApplicationContext)){
            theContext = ((ApplicationContext)theContext).getFacade();
        }
        return (theContext);
	}

	public int getMajorVersion() {
		return context.getMajorVersion();
	}

	public int getMinorVersion() {
		 return context.getMinorVersion();
	}

	public int getEffectiveMajorVersion() {
		 return context.getEffectiveMajorVersion();
	}

	public int getEffectiveMinorVersion() {
		return context.getEffectiveMinorVersion();
	}

	public String getMimeType(String file) {
		return context.getMimeType(file);
	}

	public Set<String> getResourcePaths(String path) {
		return context.getResourcePaths(path);
	}

	public URL getResource(String path) throws MalformedURLException {
		return context.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return context.getResourceAsStream(path);
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return context.getRequestDispatcher(path);
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		return context.getNamedDispatcher(name);
	}

	public Servlet getServlet(String name) throws ServletException {
		return context.getServlet(name);
	}

	public Enumeration<Servlet> getServlets() {
		return context.getServlets();
	}

	public Enumeration<String> getServletNames() {
		return context.getServletNames();
	}

	public void log(String msg) {
		context.log(msg);
	}

	public void log(Exception exception, String msg) {
		context.log(exception,msg);
	}

	public void log(String message, Throwable throwable) {
		context.log(message,throwable);
	}

	public String getRealPath(String path) {
		return context.getRealPath(path);
	}

	public String getServerInfo() {
		return context.getServerInfo();
	}

	public String getInitParameter(String name) {
		return context.getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return context.getInitParameterNames();
	}

	public boolean setInitParameter(String name, String value) {
		return context.setInitParameter(name, value);
	}

	public Object getAttribute(String name) {
		return context.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return context.getAttributeNames();
	}

	public void setAttribute(String name, Object object) {
		context.setAttribute(name, object);
	}

	public void removeAttribute(String name) {
		context.removeAttribute(name);
	}

	public String getServletContextName() {
		return context.getServletContextName();
	}

	public Dynamic addServlet(String servletName, String className) {
		return context.addServlet(servletName,servletName);
	}

	public Dynamic addServlet(String servletName, Servlet servlet) {
		return context.addServlet(servletName,servlet);
	}

	public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
		return context.addServlet(servletName,servletClass);
	}

	public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
		return context.createServlet(clazz);
	}

	public ServletRegistration getServletRegistration(String servletName) {
		return context.getServletRegistration(servletName);
	}

	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return context.getServletRegistrations();
	}

	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
		return context.addFilter(filterName,className);
	}

	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
		return context.addFilter(filterName,filter);
	}

	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
		return context.addFilter(filterName,filterClass);
	}

	public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
		return context.createFilter(clazz);
	}

	public FilterRegistration getFilterRegistration(String filterName) {
		return context.getFilterRegistration(filterName);
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return context.getFilterRegistrations();
	}

	public SessionCookieConfig getSessionCookieConfig() {
		return context.getSessionCookieConfig();
	}

	public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
		context.setSessionTrackingModes(sessionTrackingModes);
	}

	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return context.getDefaultSessionTrackingModes();
	}

	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return context.getEffectiveSessionTrackingModes();
	}

	public void addListener(String className) {
		context.addListener(className);
	}

	public <T extends EventListener> void addListener(T t) {
		context.addListener(t);
	}

	public void addListener(Class<? extends EventListener> listenerClass) {
		context.addListener(listenerClass);
	}

	public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
		return context.createListener(clazz);
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		return context.getJspConfigDescriptor();
	}

	public ClassLoader getClassLoader() {
		return context.getClassLoader();
	}

	public void declareRoles(String... roleNames) {
		context.declareRoles(roleNames);
	}
	
    private Object doPrivileged(final String methodName, final Object[] params) {
        try{
            return invokeMethod(context, methodName, params);
        }catch(Throwable t){
            throw new RuntimeException(t.getMessage(), t);
        }
    }
    
	private Object invokeMethod(ApplicationContext appContext, final String methodName, Object[] params)
			throws Throwable {

		try {
			Method method = objectCache.get(methodName);
			if (method == null) {
				method = appContext.getClass().getMethod(methodName, classCache.get(methodName));
				objectCache.put(methodName, method);
			}

			return executeMethod(method, appContext, params);
		} catch (Exception ex) {
			return null;
		} finally {
			params = null;
		}
	}
	
	private Object executeMethod(final Method method, final ApplicationContext context, final Object[] params)
			throws PrivilegedActionException, IllegalAccessException, InvocationTargetException {
		return method.invoke(context, params);
	}
}
