package com.xrosstools.xstate.def;

import com.xrosstools.xstate.NullAction;

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
		if(implName == null || implName.length() == 0)
			return NullAction.instance;
		return Class.forName(implName).newInstance();
	}
}
