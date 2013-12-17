package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateNode;

public class AssignClassCommand extends Command{
    private boolean isEntyAction;
    private StateNode node;
    private String value;
    private String oldValue;
    
    public AssignClassCommand(StateNode node, boolean isEntyAction, String value){
    	this.node = node;
    	this.isEntyAction = isEntyAction;
    	oldValue = isEntyAction ? node.getEntryAction() : node.getExitAction();
    	this.value = value;
    }
    
    public void execute() {
    	if(isEntyAction)
    		node.setEntryAction(value);
    	else
    		node.setExitAction(value);
    }

    public String getLabel() {
        return "Change action class";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(isEntyAction)
    		node.setEntryAction(oldValue);
    	else
    		node.setExitAction(oldValue);
    }
}
