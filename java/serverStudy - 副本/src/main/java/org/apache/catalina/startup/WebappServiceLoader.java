package org.apache.catalina.startup;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;

import org.apache.catalina.Context;

public class WebappServiceLoader<T> {
	
	private final Context context;
	private final ServletContext servletContext;
	private final Pattern containerSciFilterPattern;
	
	public WebappServiceLoader(Context context) {
        this.context = context;
        this.servletContext = context.getServletContext();
        String containerSciFilter = context.getContainerSciFilter();
        if (containerSciFilter != null && containerSciFilter.length() > 0) {
            containerSciFilterPattern = Pattern.compile(containerSciFilter);
        } else {
            containerSciFilterPattern = null;
        }
    }

	public List<T> load(Class<T> serviceType) throws IOException {
		return null;
	}
}
