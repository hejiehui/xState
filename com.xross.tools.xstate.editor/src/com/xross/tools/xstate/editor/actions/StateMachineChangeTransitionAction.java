package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.StateMachineDiagramGraphicalEditor;
import com.xross.tools.xstate.editor.commands.ChangeTransitionActionCommand;
import com.xross.tools.xstate.editor.model.Event;
import com.xross.tools.xstate.editor.model.StateTransition;
import com.xross.tools.xstate.editor.parts.ImplementationFinder;

public class StateMachineChangeTransitionAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateTransition transition;
	private ImplementationFinder finder;
	public StateMachineChangeTransitionAction(IWorkbenchPart part, StateTransition transition, ImplementationFinder finder){
		super(part);
		setId(ID_PREFIX + CHANGE_TRANSIT_ACTION);
		setText(CHANGE_TRANSIT_ACTION_MSG);
		this.transition = transition;
		this.finder = finder;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		String impl = finder.assignImpl(transition.getTransitAction());
		execute(new ChangeTransitionActionCommand(transition, impl));
	}
}
