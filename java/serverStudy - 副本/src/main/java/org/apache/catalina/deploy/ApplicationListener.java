package org.apache.catalina.deploy;

public class ApplicationListener {

	private final String className;
	private final boolean pluggabilityBlocked;
	
	 public ApplicationListener(String className,boolean pluggabilityBlocked) {
	        this.className = className;
	        this.pluggabilityBlocked = pluggabilityBlocked;
	    }
	
	public String getClassName() {
        return className;
    }

    public boolean isPluggabilityBlocked() {
        return pluggabilityBlocked;
    }
	
}
