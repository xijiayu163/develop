package org.apache.catalina;

import java.util.Set;

import javax.servlet.ServletContext;

public interface JarScanner {
	public void scan(ServletContext context, ClassLoader classloader,
            JarScannerCallback callback, Set<String> jarsToSkip);
}
