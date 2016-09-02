package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.ChangeExitActionCommand;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.parts.ImplementationFinder;
import com.xrosstools.xstate.editor.parts.ImplementationSource;

public class StateMachineOpenExitAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages, ImplementationSource{
	private StateNode node;
	private ImplementationFinder finder;
	public StateMachineOpenExitAction(IWorkbenchPart part, StateNode node, ImplementationFinder finder){
		super(part);
		setId(ID_PREFIX + OPEN_ENTRY_ACTION);
		setText(OPEN_EXIT_ACTION_MSG);
		this.node = node;
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
		return node.getExitAction();
	}

	@Override
	public void implChanged(String newImpl) {
		execute(new ChangeExitActionCommand(node, newImpl));
	}
}
