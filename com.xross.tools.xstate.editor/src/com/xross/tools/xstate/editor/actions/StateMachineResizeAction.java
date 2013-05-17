package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.StateMachineDiagramGraphicalEditor;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.requests.StateNodeResizeRequest;

public class StateMachineResizeAction extends WorkbenchPartAction implements StateMachineActionConstants {
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	
	public StateMachineResizeAction(IWorkbenchPart part, String id, boolean nodeSize, boolean horizantal, boolean increase){
		super(part);
		setId(ID_PREFIX + id);
		this.horizantal = horizantal;
		this.increase = increase;
		this.nodeSize = nodeSize;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	private Command createAlignmentCommand() {
		StateMachineDiagramGraphicalEditor editor = (StateMachineDiagramGraphicalEditor)getWorkbenchPart();
		StateNodeResizeRequest request = new StateNodeResizeRequest((StateMachineDiagram)editor.getRootEditPart().getContents().getModel(), nodeSize, horizantal, increase);
		return editor.getRootEditPart().getContents().getCommand(request);
	}

	public void run() {
		execute(createAlignmentCommand());
	}
}
