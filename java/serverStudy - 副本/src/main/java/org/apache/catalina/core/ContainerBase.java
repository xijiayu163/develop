package org.apache.catalina.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.directory.DirContext;

import org.apache.catalina.Container;
import org.apache.catalina.ContainerEvent;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.util.LifecycleBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.naming.resources.ProxyDirContext;

import sun.util.logging.resources.logging;

public abstract class ContainerBase extends LifecycleBase implements Container{
	protected Pipeline pipeline = new StandardPipeline(this);
	protected HashMap<String, Container> children =
	        new HashMap<String, Container>();
	
	protected Container parent = null;
	protected String name = null;
	protected ThreadPoolExecutor startStopExecutor;
	protected List<ContainerListener> listeners =
            new CopyOnWriteArrayList<ContainerListener>();
	
	protected Loader loader = null;
	private int startStopThreads = 1;
	protected DirContext resources = null;
	
	//��̨����߳�
	private Thread thread = null;
	protected int backgroundProcessorDelay = -1;
	private volatile boolean threadDone=false;
	protected PropertyChangeSupport support = new PropertyChangeSupport(this);
	protected ClassLoader parentClassLoader = null;
	
    public String getName() {
        return (name);
    }
    
	public void setName(String name) {
		this.name=name;
	}
	
    public Pipeline getPipeline() {
        return (this.pipeline);
    }
    
    public Loader getLoader() {
        if (loader != null)
            return (loader);
        if (parent != null)
            return (parent.getLoader());
        return (null);
    }
    
    public synchronized void setLoader(Loader loader) {
    	Loader oldLoader = this.loader;
    	if (oldLoader == loader)
            return;
        this.loader = loader;
        
     // Stop the old component if necessary
        if (getState().isAvailable() && (oldLoader != null) &&
            (oldLoader instanceof Lifecycle)) {
            try {
                ((Lifecycle) oldLoader).stop();
            } catch (LifecycleException e) {
            }
        }
        
        // Start the new component if necessary
        if (loader != null)
            loader.setContainer(this);
        if (getState().isAvailable() && (loader != null) &&
                (loader instanceof Lifecycle)) {
                try {
                    ((Lifecycle) loader).start();
                } catch (LifecycleException e) {
                }
            }
        
        // Report this property change to interested listeners
        support.firePropertyChange("loader", oldLoader, this.loader);
    }
    
    public void addChild(Container child) {
    	addChildInternal(child);
    }
    
    private void addChildInternal(Container child){
    	synchronized(children){
    		child.setParent(this);
            children.put(child.getName(), child);
    	}
    	
    	try {
    		log.debug("添加子容器,子容器类:"+child.getClass().getName()+"，并启动...");
			child.start();
		} catch (LifecycleException e) {
		}finally {
			
		}
    }
    
    /**
     * ��ʼ��startstop�߳�
     * 
     */
    protected void initInternal() throws LifecycleException {
    	log.debug("initInternal...");
        BlockingQueue<Runnable> startStopQueue =
            new LinkedBlockingQueue<Runnable>();
        startStopExecutor = new ThreadPoolExecutor(
                getStartStopThreadsInternal(),
                getStartStopThreadsInternal(), 10, TimeUnit.SECONDS,
                startStopQueue,
                new StartStopThreadFactory(getName() + "-startStop-"));
        startStopExecutor.allowCoreThreadTimeOut(true);
        log.debug("创建startStopExecutor，执行器前缀名:"+getName() + "-startStop-");
    }
    
    private int getStartStopThreadsInternal() {
        int result = getStartStopThreads();

        // Positive values are unchanged
        if (result > 0) {
            return result;
        }

        // Zero == Runtime.getRuntime().availableProcessors()
        // -ve  == Runtime.getRuntime().availableProcessors() + value
        // These two are the same
        result = Runtime.getRuntime().availableProcessors() + result;
        if (result < 1) {
            result = 1;
        }
        return result;
    }
    
