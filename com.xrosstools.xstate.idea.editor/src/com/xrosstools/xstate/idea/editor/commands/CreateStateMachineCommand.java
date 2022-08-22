package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;

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
