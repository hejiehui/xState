package com.xrosstools.xstate.idea.editor.policies;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.commands.CreateTransitionCommand;
import com.xrosstools.xstate.idea.editor.commands.DeleteNodeCommand;
import com.xrosstools.xstate.idea.editor.commands.ReconnectSourceCommand;
import com.xrosstools.xstate.idea.editor.commands.ReconnectTargetCommand;
import com.xrosstools.xstate.idea.editor.model.*;

public class StateNodeComponentEditPolicy extends EditPolicy {
	public Command getDeleteCommand() {
		return new DeleteNodeCommand(
				(StateMachine)(getHost().getParent().getModel()),
				(StateNode)(getHost().getModel()));
	}

	public boolean isSelectableSource(Object connectionModel) {
		return !(getHost().getModel() instanceof EndNode);
	}

	public Command getCreateConnectionCommand(Object newConnectionModel, AbstractGraphicalEditPart sourcePart) {
		if(sourcePart == getHost())
			return null;

		CreateTransitionCommand cmd = new CreateTransitionCommand(RouteStyle.direct);
		cmd.setSource((StateNode)sourcePart.getModel());
		cmd.setTarget((StateNode)getHost().getModel());
		cmd.setStateMachine((StateMachine)getHost().getParent().getModel());
		return cmd;
	}

	public Command getReconnectSourceCommand(AbstractConnectionEditPart connectionPart) {
		return new ReconnectSourceCommand(
				(StateTransition)connectionPart.getModel(),
				(StateNode)getHost().getModel());
	}

	public Command getReconnectTargetCommand(AbstractConnectionEditPart connectionPart) {
		return new ReconnectTargetCommand(
				(StateTransition)connectionPart.getModel(),
				(StateNode)getHost().getModel());
	}
}
