package com.xrosstools.xstate.editor.treeparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateMachineTreePartFactory  implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof StateMachineDiagram)
			return new StateMachineDiagramTreePart(model);

		if(model instanceof StateMachine)
			return new StateMachineTreePart(model);

		if(model instanceof StateNode)
			return new StateNodeTreePart(model);
		
		if(model instanceof Event)
			return new EventTreePart(model);
		
        if(model instanceof StateTransition)
            return new StateTransitionTreePart(model);
		
		return null;
	}
}
