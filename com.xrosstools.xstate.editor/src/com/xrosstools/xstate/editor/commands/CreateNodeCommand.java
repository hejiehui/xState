package com.xrosstools.xstate.editor.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;

public class CreateNodeCommand extends Command{
    private StateMachine stateMachine;
    private StateNode node;
    private Point location;
    
    public CreateNodeCommand(
    		StateMachine stateMachine, 
    		StateNode node, 
    		Point location){
    	this.stateMachine = stateMachine;
    	this.node = node;
    	this.location = location;
    }
    
    public void execute() {
        node.setLocation(location);
        stateMachine.addNode(node);
    }

    public String getLabel() {
        return "Create Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	stateMachine.removeNode(node);
    }
}
