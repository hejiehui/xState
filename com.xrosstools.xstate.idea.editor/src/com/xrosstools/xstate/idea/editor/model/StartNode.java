package com.xrosstools.xstate.idea.editor.model;

import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

public class StartNode extends StateNode {
	public StartNode() {
		setId("start");
	}
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_ID, PROP_ID),
			};
	}
}
