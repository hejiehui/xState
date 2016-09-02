package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateTransition;

public class ChangeTransitionActionCommand extends Command{
    private StateTransition transition;
    private String oldValue;
    private String newValue;
    
    public ChangeTransitionActionCommand(StateTransition transition, String newValue){
    	this.transition = transition;
    	this.newValue = newValue;
    	oldValue = transition.getTransitAction();
    }
    
    public void execute() {
    	transition.setTransitAction(newValue);
    }

    public String getLabel() {
        return "Change transition action";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	transition.setTransitAction(oldValue);
    }
}
