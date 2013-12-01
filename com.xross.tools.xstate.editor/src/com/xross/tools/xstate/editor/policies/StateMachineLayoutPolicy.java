package com.xross.tools.xstate.editor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.xross.tools.xstate.editor.commands.AddStateNodeCommand;
import com.xross.tools.xstate.editor.commands.CreateNodeCommand;
import com.xross.tools.xstate.editor.commands.LayoutStateMachineCommand;
import com.xross.tools.xstate.editor.commands.MoveNodeCommand;
import com.xross.tools.xstate.editor.commands.ResizeNodeCommand;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.requests.StateMachineLayoutRequest;
import com.xross.tools.xstate.editor.requests.StateNodeResizeRequest;

public class StateMachineLayoutPolicy extends XYLayoutEditPolicy {

    protected Command createAddCommand(EditPart child, Object constraint) {
    	if(!(getHost().getModel() instanceof StateMachine))
    		return null;
    	
    	return new AddStateNodeCommand(
        		(StateMachine)getHost().getModel(),
        		(StateNode)child.getModel(),
        		(StateMachine)child.getParent().getModel()
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
    	
//    	if(request.getType() == RequestConstants.REQ_RESIZE){
//    		StateNodeResizeRequest resizeReq = (StateNodeResizeRequest)request;
//    		return new ResizeNodeCommand(resizeReq.getDiagram(), resizeReq.isNodeSize(), resizeReq.isHorizantal(), resizeReq.isIncrease());
//    	}
    	
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