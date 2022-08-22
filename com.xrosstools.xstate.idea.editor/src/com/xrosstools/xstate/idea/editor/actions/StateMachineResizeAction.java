package com.xrosstools.xstate.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;

public class StateMachineResizeAction extends Action implements StateMachineActionConstants {
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	
	public StateMachineResizeAction(String id, boolean nodeSize, boolean horizantal, boolean increase){
		setText(id);
		this.horizantal = horizantal;
		this.increase = increase;
		this.nodeSize = nodeSize;
	}
	
	public Command createCommand() {
//		StateMachineDiagramGraphicalEditor editor = (StateMachineDiagramGraphicalEditor)getWorkbenchPart();
//		StateNodeResizeRequest request = new StateNodeResizeRequest((StateMachineDiagram)editor.getRootEditPart().getContents().getModel(), nodeSize, horizantal, increase);
//		return editor.getRootEditPart().getContents().getCommand(request);
		return null;
	}
}
