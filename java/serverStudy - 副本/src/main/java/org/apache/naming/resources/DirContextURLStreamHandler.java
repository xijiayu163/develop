package org.apache.naming.resources;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Hashtable;

import javax.naming.directory.DirContext;

import org.apache.catalina.loader.WebappClassLoaderBase;

public class DirContextURLStreamHandler extends URLStreamHandler{

    /**
     * Bindings thread - directory context. Keyed by thread id.
     */
    private static Hashtable<Thread,DirContext> threadBindings =
        new Hashtable<Thread,DirContext>();
	private static Hashtable<ClassLoader,DirContext> clBindings =
        new Hashtable<ClassLoader,DirContext>();
	
	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void bindThread(DirContext dirContext) {
		threadBindings.put(Thread.currentThread(), dirContext);
	}

	public static void unbindThread() {
		threadBindings.remove(Thread.currentThread());
	}

	public static void bind(WebappClassLoaderBase cl, DirContext dirContext) {
		 clBindings.put(cl, dirContext);
	}
	
    public static void unbind(ClassLoader cl) {
        clBindings.remove(cl);
    }
	
}