    public void setStartStopThreads(int startStopThreads){
    	this.startStopThreads = startStopThreads;
    	
    	// Use local copies to ensure thread safety
    	ThreadPoolExecutor executor = startStopExecutor;
    	if (executor != null) {
            int newThreads = getStartStopThreadsInternal();
            executor.setMaximumPoolSize(newThreads);
            executor.setCorePoolSize(newThreads);
        }
    }
    
    public int getStartStopThreads(){
    	return startStopThreads;
    }
    
    protected synchronized void startInternal() throws LifecycleException{
    	if ((resources != null) && (resources instanceof Lifecycle))
    	{
    		log.debug("resources不为空，为生命周期对象，调用其start");
    		((Lifecycle) resources).start();
    	}
    	
    	log.debug("遍历孩子，使用startStopExecutor并行启动所有孩子,使用Future特性阻塞等待直到所有的孩子成功启动完成");
    	Container children[] = findChildren();
        List<Future<Void>> results = new ArrayList<Future<Void>>();
        for (int i = 0; i < children.length; i++) {
            results.add(startStopExecutor.submit(new StartChild(children[i])));
        }
        
        boolean fail = false;
        for (Future<Void> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                fail = true;
            }
        }
        
        if (fail) {
            throw new LifecycleException("containerBase.threadedStartFailed");
        }
        
        log.debug("如果关联pipeline为生命周期对象，调用其start");
    	if (pipeline instanceof Lifecycle)
    	{
    		log.debug("关联pipeline为生命周期对象，调用其start...");
    		((Lifecycle) pipeline).start();
    	}
    	
