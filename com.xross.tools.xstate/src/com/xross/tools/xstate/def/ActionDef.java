package com.xross.tools.xstate.def;

public class ActionDef {
	private String implName;

	public ActionDef(String implName) {
		this.implName = implName;
	}
	
	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}
	
	public Object create() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return Class.forName(implName).newInstance();
	}
}
