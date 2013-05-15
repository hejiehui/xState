package com.xross.tools.xstate.editor.treeparts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xross.tools.xstate.editor.Activator;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateMachineTreePart extends AbstractTreeEditPart {
    public StateMachineTreePart(Object model) {
        super(model);
     }

    protected List<StateNode> getModelChildren() {
    	return ((StateMachine)getModel()).getNodes();
    }
    
    protected String getText() {
    	StateMachine stateMachine = (StateMachine)getModel();
    	return stateMachine.getName();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(Activator.STATE_MACHINE);
    }
}
