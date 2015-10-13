package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class DeleteStateMachineCommand  extends Command{
    private StateMachineDiagram diagram;
    private StateMachine stateMachine;
    private int index;
    
    public DeleteStateMachineCommand(
    		StateMachineDiagram diagram, 
    		StateMachine stateMachine){
    	this.diagram = diagram;
    	this.stateMachine = stateMachine;
    	index = diagram.indexOf(stateMachine);
    }
    
    public void execute() {
    	diagram.removeMachine(stateMachine);
    }

    public String getLabel() {
        return "Delete State Machine";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	diagram.addMachine(index, stateMachine);
    }
}
