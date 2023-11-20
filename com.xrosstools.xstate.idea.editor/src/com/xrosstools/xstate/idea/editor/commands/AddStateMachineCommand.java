package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;

public class AddStateMachineCommand extends Command {
	private StateMachineDiagram parent;
	private StateMachine mchine;
	private int oldIndex;
	private int index;
	
	public AddStateMachineCommand(StateMachineDiagram parent, StateMachine mchine, int index){
		this.parent = parent;
		this.mchine = mchine;
		oldIndex = parent.indexOf(mchine);
		this.index = index;
	}
	
	public void execute() {
		parent.move(index, mchine);
	}

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.move(oldIndex, mchine);
    }
}