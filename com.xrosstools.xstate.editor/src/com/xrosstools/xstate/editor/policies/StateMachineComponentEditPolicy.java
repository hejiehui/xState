package com.xrosstools.xstate.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xrosstools.xstate.editor.commands.DeleteStateMachineCommand;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;

public class StateMachineComponentEditPolicy  extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteStateMachineCommand(
				(StateMachineDiagram)(getHost().getParent().getModel()), 
				(StateMachine)(getHost().getModel()));
	}
}
