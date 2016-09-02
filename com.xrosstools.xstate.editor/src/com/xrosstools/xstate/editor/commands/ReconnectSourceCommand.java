package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class ReconnectSourceCommand extends Command {

	private StateTransition transition;
	private StateNode oldSource;
	private StateNode newSource;

	public ReconnectSourceCommand(StateTransition transition, StateNode newSource){
		this.transition = transition;
		this.newSource = newSource;
		oldSource = transition.getSource();
	}
	
	public boolean canExecute() {
		return transition.getSource().equals(newSource);
	}

	public void execute() {
		oldSource.removeOutput(transition);
		transition.setSource(newSource);
		newSource.addOutput(transition);
	}

	public void undo() {
		newSource.removeOutput(transition);
		transition.setSource(oldSource);
		oldSource.addOutput(transition);
	}
}