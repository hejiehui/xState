package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.RouteStyle;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class CreateTransitionCommand extends Command {
	private StateTransition transition;
	private RouteStyle style;
	private StateMachine machine;
	private StateNode source;
	private StateNode target;

	public CreateTransitionCommand(RouteStyle style) {
	    this.style = style;
	}
	
	public void execute() {
		transition = new StateTransition(source, target, style, machine.getHelper());
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
	
	public RouteStyle getStyle() {
	    return style;
	}
}