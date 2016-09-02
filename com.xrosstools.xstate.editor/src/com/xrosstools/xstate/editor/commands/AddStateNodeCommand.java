package com.xrosstools.xstate.editor.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;

public class AddStateNodeCommand extends Command {
	private StateMachine parent;
	private StateNode node;
	private StateMachine oldParent;
	private Point oldPosition;
	private Point newPosition;
	
	public AddStateNodeCommand(StateMachine parent, StateNode node, StateMachine oldParent, Point newPosition){
		this.parent = parent;
		this.node = node;
		this.oldParent = oldParent;
		oldPosition = new Point(node.getLocation());
		this.newPosition = newPosition;
	}
	
	public void execute() {
		oldParent.removeNode(node);
		parent.addNode(node);
		node.setLocation(newPosition);
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
		node.setLocation(oldPosition);
	}
}
