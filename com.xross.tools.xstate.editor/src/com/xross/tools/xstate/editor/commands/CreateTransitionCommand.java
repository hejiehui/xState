package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;

public class CreateTransitionCommand extends Command {
	private StateTransition transition;
	private StateMachine machine;
	private StateNode source;
	private StateNode target;

	public void execute() {
		transition = new StateTransition(source, target, machine.getHelper());
	}

	public void redo() {
		source.addOutput(transition);
		target.addInput(transition);
	}

	public void setSource(StateNode source) {
		this.source = source;
	}

	public void setTarget(StateNode target) {
		this.target = target;
	}
	
	public void setStateMachine(StateMachine machine) {
		this.machine = machine;
	}

	public void undo() {
		source.removeOutput(transition);
		target.removeInput(transition);
	}
}