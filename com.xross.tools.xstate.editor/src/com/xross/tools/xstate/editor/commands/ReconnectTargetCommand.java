package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;

public class ReconnectTargetCommand extends Command {

	private StateTransition transition;
	private StateNode oldTarget;
	private StateNode newTarget;

	public ReconnectTargetCommand(StateTransition transition, StateNode newTarget){
		this.transition = transition;
		this.newTarget = newTarget;
		oldTarget = transition.getTarget();
	}
	
	public boolean canExecute() {
		return transition.getTarget().equals(newTarget);
	}

	public void execute() {
		oldTarget.removeInput(transition);
		transition.setTarget(newTarget);
		newTarget.addInput(transition);
	}

	public void undo() {
		newTarget.removeInput(transition);
		transition.setTarget(oldTarget);
		oldTarget.addInput(transition);
	}
}
