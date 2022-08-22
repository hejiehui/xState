package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xstate.idea.editor.model.*;

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
		
		return null;
	}
}
