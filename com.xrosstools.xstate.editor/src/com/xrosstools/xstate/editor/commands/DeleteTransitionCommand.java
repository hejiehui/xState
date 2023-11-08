package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateTransition;

public class DeleteTransitionCommand extends Command {
	private StateTransition transition;
	private int outputIndex;
	private int inputIndex;
	public DeleteTransitionCommand(StateTransition transition){
		this.transition = transition;
		inputIndex = transition.getTarget().getInputs().indexOf(transition);
		outputIndex = transition.getSource().getOutputs().indexOf(transition);
	}
	
    public void execute() {
    	transition.getSource().removeOutput(transition);
    	transition.getTarget().removeInput(transition);
    }

    public String getLabel() {
        return "Delete transition";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	transition.getSource().addOutput(outputIndex, transition);
    	transition.getTarget().addInput(inputIndex, transition);
    }
}
