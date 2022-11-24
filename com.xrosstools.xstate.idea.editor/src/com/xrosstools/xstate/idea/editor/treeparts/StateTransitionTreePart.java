package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import java.beans.PropertyChangeListener;

public class StateTransitionTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
    private StateTransition transition;
    public StateTransitionTreePart(Object model) {
        super(model);
        this.transition = (StateTransition)model;
    }

    @Override
    public String getText() {
        return transition.getDisplayLabel();
    }
}
