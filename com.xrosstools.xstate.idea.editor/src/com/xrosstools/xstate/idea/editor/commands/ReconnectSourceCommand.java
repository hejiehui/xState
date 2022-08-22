package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

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

	@Override
	public String getLabel() {
		return "";
	}

	public void undo() {
		newSource.removeOutput(transition);
		transition.setSource(oldSource);
		oldSource.addOutput(transition);
	}
}