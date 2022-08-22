package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.RouteStyle;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

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

	@Override
	public String getLabel() {
		return "";
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