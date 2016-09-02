package com.xrosstools.xstate.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.xrosstools.xstate.editor.commands.CreateTransitionCommand;
import com.xrosstools.xstate.editor.commands.ReconnectSourceCommand;
import com.xrosstools.xstate.editor.commands.ReconnectTargetCommand;
import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateMachineGraphicNodeEditPolicy extends GraphicalNodeEditPolicy {
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		if(getHost().getModel() instanceof StartNode)
			return null;
		
		CreateTransitionCommand cmd = (CreateTransitionCommand)request.getStartCommand();
		cmd.setTarget((StateNode)getHost().getModel());
		cmd.setStateMachine((StateMachine)getHost().getParent().getModel());
		return cmd;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		if(getHost().getModel() instanceof EndNode)
			return null;
		
		CreateTransitionCommand cmd = new CreateTransitionCommand();
		cmd.setSource((StateNode)getHost().getModel());
		request.setStartCommand(cmd);
		return cmd;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return new ReconnectSourceCommand(
				(StateTransition)request.getConnectionEditPart().getModel(), 
				(StateNode)getHost().getModel());
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return new ReconnectTargetCommand(
				(StateTransition)request.getConnectionEditPart().getModel(), 
				(StateNode)getHost().getModel());
	}

}
