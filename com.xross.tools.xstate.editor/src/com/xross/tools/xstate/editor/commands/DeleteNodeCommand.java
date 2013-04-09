package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;

public class DeleteNodeCommand extends Command{
    private StateMachine stateMachine;
    private StateNode node;
    
    public DeleteNodeCommand(
    		StateMachine stateMachine, 
    		StateNode node){
    	this.stateMachine = stateMachine;
    	this.node = node;
    }
    
    public void execute() {
    	stateMachine.removeNode(node);
        for(StateTransition path: node.getOutputs()){
        	path.getChild().setInput(null);
        }
        
        if(node.getInput() == null)
        	return;
    	node.getInput().getParent().removeOutput(node.getInput());
    }

    public String getLabel() {
        return "Delete Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        diagram.addNode(node);
        for(StateTransition path: node.getOutputs()){
        	path.getChild().setInput(path);
        }
        
        if(node.getInput() == null)
        	return;
    	node.getInput().getParent().addOutput(node.getInput());
    }
}
