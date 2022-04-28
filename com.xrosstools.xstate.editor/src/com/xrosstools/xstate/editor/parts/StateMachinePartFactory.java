package com.xrosstools.xstate.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

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
			part = new ImageNodePart(Activator.START_NODE);
		else
		if(model instanceof EndNode)
			part = new ImageNodePart(Activator.END_NODE );
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
