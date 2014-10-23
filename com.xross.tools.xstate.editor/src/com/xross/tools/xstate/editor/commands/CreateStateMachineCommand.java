package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class CreateStateMachineCommand extends Command {
	private StateMachineDiagram diagram;
	private StateMachine machine;
	private int index;
	
	public CreateStateMachineCommand(StateMachineDiagram diagram, StateMachine machine, int index){
		this.diagram = diagram;
		this.machine = machine;
		this.index = index;
	}
	
	public void execute() {
		diagram.addMachine(index, machine);
	}
	
    public String getLabel() {
        return "Add state machine";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	diagram.removeMachine(machine);
	}
}
