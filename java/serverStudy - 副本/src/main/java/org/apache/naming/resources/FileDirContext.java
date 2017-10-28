package org.apache.naming.resources;

public class FileDirContext extends BaseDirContext{
	
	protected boolean allowLinking = false;
	
	public void setAllowLinking(boolean allowLinking) {
		this.allowLinking = allowLinking;
	}
	
    public boolean getAllowLinking() {
        return allowLinking;
    }
	
}
