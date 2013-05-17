package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.StateMachineDiagramGraphicalEditor;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.requests.StateMachineLayoutRequest;

public class StateMachineLayoutAction extends WorkbenchPartAction implements StateMachineActionConstants {
	private boolean horizantal;
	private float alignment;
	
	public StateMachineLayoutAction(IWorkbenchPart part, String id, boolean horizantal, float alignment){
		super(part);
		setId(ID_PREFIX + id);
		this.alignment = alignment;
		this.horizantal = horizantal;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	private Command createAlignmentCommand() {
		StateMachineDiagramGraphicalEditor editor = (StateMachineDiagramGraphicalEditor)getWorkbenchPart();
		StateMachineLayoutRequest request = new StateMachineLayoutRequest((StateMachineDiagram)editor.getRootEditPart().getContents().getModel(), horizantal, alignment);
		return editor.getRootEditPart().getContents().getCommand(request);
	}

	public void run() {
		execute(createAlignmentCommand());
	}
}