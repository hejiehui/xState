package com.xrosstools.xstate.idea.editor.policies;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.commands.DeleteTransitionCommand;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

public class StateTransitionComponentEditPolicy extends EditPolicy {

	public Command getDeleteCommand() {
		return new DeleteTransitionCommand(
				(StateTransition)(getHost().getModel()));
	}
}
