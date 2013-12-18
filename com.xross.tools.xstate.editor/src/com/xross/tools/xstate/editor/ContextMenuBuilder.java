package com.xross.tools.xstate.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;

public interface ContextMenuBuilder {
	public void buildContextMenu(IMenuManager menu, IWorkbenchPart editor);
}
