package org.apache.catalina.startup;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ClassLoaderFactory {
	
	public static ClassLoader createClassLoader(List<Repository> repositories, final ClassLoader parent) throws Exception {
		Set<URL> set = new LinkedHashSet<URL>();
		if (repositories != null) {
			for (Repository repository : repositories)  {
				if (repository.getType() == RepositoryType.URL) {
					URL url = buildClassLoaderUrl(repository.getLocation());
					set.add(url);
				}else if (repository.getType() == RepositoryType.DIR) {
					File directory = new File(repository.getLocation());
                    directory = directory.getCanonicalFile();
                    if (!validateFile(directory, RepositoryType.DIR)) {
                        continue;
                    }
                    URL url = buildClassLoaderUrl(directory);
                    set.add(url);
				}else if (repository.getType() == RepositoryType.JAR) {
					File file=new File(repository.getLocation());
					file = file.getCanonicalFile();
					if (!validateFile(file, RepositoryType.JAR)) {
	                        continue;
					}
					URL url = buildClassLoaderUrl(file);
					set.add(url);
				}else if (repository.getType() == RepositoryType.GLOB) {
					File directory=new File(repository.getLocation());
                    directory = directory.getCanonicalFile();
                    if (!validateFile(directory, RepositoryType.GLOB)) {
                        continue;
                    }
                    String filenames[] = directory.list();
                    if (filenames == null) {
                        continue;
                    }
                    for (int j = 0; j < filenames.length; j++) {
                        String filename = filenames[j].toLowerCase();
                        if (!filename.endsWith(".jar"))
                            continue;
                        File file = new File(directory, filenames[j]);
                        file = file.getCanonicalFile();
                        if (!validateFile(file, RepositoryType.JAR)) {
                            continue;
                        }
                        URL url = buildClassLoaderUrl(file);
                        set.add(url);
                    }
				}
			}
		}
		
		 final URL[] array = set.toArray(new URL[set.size()]);
		 return AccessController.doPrivileged(
	                new PrivilegedAction<URLClassLoader>() {
	                    public URLClassLoader run() {
	                        if (parent == null)
	                            return new URLClassLoader(array);
	                        else
	                            return new URLClassLoader(array, parent);
	                    }
	                });
		 
	}
	
	private static boolean validateFile(File file, RepositoryType type) throws IOException {
		if (RepositoryType.DIR == type || RepositoryType.GLOB == type) {
            if (!file.exists() || !file.isDirectory() || !file.canRead()) {
                String msg = "Problem with directory [" + file +
                        "], exists: [" + file.exists() +
                        "], isDirectory: [" + file.isDirectory() +
                        "], canRead: [" + file.canRead() + "]";

                File home = new File (Bootstrap.getCatalinaHome());
                home = home.getCanonicalFile();
                File base = new File (Bootstrap.getCatalinaBase());
                base = base.getCanonicalFile();
                return false;
            }
        } else if (RepositoryType.JAR == type) {
            if (!file.exists() || !file.canRead()) {
                return false;
            }
        }
        return true;
	}

	private static URL buildClassLoaderUrl(String urlString) throws MalformedURLException {
        // URLs passed to class loaders may point to directories that contain
        // JARs. If these URLs are used to construct URLs for resources in a JAR
        // the URL will be used as is. It is therefore necessary to ensure that
        // the sequence "!/" is not present in a class loader URL.
        String result = urlString.replaceAll("!/", "%21/");
        return new URL(result);
    }
	
    private static URL buildClassLoaderUrl(File file) throws MalformedURLException {
        // Could be a directory or a file
        String fileUrlString = file.toURI().toString();
        fileUrlString = fileUrlString.replaceAll("!/", "%21/");
        return new URL(fileUrlString);
    }
	
	
	public static enum RepositoryType {
        DIR,
        GLOB,
        JAR,
        URL
    }
	
	public static class Repository {
        private String location;
        private RepositoryType type;
        
        public Repository(String location, RepositoryType type) {
            this.location = location;
            this.type = type;
        }
        
        public String getLocation() {
            return location;
        }
        
        public RepositoryType getType() {
            return type;
        }
    }
}
