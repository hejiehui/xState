package com.xrosstools.xstate.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.StateTransition;

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
    
    protected Image getImage() {
        return Activator.getDefault().getImage(Activator.STATE_TRANSITION);
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
        refreshVisuals();
    }
}
