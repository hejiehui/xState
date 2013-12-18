package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateNode;

public class ChangeEntryActionCommand extends Command{
    private StateNode node;
    private String oldValue;
    private String newValue;
    
    public ChangeEntryActionCommand(StateNode node, String newValue){
    	this.node = node;
    	this.newValue = newValue;
    	oldValue = node.getEntryAction();
    }
    
    public void execute() {
    	node.setEntryAction(newValue);
    }

    public String getLabel() {
        return "Change node action";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	node.setEntryAction(oldValue);
    }
}
