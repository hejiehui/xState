package com.xross.tools.xstate.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xross.tools.xstate.editor.commands.DeleteStateMachineCommand;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineComponentEditPolicy  extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteStateMachineCommand(
				(StateMachineDiagram)(getHost().getParent().getModel()), 
				(StateMachine)(getHost().getModel()));
	}
}