    	setState(LifecycleState.STARTING);
    	
//    	log.debug("启动后台监控线程,该线程执行无限循环，监控变动等等以作处理");
//        threadStart();
    }
    
    protected void threadStart() {
    	if (thread != null)
            return;
    	if (backgroundProcessorDelay <= 0)
            return;
    	
    	threadDone = false;
    	String threadName = "ContainerBackgroundProcessor[" + toString() + "]";
    	 thread = new Thread(new ContainerBackgroundProcessor(), threadName);
    	 thread.setDaemon(true);
         thread.start();
	}

	public void setParent(Container container) {
        Container oldParent = this.parent;
        this.parent = container;
    }
    
	public Container getParent() {
		return this.parent;
	}
	
    public ClassLoader getParentClassLoader() {
        if (parentClassLoader != null)
            return (parentClassLoader);
        if (parent != null) {
            return (parent.getParentClassLoader());
        }
        return (ClassLoader.getSystemClassLoader());
        
    }
    
    public void setParentClassLoader(ClassLoader parent) {
        ClassLoader oldParentClassLoader = this.parentClassLoader;
        this.parentClassLoader = parent;
        support.firePropertyChange("parentClassLoader", oldParentClassLoader,
                                   this.parentClassLoader);

    }
	
    public DirContext getResources() {
        if (resources != null)
            return (resources);
        if (parent != null)
            return (parent.getResources());
        return (null);

    }
    
	private Container getMappingObject() {
		return this;
	}

    public int getBackgroundProcessorDelay() {
        return backgroundProcessorDelay;
    }
    
    public void setBackgroundProcessorDelay(int delay) {
        backgroundProcessorDelay = delay;
    }
	
    public synchronized void setResources(DirContext resources) {
    	DirContext oldResources = this.resources;
    	if (oldResources == resources)
            return;
    	
    	Hashtable<String, String> env = new Hashtable<String, String>();
    	if (getParent() != null){
    		env.put(ProxyDirContext.HOST, getParent().getName());
    	}
    	env.put(ProxyDirContext.CONTEXT, getName());	
    	this.resources = new ProxyDirContext(env, resources);
    	support.firePropertyChange("resources", oldResources, this.resources);
    }
    
    public Container[] findChildren() {
        synchronized (children) {
            Container results[] = new Container[children.size()];
            return children.values().toArray(results);
        }
    }
    
    public Container findChild(String name) {
        if (name == null)
            return (null);
        synchronized (children) {
            return children.get(name);
        }
    }
    
	@Override
	protected void stopInternal() throws LifecycleException {
		Container children[] = findChildren();
        List<Future<Void>> results = new ArrayList<Future<Void>>();
        for (int i = 0; i < children.length; i++) {
            results.add(startStopExecutor.submit(new StartChild(children[i])));
        }
        boolean fail = false;
        for (Future<Void> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                fail = true;
            }
        }
        
        if (fail) {
            throw new LifecycleException("containerBase.threadedStartFailed");
        }
        
        if (pipeline instanceof Lifecycle)
            ((Lifecycle) pipeline).start();
        
        setState(LifecycleState.STARTING);
	}

	@Override
	protected void destroyInternal() throws LifecycleException {
		
	}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
	
    public void addContainerListener(ContainerListener listener) {
        listeners.add(listener);
    }
    
    public void removeContainerListener(ContainerListener listener) {
        listeners.remove(listener);
    }
	
    public void fireContainerEvent(String type, Object data) {

        if (listeners.size() < 1)
            return;

        ContainerEvent event = new ContainerEvent(this, type, data);
        // Note for each uses an iterator internally so this is safe
        for (ContainerListener listener : listeners) {
            listener.containerEvent(event);
        }
    }
    
    public synchronized void addValve(Valve valve) {
        pipeline.addValve(valve);
    }
	
    public void backgroundProcess() {
        if (!getState().isAvailable())
            return;
        
        if (loader != null) {
            try {
            	log.debug("loader不为空,调用loader.backgroundProcess");
                loader.backgroundProcess();
            } catch (Exception e) {
            }
        }
        
        log.debug("链式调用关联pipeline的所有Valve,逐个调用其backgroundProcess");
        Valve current = pipeline.getFirst();
        while (current != null) {
            try {
                current.backgroundProcess();
            } catch (Exception e) {
            }
            current = current.getNext();
        }
        log.debug("触发生命周事件PERIODIC_EVENT");
        fireLifecycleEvent(Lifecycle.PERIODIC_EVENT, null);
    }
    
	private static class StartChild implements Callable<Void> {
		private Log log = LogFactory.getLog(this.getClass());
		
        private Container child;

        public StartChild(Container child) {
            this.child = child;
        }

        public Void call() throws LifecycleException {
        	log.debug("由containerBase启动孩子，当前孩子类名为:"+child.getClass().getName());
            child.start();
            return null;
        }
    }
	
    private static class StartStopThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public StartStopThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }
    
    protected class ContainerBackgroundProcessor implements Runnable {

		public void run() {
			Throwable t = null;
            try {
            	while (!threadDone) {
            		try {
                        Thread.sleep(backgroundProcessorDelay * 1000L);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
            		if (!threadDone) {
            			Container parent = (Container) getMappingObject();
            			ClassLoader cl = 
                                Thread.currentThread().getContextClassLoader();
            			if (parent.getLoader() != null) {
            				 cl = parent.getLoader().getClassLoader();
            			}
            			
            			log.debug("processChildren,cl类名为:"+cl.getClass().getName());
            			processChildren(parent, cl);
            		}
            	}
            	
            }catch (RuntimeException e) {
            	t = e;
                throw e;
            }catch (Error e) {
                t = e;
                throw e;
            }finally {
                if (!threadDone) {
                }
            }
		}
    
		protected void processChildren(Container container, ClassLoader cl) {
            try {
                if (container.getLoader() != null) {
                	log.debug("container.getLoader()不为空，设置当前线程类加载器为container.getLoader():"+container.getLoader().getClass().getName());
                    Thread.currentThread().setContextClassLoader
                        (container.getLoader().getClassLoader());
                }
                log.debug("调用backgroundProcess,后台处理");
                container.backgroundProcess();
            } catch (Throwable t) {
            } finally {
            	log.debug("恢复当前线程类加载器为cl");
                Thread.currentThread().setContextClassLoader(cl);
            }
            Container[] children = container.findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i].getBackgroundProcessorDelay() <= 0) {
                	log.debug("递归调用children的processChildren");
                    processChildren(children[i], cl);
                }
            }
        }
    }
}
