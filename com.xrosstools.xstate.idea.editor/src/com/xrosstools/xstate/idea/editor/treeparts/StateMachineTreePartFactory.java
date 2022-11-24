package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.idea.gef.parts.EditContext;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xstate.idea.editor.model.*;

public class StateMachineTreePartFactory  implements EditPartFactory {
    @Override
    public EditPart createEditPart(EditPart parent, Object model) {
        AbstractTreeEditPart part = null;
        if(model == null)
            return part;

        if(model instanceof StateMachineDiagram)
            part = new StateMachineDiagramTreePart(model);
        else if(model instanceof StateMachine)
            part = new StateMachineTreePart(model);
        else if(model instanceof StateNode)
            part = new StateNodeTreePart(model);
        else if(model instanceof StateTransition)
            part = new StateTransitionTreePart(model);
		else if(model instanceof Event)
            part = new EventTreePart(model);

		if (part == null )
		    return null;

        part.setModel(model);
		return part;
	}
}
