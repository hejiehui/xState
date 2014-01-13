package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.commands.ChangeExitActionCommand;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.parts.ImplementationFinder;
import com.xross.tools.xstate.editor.parts.ImplementationSource;

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
