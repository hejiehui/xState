package com.xrosstools.xstate.editor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.xrosstools.xstate.editor.commands.AddStateNodeCommand;
import com.xrosstools.xstate.editor.commands.CreateNodeCommand;
import com.xrosstools.xstate.editor.commands.MoveNodeCommand;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.parts.StateNodePart;

public class StateMachineLayoutPolicy extends XYLayoutEditPolicy {

    protected Command createAddCommand(EditPart child, Object constraint) {
    	if(!(getHost().getModel() instanceof StateMachine))
    		return null;
    	
    	if(!(child.getModel() instanceof StateNode))
    		return null;

    	Rectangle constr = (Rectangle)constraint;
    	return new AddStateNodeCommand(
        		(StateMachine)getHost().getModel(),
        		(StateNode)child.getModel(),
        		(StateMachine)child.getParent().getModel(),
        		constr.getTopLeft()
        		);
    }

    protected Command getMoveChildrenCommand(Request request) {
        ChangeBoundsRequest req = (ChangeBoundsRequest)request;
        if(!(req.getEditParts().get(0) instanceof StateNodePart))
            return null;
            
        MoveNodeCommand cmd = new MoveNodeCommand();
        cmd.setNode((StateNode)((StateNodePart)req.getEditParts().get(0)).getModel());
        cmd.setMoveDelta(req.getMoveDelta());
        return cmd;
    }
    
    protected Command getCreateCommand(CreateRequest request) {
    	if(!(request.getNewObject() instanceof StateNode))
			return null;
        return new CreateNodeCommand(
        		(StateMachine)getHost().getModel(),
        		(StateNode)request.getNewObject(),
        		((Rectangle) getConstraintFor(request)).getLocation());
    }
}