package com.xrosstools.xstate.idea.editor.policies;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.commands.AddStateMachineCommand;
import com.xrosstools.xstate.idea.editor.commands.CreateStateMachineCommand;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;

import java.awt.*;

public class StateMachineDiagramLayoutPolicy extends EditPolicy {
	/**
	 * @return <code>true</code> if the host's LayoutManager is in a horizontal
	 *         orientation
	 */
	protected boolean isHorizontal() {
		return false;
	}

	public Command getCreateCommand(Object newModel, Point location) {
		if(!(newModel instanceof StateMachine))
			return null;
        
		return new CreateStateMachineCommand(
        		(StateMachineDiagram)getHost().getModel(),
				(StateMachine)newModel, getIndex(location));
    }

	@Override
	public Command getMoveCommand(AbstractGraphicalEditPart child, Rectangle constraint) {
    	if(!(child.getModel() instanceof StateMachine))
    		return null;

    	return new AddStateMachineCommand(
        		(StateMachineDiagram)getHost().getModel(),
        		(StateMachine)child.getModel(),
        		getIndex(constraint.getLocation())
        		);
	}

	private int getIndex(Point location) {
	    return getHost().getFigure().getInsertionIndex();
	}
}
