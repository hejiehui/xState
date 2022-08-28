package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.parts.*;
import com.xrosstools.xstate.idea.editor.StateMachineEditorProvider;
import com.xrosstools.xstate.idea.editor.model.*;

public class StateMachinePartFactory implements EditPartFactory {
	private EditContext editContext;

	public StateMachinePartFactory(EditContext editContext) {
		this.editContext = editContext;
	}

	public EditPart createEditPart(EditPart parent, Object model) {
		AbstractGraphicalEditPart part = null;

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

		if (part == null )
			return null;

		part.setEditPartFactory(this);
		part.setModel(model);
		part.setParent(parent);
		part.setContext(editContext);
		editContext.add(part, model);
		
		return part;
	}
}
