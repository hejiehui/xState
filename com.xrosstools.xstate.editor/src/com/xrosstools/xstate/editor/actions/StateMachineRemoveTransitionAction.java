package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.ChangeTransitionActionCommand;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateMachineRemoveTransitionAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateTransition transition;
	public StateMachineRemoveTransitionAction(IWorkbenchPart part, StateTransition transition){
		super(part);
		setId(ID_PREFIX + REMOVE_TRANSIT_ACTION);
		setText(REMOVE_TRANSIT_ACTION_MSG);
		this.transition = transition;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new ChangeTransitionActionCommand(transition, null));
	}
}
