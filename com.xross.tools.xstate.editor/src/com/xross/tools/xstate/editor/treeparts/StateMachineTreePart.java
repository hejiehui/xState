package com.xross.tools.xstate.editor.treeparts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateMachineTreePart extends AbstractTreeEditPart {
    public StateMachineTreePart(Object model) {
        super(model);
     }

    protected List<StateNode> getModelChildren() {
    	return ((StateMachine)getModel()).getNodes();
    }
}
