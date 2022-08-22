package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xstate.idea.editor.StateMachineEditorProvider;
import com.xrosstools.xstate.idea.editor.model.*;

public class StateMachinePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		
		if(model == null)
			part = null;
		else
		if(model instanceof StateMachineDiagram)
			part = new StateMachineDiagramPart();
		else
		if(model instanceof StateMachine)
			part = new StateMachinePart();
		else
		if(model instanceof StartNode)
			part = new ImageNodePart(StateMachineEditorProvider.START_NODE);
		else
		if(model instanceof EndNode)
			part = new ImageNodePart(StateMachineEditorProvider.END_NODE );
		else
		if(model instanceof StateNode)
			part = new StateNodePart();
		else
		if(model instanceof StateTransition)
			part = new StateTransitionPart();
		
		part.setModel(model);
		
		return part;
	}
}
