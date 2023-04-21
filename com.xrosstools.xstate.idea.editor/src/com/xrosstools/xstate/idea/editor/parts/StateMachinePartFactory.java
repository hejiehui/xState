package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xstate.idea.editor.StateMachineIcons;
import com.xrosstools.xstate.idea.editor.model.*;

public class StateMachinePartFactory implements EditPartFactory {
	@Override
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
			part = new ImageNodePart(StateMachineIcons.START_NODE);
		else
		if(model instanceof EndNode)
			part = new ImageNodePart(StateMachineIcons.END_NODE );
		else
		if(model instanceof StateNode)
			part = new StateNodePart();
		else
		if(model instanceof StateTransition)
			part = new StateTransitionPart();

		if (part == null )
			return null;

		part.setModel(model);
		return part;
	}
}
