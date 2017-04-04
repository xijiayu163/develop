package org.apache.coyote;

public class Response {
	/**
     * Action hook.
     */
	ActionHook hook;
	Request request;

	public void setHook(ActionHook hook) {
		this.hook = hook;
	}

	public void setRequest(Request request) {
		this.request=request;
	}
}
