package com.xross.tools.xstate.editor.treeparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateMachineTreePartFactory  implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof StateMachineDiagram)
			return new StateMachineDiagramTreePart(model);

		if(model instanceof StateMachine)
			return new StateMachineTreePart(model);

		if(model instanceof StateNode)
			return new StateNodeTreePart(model);
		
		return null;
	}
}
