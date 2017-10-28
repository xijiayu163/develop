package org.apache.catalina.util;

import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestResource {
	public static final int SYSTEM = 1;
	public static final int WAR = 2;
	
	
    private ArrayList<Extension> availableExtensions = null;
    private ArrayList<Extension> requiredExtensions = null;
    
	private String resourceName = null;
    private int resourceType = -1;
	
    public String getResourceName() {
        return resourceName;
    }
    
	public ManifestResource(String resourceName, Manifest manifest, int resourceType) {
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		processManifest(manifest);
	}

    private void processManifest(Manifest manifest) {
        availableExtensions = getAvailableExtensions(manifest);
        requiredExtensions = getRequiredExtensions(manifest);
    }

	private ArrayList<Extension> getAvailableExtensions(Manifest manifest) {
		Attributes attributes = manifest.getMainAttributes();
        String name = attributes.getValue("Extension-Name");
        if (name == null)
            return null;

        ArrayList<Extension> extensionList = new ArrayList<Extension>();

        Extension extension = new Extension();
        extension.setExtensionName(name);
        extension.setImplementationURL(
            attributes.getValue("Implementation-URL"));
        extension.setImplementationVendor(
            attributes.getValue("Implementation-Vendor"));
        extension.setImplementationVendorId(
            attributes.getValue("Implementation-Vendor-Id"));
        extension.setImplementationVersion(
            attributes.getValue("Implementation-Version"));
        extension.setSpecificationVersion(
            attributes.getValue("Specification-Version"));

        extensionList.add(extension);

        return extensionList;
	}
	
	private ArrayList<Extension> getRequiredExtensions(Manifest manifest) {
		Attributes attributes = manifest.getMainAttributes();
        String names = attributes.getValue("Extension-List");
        if (names == null)
            return null;

        ArrayList<Extension> extensionList = new ArrayList<Extension>();
        names += " ";

        while (true) {

            int space = names.indexOf(' ');
            if (space < 0)
                break;
            String name = names.substring(0, space).trim();
            names = names.substring(space + 1);

            String value =
                attributes.getValue(name + "-Extension-Name");
            if (value == null)
                continue;
            Extension extension = new Extension();
            extension.setExtensionName(value);
            extension.setImplementationURL
                (attributes.getValue(name + "-Implementation-URL"));
            extension.setImplementationVendorId
                (attributes.getValue(name + "-Implementation-Vendor-Id"));
            String version = attributes.getValue(name + "-Implementation-Version");
            extension.setImplementationVersion(version);
            extension.setSpecificationVersion
                (attributes.getValue(name + "-Specification-Version"));
            extensionList.add(extension);
        }
        return extensionList;
	}
}
