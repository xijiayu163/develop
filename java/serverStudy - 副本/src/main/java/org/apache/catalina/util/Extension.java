package org.apache.catalina.util;

public final class Extension {
	private String extensionName = null;
	
    public String getExtensionName() {
        return (this.extensionName);
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }
    
    private String implementationURL = null;
	public void setImplementationURL(String implementationURL) {
		this.implementationURL = implementationURL;
		
	}
	
    public String getImplementationURL() {
        return (this.implementationURL);
    }
    
    private String implementationVendor = null;
	public void setImplementationVendor(String implementationVendor) {
		this.implementationVendor = implementationVendor;
		
	}
	
    public String getImplementationVendor() {
        return (this.implementationVendor);
    }

    private String implementationVendorId = null;

    public String getImplementationVendorId() {
        return (this.implementationVendorId);
    }

    public void setImplementationVendorId(String implementationVendorId) {
        this.implementationVendorId = implementationVendorId;
    }

    private String implementationVersion = null;

    public String getImplementationVersion() {
        return (this.implementationVersion);
    }

    public void setImplementationVersion(String implementationVersion) {
        this.implementationVersion = implementationVersion;
    }

    private String specificationVendor = null;

    public String getSpecificationVendor() {
        return (this.specificationVendor);
    }

    public void setSpecificationVendor(String specificationVendor) {
        this.specificationVendor = specificationVendor;
    }

    private String specificationVersion = null;

    public String getSpecificationVersion() {
        return (this.specificationVersion);
    }

    public void setSpecificationVersion(String specificationVersion) {
        this.specificationVersion = specificationVersion;
    }
}
