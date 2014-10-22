package com.xross.tools.xstate.editor.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.xross.tools.xstate.editor.commands.CreateStateMachineCommand;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramLayoutPolicy extends FlowLayoutEditPolicy {
	/**
	 * @return <code>true</code> if the host's LayoutManager is in a horizontal
	 *         orientation
	 */
	protected boolean isHorizontal() {
		return false;
	}

	protected Command getCreateCommand(CreateRequest request) {
		if(!(request.getNewObject() instanceof StateMachine))
			return null;
        
		return new CreateStateMachineCommand(
        		(StateMachineDiagram)getHost().getModel(),
        		(StateMachine)request.getNewObject(), getIndex(request));
    }

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		return null;
//		return new AddStateNodeCommand(
//				(StateMachine)child.getParent().getModel(),
//        		(UnitNodeContainer)getHost().getModel(),
//        		(UnitNode)child.getModel(), getIndex(after));
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return null;
//		return new UnitNodeContainerMoveChildCommand(
//        		(UnitNodeContainer)getHost().getModel(),
//        		(UnitNode)child.getModel(), getIndex(after));
	}

	private int getIndex(Request request) {
		return getIndex(getInsertionReference(request));
	}
	
	private int getIndex(EditPart after) {
		StateMachineDiagram container = (StateMachineDiagram)getHost().getModel();
		int index = -1;
		if(after == null)
			index = container.getMachines().size();
		else
			index = container.getMachines().indexOf((StateMachine)after.getModel());
		
		return index;
	}
}
