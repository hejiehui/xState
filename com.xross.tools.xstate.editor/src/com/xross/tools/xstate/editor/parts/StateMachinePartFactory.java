package com.xross.tools.xstate.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateMachinePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		
		if(model == null)
			part = null;
		
		if(model instanceof StateMachineDiagram)
			part = new StateMachineDiagramPart();
		else
		if(model instanceof StateMachine)
			part = new StateMachinePart();
		else
		if(model instanceof StateNode)
			part = new StateNodePart();
		else
		if(model instanceof StateMachineTransition)
			part = new StateTransitionPart();
		
		part.setModel(model);
		
		return part;
	}
}
