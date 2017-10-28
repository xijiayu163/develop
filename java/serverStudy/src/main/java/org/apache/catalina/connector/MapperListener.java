package org.apache.catalina.connector;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Wrapper;
import org.apache.catalina.util.LifecycleBase;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.apache.tomcat.util.http.mapper.WrapperMappingInfo;

public class MapperListener extends LifecycleBase{
	 private Mapper mapper = null;
	 private Connector connector = null;
	 
	 public MapperListener(Mapper mapper, Connector connector) {
	        this.mapper = mapper;
	        this.connector = connector;
	    }

	@Override
	protected void initInternal() {
		//no-op
	}

	@Override
	protected void startInternal() throws LifecycleException {
		setState(LifecycleState.STARTING);
		Engine engine = (Engine) connector.getService().getContainer();
		Container[] conHosts = engine.findChildren();
		for (Container conHost : conHosts) {
			Host host = (Host) conHost;
			// Registering the host will register the context and wrappers
			 registerHost(host);
		}
	}
	
	private void registerHost(Host host) {
		String[] aliases = host.findAliases();
		mapper.addHost(host.getName(), aliases, host);
		for (Container container : host.findChildren()) {
             registerContext((Context) container);
        }
	}
	
	private void registerContext(Context context) {
        String contextPath = context.getPath();
        if ("/".equals(contextPath)) {
            contextPath = "";
        }
        Container host = context.getParent();

        javax.naming.Context resources = context.getResources();
        String[] welcomeFiles = context.findWelcomeFiles();
        List<WrapperMappingInfo> wrappers = new ArrayList<WrapperMappingInfo>();

        for (Container container : context.findChildren()) {
            prepareWrapperMappingInfo(context, (Wrapper) container, wrappers);
        }

        mapper.addContextVersion(host.getName(), host, contextPath,
                context.getWebappVersion(), context, welcomeFiles, resources,
                wrappers, context.getMapperContextRootRedirectEnabled(),
                context.getMapperDirectoryRedirectEnabled());
    }

	private void prepareWrapperMappingInfo(Context context, Wrapper wrapper, List<WrapperMappingInfo> wrappers) {
		String wrapperName = wrapper.getName();
		boolean resourceOnly = context.isResourceOnlyServlet(wrapperName);
		String[] mappings = wrapper.findMappings();
		for (String mapping : mappings) {
			boolean jspWildCard = (wrapperName.equals("jsp")
                    && mapping.endsWith("/*"));
			 wrappers.add(new WrapperMappingInfo(mapping, wrapper, jspWildCard,
	                    resourceOnly));
		}
	}

	@Override
	protected void stopInternal() throws LifecycleException {
		
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
