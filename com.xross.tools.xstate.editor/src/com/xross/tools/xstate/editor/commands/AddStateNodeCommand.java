package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;

public class AddStateNodeCommand extends Command {
	private StateMachine parent;
	private StateNode node;
	private StateMachine oldParent;
	
	public AddStateNodeCommand(StateMachine parent, StateNode node, StateMachine oldParent){
		this.parent = parent;
		this.node = node;
		this.oldParent = oldParent;
	}
	
	public void execute() {
		oldParent.removeNode(node);
		parent.addNode(node);
	}
	
    public String getLabel() {
        return "Add node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.removeNode(node);
    	oldParent.addNode(node);
	}
}
