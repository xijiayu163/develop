package org.apache.catalina.deploy;

import java.io.Serializable;

import javax.servlet.Filter;

public class FilterDef implements Serializable{
	private static final long serialVersionUID = 1L;
	
	 private transient Filter filter = null;

	public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    private String filterClass = null;

    public String getFilterClass() {
        return (this.filterClass);
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }
}
