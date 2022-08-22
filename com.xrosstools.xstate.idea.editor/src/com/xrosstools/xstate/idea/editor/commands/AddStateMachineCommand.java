package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;

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