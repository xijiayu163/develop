package org.apache.catalina.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.catalina.Globals;

public class CatalinaProperties {
	private static Properties properties = null;
	
	static {
        loadProperties();
    }

	private static void loadProperties() {
		InputStream is = null;
		try {
            File home = new File(getCatalinaBase());
            //F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src\conf
            File conf = new File(home, "conf");
            //F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src\conf\catalina.properties
            File propsFile = new File(conf, "catalina.properties");
            is = new FileInputStream(propsFile);
        } catch (Throwable t) {
        	t.printStackTrace();
        }
		
		if (is != null) {
            try {
                properties = new Properties();
                properties.load(is);
            } catch (Throwable t) {
            	t.printStackTrace();
            }
            finally
            {
                try {
                    is.close();
                } catch (IOException ioe) {
                }
            }
        }
	}

	private static String getCatalinaBase() {
		return System.getProperty(Globals.CATALINA_BASE_PROP, getCatalinaHome());
	}

	private static String getCatalinaHome() {
		return System.getProperty(Globals.CATALINA_HOME_PROP,
                System.getProperty("user.dir"));
	}

    public static String getProperty(String name) {
        return properties.getProperty(name);

    }
}
