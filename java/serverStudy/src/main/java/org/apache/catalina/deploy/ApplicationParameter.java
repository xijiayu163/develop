package org.apache.catalina.deploy;

import java.io.Serializable;

public class ApplicationParameter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private boolean override = true;

    public boolean getOverride() {
        return (this.override);
    }

    public void setOverride(boolean override) {
        this.override = override;
    }
    
    private String name = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private String value = null;

    public String getValue() {
        return (this.value);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
