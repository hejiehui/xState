package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

public class DeleteNodeCommand extends Command {
    private StateMachine stateMachine;
    private StateNode node;
    
    public DeleteNodeCommand(
    		StateMachine stateMachine,
    		StateNode node){
    	this.stateMachine = stateMachine;
    	this.node = node;
    }
    
    public void execute() {
        for(StateTransition transition: node.getOutputs()){
        	transition.getTarget().removeInput(transition);
        }
        
        for(StateTransition transition: node.getInputs()){
        	transition.getSource().removeOutput(transition);
        }
        
    	stateMachine.removeNode(node);
    }

    public String getLabel() {
        return "Delete Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	stateMachine.addNode(node);
        for(StateTransition transition: node.getOutputs()){
        	transition.getTarget().addInput(transition);
        }
        
        for(StateTransition transition: node.getInputs()){
        	transition.getSource().addOutput(transition);
        }
    }
}
