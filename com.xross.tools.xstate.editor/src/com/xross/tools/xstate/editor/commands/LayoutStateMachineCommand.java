package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class LayoutStateMachineCommand extends Command {
	private StateMachineDiagram diagram;
	private boolean oldOrintation;
	private boolean orintation;
	private float oldAlignment;
	private float alignment;
	public LayoutStateMachineCommand(StateMachineDiagram diagram, boolean isHorizantal, float alignment){
		this.diagram = diagram;
		oldOrintation = diagram.isHorizantal();
		oldAlignment = diagram.getAlignment();
		this.alignment = alignment;
		orintation = isHorizantal;
	}
	
    public void execute() {
    	diagram.setAlignment(alignment); 
    	diagram.setHorizantal(orintation);
    }

    public void redo() {
    	execute();
    }

    public void undo() {
    	diagram.setAlignment(oldAlignment);
    	diagram.setHorizantal(oldOrintation);
    }
}
