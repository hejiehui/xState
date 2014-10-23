package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class AddStateMachineCommand extends Command {
	private StateMachineDiagram parent;
	private StateMachine amchine;
	private int oldIndex;
	private int index;
	
	public AddStateMachineCommand(StateMachineDiagram parent, StateMachine unit, int index){
		this.parent = parent;
		this.amchine = unit;
		oldIndex = parent.indexOf(unit);
		this.index = index;
	}
	
	public void execute() {
		parent.move(index, amchine);
	}

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.move(oldIndex, amchine);
    }
}