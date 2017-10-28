package org.apache.catalina.util;

import java.util.Locale;

public class ContextName {
	private static final String ROOT_NAME = "ROOT";
    private static final String VERSION_MARKER = "##";
    private static final String FWD_SLASH_REPLACEMENT = "#";
    
    private final String baseName;
    private final String path;
    private final String version;
    private final String name;
    
    public ContextName(String name, boolean stripFileExtension) {
    	String tmp1 = name;
        
        // Convert Context names and display names to base names
        
        // Strip off any leading "/"
        if (tmp1.startsWith("/")) {
            tmp1 = tmp1.substring(1);
        }
        
        // Replace any remaining /
        tmp1 = tmp1.replaceAll("/", FWD_SLASH_REPLACEMENT);
        
        // Insert the ROOT name if required
        if (tmp1.startsWith(VERSION_MARKER) || "".equals(tmp1)) {
            tmp1 = ROOT_NAME + tmp1;
        }

        // Remove any file extensions
        if (stripFileExtension &&
                (tmp1.toLowerCase(Locale.ENGLISH).endsWith(".war") ||
                        tmp1.toLowerCase(Locale.ENGLISH).endsWith(".xml"))) {
            tmp1 = tmp1.substring(0, tmp1.length() -4);
        }

        baseName = tmp1;
        
        String tmp2;
        // Extract version number
        int versionIndex = baseName.indexOf(VERSION_MARKER);
        if (versionIndex > -1) {
            version = baseName.substring(versionIndex + 2);
            tmp2 = baseName.substring(0, versionIndex);
        } else {
            version = "";
            tmp2 = baseName;
        }

        if (ROOT_NAME.equals(tmp2)) {
            path = "";
        } else {
            path = "/" + tmp2.replaceAll(FWD_SLASH_REPLACEMENT, "/");
        }
        
        if (versionIndex > -1) {
            this.name = path + VERSION_MARKER + version;
        } else {
            this.name = path;
        }
    }

    public String getBaseName() {
        return baseName;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getVersion() {
        return version;
    }
    
    public String getName() {
        return name;
    }
}
