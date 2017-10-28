package org.apache.catalina.startup;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.catalina.Globals;
import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.apache.catalina.startup.ClassLoaderFactory.RepositoryType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Log4jConfigurer;

public class Bootstrap {
	ClassLoader commonLoader = null;
    ClassLoader catalinaLoader = null;
    ClassLoader sharedLoader = null;
	private Object catalinaDaemon = null;
	
	private static Log log = LogFactory.getLog(Bootstrap.class);
	
	static {
        try {
			Log4jConfigurer.initLogging("classpath:log4j.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		try {
			log.debug("tomcat 初始化");
			bootstrap.init();
			log.debug("tomcat 加载资源");
			bootstrap.load();
			log.debug("tomcat 启动");
			bootstrap.start();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private void load() throws Exception{
		String methodName = "load";
		Method method = catalinaDaemon.getClass().getMethod(methodName);
		method.invoke(catalinaDaemon);
        log.debug("反射调用Catalina的load加载资源");
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		//F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src
		setCatalinaHome();
		log.debug("设置CATALINA_HOME_PROP 为"+ System.getProperty("user.dir"));
		//F:\tomcat7\tomcat_src\apache-tomcat-7.0.76-src
		setCatalinaBase();
		log.debug("设置CATALINA_BASE_PROP 为"+ System.getProperty("user.dir"));
		initClassLoaders();
		log.debug("设置commonLoader,并将catalinaLoader,sharedLoader引用指向commonLoader");
		Thread.currentThread().setContextClassLoader(catalinaLoader);
		log.debug("设置当前线程下文类加载器为catalinaLoader");
		
		Class<?> startupClass = catalinaLoader.loadClass("org.apache.catalina.startup.Catalina");
		log.debug("catalinaLoader 加载Catalina");
		Object startupInstance = startupClass.newInstance();
		String methodName = "setParentClassLoader";
		Class<?> paramTypes[] = new Class[1];
        paramTypes[0] = Class.forName("java.lang.ClassLoader");
        Object paramValues[] = new Object[1];
        paramValues[0] = sharedLoader;
        Method method =
            startupInstance.getClass().getMethod(methodName, paramTypes);
        method.invoke(startupInstance, paramValues);
        log.debug("反射生成Catalina的实例，并设置Catalina父类加载器为sharedLoader");
        
        catalinaDaemon = startupInstance;
	}
	
	public void start() throws Exception {
		Method method = catalinaDaemon.getClass().getMethod("start", (Class[]) null);
		method.invoke(catalinaDaemon, (Object[]) null);
		log.debug("反射调用Catalina的start");
	}

	
	private void initClassLoaders() {
		log.debug("初始化类加载器");
		try {
			commonLoader = createClassLoader("common", null);
			log.debug("commonLoader 创建完毕");
			URL[] extURLs = ((URLClassLoader)commonLoader).getURLs();  
			StringBuilder sBuilder = new StringBuilder();
            for (int i = 0; i < extURLs.length; i++) {  
            	sBuilder.append(extURLs[i]+"\r\n");
            	log.debug("commonLoader url加载路径包括:");
            	log.debug(sBuilder.toString());
            }  
			
			if( commonLoader == null ) {
                // no config file, default to this loader - we might be in a 'single' env.
                commonLoader=this.getClass().getClassLoader();
                log.debug("commonLoader 为空，没有配置文件，使用当前类胡类加载器作为commonLoader");
            }
			catalinaLoader = createClassLoader("server", commonLoader);
			sharedLoader = createClassLoader("shared", commonLoader);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private ClassLoader createClassLoader(String name, ClassLoader parent) throws Exception {
		log.debug("开始创建类加载器,name:"+name+" parent 类名:"+(parent==null?"为空":parent.getClass().getName()));
		String value = CatalinaProperties.getProperty(name + ".loader");
		log.debug("获取属性"+name+".loader的值,value:"+value);
		if ((value == null) || (value.equals("")))
		{
			log.debug("value 为空，直接返回parent");
			return parent;
		}
		 value = replace(value);
		 log.debug("设置value 字符串替换后值为:"+value);
		 
		 List<Repository> repositories = new ArrayList<Repository>();
		 
		 StringTokenizer tokenizer = new StringTokenizer(value, ",");
		 
		 log.debug("遍历value逗号分割成的数组");
		 while (tokenizer.hasMoreElements()) {
			 String repository = tokenizer.nextToken().trim();
			 log.debug("repository:"+repository);
			 if (repository.length() == 0) {
	                continue;
	         }
			 if (repository.endsWith("*.jar")) {
	                repository = repository.substring
	                    (0, repository.length() - "*.jar".length());
	                repositories.add(
	                        new Repository(repository, RepositoryType.GLOB));
	            } else if (repository.endsWith(".jar")) {
	                repositories.add(
	                        new Repository(repository, RepositoryType.JAR));
	            } else {
	                repositories.add(
	                        new Repository(repository, RepositoryType.DIR));
	            }
		 }
		 
		 return ClassLoaderFactory.createClassLoader(repositories, parent);
	}
	
	protected String replace(String str) {
        // Implementation is copied from ClassLoaderLogManager.replace(),
        // but added special processing for catalina.home and catalina.base.
        String result = str;
        int pos_start = str.indexOf("${");
        if (pos_start >= 0) {
            StringBuilder builder = new StringBuilder();
            int pos_end = -1;
            while (pos_start >= 0) {
                builder.append(str, pos_end + 1, pos_start);
                pos_end = str.indexOf('}', pos_start + 2);
                if (pos_end < 0) {
                    pos_end = pos_start - 1;
                    break;
                }
                String propName = str.substring(pos_start + 2, pos_end);
                String replacement;
                if (propName.length() == 0) {
                    replacement = null;
                } else if (Globals.CATALINA_HOME_PROP.equals(propName)) {
                    replacement = getCatalinaHome();
                } else if (Globals.CATALINA_BASE_PROP.equals(propName)) {
                    replacement = getCatalinaBase();
                } else {
                    replacement = System.getProperty(propName);
                }
                if (replacement != null) {
                    builder.append(replacement);
                } else {
                    builder.append(str, pos_start, pos_end + 1);
                }
                pos_start = str.indexOf("${", pos_end + 1);
            }
            builder.append(str, pos_end + 1, str.length());
            result = builder.toString();
        }
        return result;
    }

	private void setCatalinaHome() {
		//F:\github\develop\java\serverStudy
		System.setProperty(Globals.CATALINA_HOME_PROP,System.getProperty("user.dir"));
	}
	
	private void setCatalinaBase() {
		System.setProperty(Globals.CATALINA_BASE_PROP,System.getProperty(Globals.CATALINA_HOME_PROP));
	}
	
	public static String getCatalinaHome() {
        return System.getProperty(Globals.CATALINA_HOME_PROP,
                                  System.getProperty("user.dir"));
    }
	
    public static String getCatalinaBase() {
        return System.getProperty(Globals.CATALINA_BASE_PROP, getCatalinaHome());
    }
}
