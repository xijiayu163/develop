package org.apache.catalina.core;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.InstanceManager;
import org.apache.catalina.deploy.FilterDef;

public class ApplicationFilterConfig implements FilterConfig, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private transient Context context = null;
	private transient Filter filter = null;
	private final FilterDef filterDef;

	private transient InstanceManager instanceManager;
	
	public ApplicationFilterConfig(Context context, FilterDef filterDef) throws ClassCastException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, ServletException {
		super();
        this.context = context;
        this.filterDef = filterDef;
        if (filterDef.getFilter() == null) {
        	getFilter();
        }
	}
	
	Filter getFilter() throws ClassCastException, ClassNotFoundException,
    IllegalAccessException, InstantiationException, ServletException,
    InvocationTargetException {
		if (this.filter != null)
            return (this.filter);
		
		String filterClass = filterDef.getFilterClass();
		this.filter = (Filter) getInstanceManager().newInstance(filterClass);
		initFilter();
		
		return (this.filter);
	}

	private void initFilter() throws ServletException {
		filter.init(this);
	}

	private InstanceManager getInstanceManager() {
        if (instanceManager == null) {
            if (context instanceof StandardContext) {
                instanceManager = ((StandardContext)context).getInstanceManager();
            } else {
            	instanceManager = new DefaultInstanceManager(null,
                        new HashMap<String, Map<String, String>>(),
                        context,
                        getClass().getClassLoader()); 
            }
        }
        return instanceManager;
    }
	
	public String getFilterName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletContext getServletContext() {
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
	
}
