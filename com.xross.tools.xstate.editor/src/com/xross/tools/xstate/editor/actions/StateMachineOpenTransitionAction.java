package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.commands.ChangeTransitionActionCommand;
import com.xross.tools.xstate.editor.model.StateTransition;
import com.xross.tools.xstate.editor.parts.ImplementationFinder;
import com.xross.tools.xstate.editor.parts.ImplementationSource;

public class StateMachineOpenTransitionAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages, ImplementationSource{
	private StateTransition transition;
	private ImplementationFinder finder;
	public StateMachineOpenTransitionAction(IWorkbenchPart part, StateTransition transition, ImplementationFinder finder){
		super(part);
		setId(ID_PREFIX + OPEN_TRANSIT_ACTION);
		setText(OPEN_TRANSIT_ACTION_MSG);
		this.transition = transition;
		this.finder = finder;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		finder.openImpl(this);
	}

	@Override
	public String getImplementation() {
		return transition.getTransitAction();
	}

	@Override
	public void implChanged(String newImpl) {
		execute(new ChangeTransitionActionCommand(transition, newImpl));
	}
}
