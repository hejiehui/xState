package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.ChangeExitActionCommand;
import com.xrosstools.xstate.editor.model.StateNode;

public class StateMachineRemoveExitAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateNode node;
	public StateMachineRemoveExitAction(IWorkbenchPart part, StateNode node){
		super(part);
		setId(ID_PREFIX + REMOVE_EXIT_ACTION);
		setText(REMOVE_EXIT_ACTION_MSG);
		this.node = node;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new ChangeExitActionCommand(node, null));
	}
}
