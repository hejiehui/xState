package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.commands.ChangeEntryActionCommand;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.parts.ImplementationFinder;

public class StateMachineChangeEntryAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateNode node;
	private ImplementationFinder finder;
	public StateMachineChangeEntryAction(IWorkbenchPart part, StateNode node, ImplementationFinder finder){
		super(part);
		setId(ID_PREFIX + CHANGE_ENTRY_ACTION);
		setText(CHANGE_ENTRY_ACTION_MSG);
		this.node = node;
		this.finder = finder;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		String impl = finder.assignImpl(node.getEntryAction());
		execute(new ChangeEntryActionCommand(node, impl));
	}
}
