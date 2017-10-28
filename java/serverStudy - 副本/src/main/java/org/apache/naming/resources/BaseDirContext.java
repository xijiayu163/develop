package org.apache.naming.resources;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class BaseDirContext implements DirContext{
	
	protected Hashtable<String,Object> env;
	protected boolean cached = true;
	protected int cacheTTL = 5000; // 5s
	protected int cacheMaxSize = 10240; // 10 MB
	protected int cacheObjectMaxSize = 512; // 512 K
	protected String docBase = null;
	
	public BaseDirContext() {
        this.env = new Hashtable<String,Object>();
    }
	
	public Object lookup(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object lookup(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public void bind(Name name, Object obj) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void bind(String name, Object obj) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void rebind(Name name, Object obj) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void rebind(String name, Object obj) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void unbind(Name name) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void unbind(String name) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void rename(Name oldName, Name newName) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void rename(String oldName, String newName) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroySubcontext(Name name) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void destroySubcontext(String name) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public Context createSubcontext(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Context createSubcontext(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object lookupLink(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object lookupLink(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NameParser getNameParser(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NameParser getNameParser(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Name composeName(Name name, Name prefix) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public String composeName(String name, String prefix) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object removeFromEnvironment(String propName) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Hashtable<?, ?> getEnvironment() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public String getNameInNamespace() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Attributes getAttributes(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Attributes getAttributes(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Attributes getAttributes(Name name, String[] attrIds) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Attributes getAttributes(String name, String[] attrIds) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public void modifyAttributes(Name name, int mod_op, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void modifyAttributes(String name, int mod_op, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void modifyAttributes(Name name, ModificationItem[] mods) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void modifyAttributes(String name, ModificationItem[] mods) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void bind(Name name, Object obj, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void bind(String name, Object obj, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void rebind(Name name, Object obj, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public void rebind(String name, Object obj, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		
	}

	public DirContext createSubcontext(Name name, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public DirContext createSubcontext(String name, Attributes attrs) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public DirContext getSchema(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public DirContext getSchema(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public DirContext getSchemaClassDefinition(Name name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public DirContext getSchemaClassDefinition(String name) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name name, Attributes matchingAttributes, String[] attributesToReturn)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(String name, Attributes matchingAttributes,
			String[] attributesToReturn) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name name, Attributes matchingAttributes) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(String name, Attributes matchingAttributes) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name name, String filter, SearchControls cons)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(String name, String filter, SearchControls cons)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(Name name, String filterExpr, Object[] filterArgs,
			SearchControls cons) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingEnumeration<SearchResult> search(String name, String filterExpr, Object[] filterArgs,
			SearchControls cons) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/***********************自定义方法********************/
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	
    public boolean isCached() {
        return cached;
    }

	public void setCacheTTL(int cacheTTL) {
		this.cacheTTL = cacheTTL;
	}
	
    public int getCacheTTL() {
        return cacheTTL;
    }

    public void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }
    
    public int getCacheMaxSize() {
        return cacheMaxSize;
    }

    public void setCacheObjectMaxSize(int cacheObjectMaxSize) {
        this.cacheObjectMaxSize = cacheObjectMaxSize;
    }
    
    public int getCacheObjectMaxSize() {
        return cacheObjectMaxSize;
    }

	public void setDocBase(String docBase) {
		this.docBase = docBase;
	}
	
    public String getDocBase() {
        return (this.docBase);
    }

	public void allocate() {
		// No action taken by the default implementation
	}
}
