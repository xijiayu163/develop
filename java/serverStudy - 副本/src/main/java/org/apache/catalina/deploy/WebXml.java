package org.apache.catalina.deploy;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

public class WebXml {

    private boolean overridable = false;
    public boolean isOverridable() {
        return overridable;
    }
    public void setOverridable(boolean overridable) {
        this.overridable = overridable;
    }


    private boolean distributable = false;
    public boolean isDistributable() { return distributable; }
    public void setDistributable(boolean distributable) {
        this.distributable = distributable;
    }
    
    private boolean alwaysAddWelcomeFiles = true;
    public void setAlwaysAddWelcomeFiles(boolean alwaysAddWelcomeFiles) {
        this.alwaysAddWelcomeFiles = alwaysAddWelcomeFiles;
    }

    private boolean replaceWelcomeFiles = false;
    public void setReplaceWelcomeFiles(boolean replaceWelcomeFiles) {
        this.replaceWelcomeFiles = replaceWelcomeFiles;
    }

    private Set<String> absoluteOrdering = null;
    public Set<String> getAbsoluteOrdering() {
        return absoluteOrdering;
    }
    
    public static Set<WebXml> orderWebFragments(WebXml application,
            Map<String,WebXml> fragments, ServletContext servletContext) {
    	 Set<WebXml> orderedFragments = new LinkedHashSet<WebXml>();
    	 
    	 return orderedFragments;
    	 
    }
	
    private boolean metadataComplete = false;
    public boolean isMetadataComplete() { return metadataComplete; }
    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete; }
    
    private SessionConfig sessionConfig = new SessionConfig();
    public void setSessionConfig(SessionConfig sessionConfig) {
        this.sessionConfig = sessionConfig;
    }
    public SessionConfig getSessionConfig() { return sessionConfig; }
	
}
