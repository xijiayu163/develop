package org.apache.catalina.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class ExtensionValidator {
	private static volatile ArrayList<Extension> containerAvailableExtensions = null;
	private static ArrayList<ManifestResource> containerManifestResources =
	        new ArrayList<ManifestResource>();

	static {

        // check for container level optional packages
        String systemClasspath = System.getProperty("java.class.path");

        StringTokenizer strTok = new StringTokenizer(systemClasspath, File.pathSeparator);

        // build a list of jar files in the classpath
        while (strTok.hasMoreTokens()) {
            String classpathItem = strTok.nextToken();
            if (classpathItem.toLowerCase().endsWith(".jar")) {
                File item = new File(classpathItem);
                if (item.isFile()) {
                    try {
                        addSystemResource(item);
                    } catch (IOException e) {
                    }
                }
            }
        }

        // add specified folders to the list
        addFolderList("java.ext.dirs");
    }
	
	public static void addSystemResource(File jarFile) throws IOException {
		InputStream is = null;
        try {
            is = new FileInputStream(jarFile);
            Manifest manifest = getManifest(is);
            if (manifest != null) {
                ManifestResource mre = new ManifestResource(jarFile.getAbsolutePath(), manifest,
                        ManifestResource.SYSTEM);
                containerManifestResources.add(mre);
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
	}
	
	private static void addFolderList(String property) {
		 String extensionsDir = System.getProperty(property);
	        if (extensionsDir != null) {
	            StringTokenizer extensionsTok
	                = new StringTokenizer(extensionsDir, File.pathSeparator);
	            while (extensionsTok.hasMoreTokens()) {
	                File targetDir = new File(extensionsTok.nextToken());
	                if (!targetDir.isDirectory()) {
	                    continue;
	                }
	                File[] files = targetDir.listFiles();
	                if (files == null) {
	                    continue;
	                }
	                for (int i = 0; i < files.length; i++) {
	                    if (files[i].getName().toLowerCase().endsWith(".jar") &&
	                            files[i].isFile()) {
	                        try {
	                            addSystemResource(files[i]);
	                        } catch (IOException e) {
	                        }
	                    }
	                }
	            }
	        }
	}

	private static Manifest getManifest(InputStream inStream)
            throws IOException {

        Manifest manifest = null;
        JarInputStream jin = null;

        try {
            jin = new JarInputStream(inStream);
            manifest = jin.getManifest();
            jin.close();
            jin = null;
        } finally {
            if (jin != null) {
                try {
                    jin.close();
                } catch (Throwable t) {
                }
            }
        }

        return manifest;
    }
}
