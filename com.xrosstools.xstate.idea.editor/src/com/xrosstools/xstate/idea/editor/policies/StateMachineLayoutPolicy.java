package com.xrosstools.xstate.idea.editor.policies;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.commands.AddStateNodeCommand;
import com.xrosstools.xstate.idea.editor.commands.CreateNodeCommand;
import com.xrosstools.xstate.idea.editor.commands.DeleteStateMachineCommand;
import com.xrosstools.xstate.idea.editor.commands.MoveNodeCommand;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.parts.StateMachinePart;

import java.awt.*;

public class StateMachineLayoutPolicy extends EditPolicy {
//
//    protected Command createAddCommand(EditPart child, Object constraint) {
//    	if(!(getHost().getModel() instanceof StateMachine))
//    		return null;
//
//    	if(!(child.getModel() instanceof StateNode))
//    		return null;
//
//    	Rectangle constr = (Rectangle)constraint;
//    	return new AddStateNodeCommand(
//        		(StateMachine)getHost().getModel(),
//        		(StateNode)child.getModel(),
//        		(StateMachine)child.getParent().getModel(),
//        		constr.getTopLeft()
//        		);
//    }

    public Command getDeleteCommand() {
        return new DeleteStateMachineCommand(
                (StateMachineDiagram)(getHost().getParent().getModel()),
                (StateMachine)(getHost().getModel()));
    }

    public Command getMoveCommand(AbstractGraphicalEditPart child, Rectangle constraint) {
        //check if move can also support add
        Object model = child.getModel();
        if(!(model instanceof StateNode))
            return null;
            
        MoveNodeCommand cmd = new MoveNodeCommand();
        cmd.setNode((StateNode)model);
        cmd.setMoveDelta(constraint.getLocation());
        return cmd;
    }

    public Command getAddCommand(AbstractGraphicalEditPart target, AbstractGraphicalEditPart child, Rectangle constraint) {
//        return target instanceof StateMachinePart ?
//                new AddStateNodeCommand((StateMachine)target.getModel(), (StateNode)child.getModel(), (StateMachine)child.getParent().getModel(), constraint.getLocation()) :
//                null;
        return null;
    }

    public Command getCreateCommand(Object newModel, Point location) {
    	if(!(newModel instanceof StateNode))
			return null;
        return new CreateNodeCommand(
        		(StateMachine)getHost().getModel(),
        		(StateNode)newModel,
        		location);
    }
}