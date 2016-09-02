package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.ChangeEntryActionCommand;
import com.xrosstools.xstate.editor.model.StateNode;

public class StateMachineRemoveEntryAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateNode node;
	public StateMachineRemoveEntryAction(IWorkbenchPart part, StateNode node){
		super(part);
		setId(ID_PREFIX + REMOVE_ENTRY_ACTION);
		setText(REMOVE_ENTRY_ACTION_MSG);
		this.node = node;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new ChangeEntryActionCommand(node, null));
	}
}
