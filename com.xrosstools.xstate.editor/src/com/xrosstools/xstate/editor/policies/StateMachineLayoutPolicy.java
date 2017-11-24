package com.xrosstools.xstate.editor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.xrosstools.xstate.editor.commands.AddStateNodeCommand;
import com.xrosstools.xstate.editor.commands.CreateNodeCommand;
import com.xrosstools.xstate.editor.commands.LayoutStateMachineCommand;
import com.xrosstools.xstate.editor.commands.MoveNodeCommand;
import com.xrosstools.xstate.editor.commands.ResizeNodeCommand;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.requests.StateMachineLayoutRequest;
import com.xrosstools.xstate.editor.requests.StateNodeResizeRequest;

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

    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
        if (!(child instanceof AbstractGraphicalEditPart))
            return null;
        
        MoveNodeCommand cmd = new MoveNodeCommand();
        cmd.setNode((StateNode) child.getModel());
        cmd.setConstraint((Rectangle)constraint);
        return cmd;
    }

    public Command getCommand(Request request) {
    	if(request.getType() == RequestConstants.REQ_ALIGN){
    		StateMachineLayoutRequest layoutReq = (StateMachineLayoutRequest)request;
    		return new LayoutStateMachineCommand(layoutReq.getDiagram(), layoutReq.isHorizantal(), layoutReq.getAlignment());
    	}
    	
    	if(request.getType() == RequestConstants.REQ_RESIZE){
    		StateNodeResizeRequest resizeReq = (StateNodeResizeRequest)request;
    		return new ResizeNodeCommand(resizeReq.getDiagram(), resizeReq.isNodeSize(), resizeReq.isHorizantal(), resizeReq.isIncrease());
    	}
    	
    	return super.getCommand(request);
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