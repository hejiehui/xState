package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateNode;

public class ChangeExitActionCommand extends Command{
    private StateNode node;
    private String oldValue;
    private String newValue;
    
    public ChangeExitActionCommand(StateNode node, String newValue){
    	this.node = node;
    	this.newValue = newValue;
    	oldValue = node.getExitAction();
    }
    
    public void execute() {
    	node.setExitAction(newValue);
    }

    public String getLabel() {
        return "Change node action";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	node.setExitAction(oldValue);
    }
}
