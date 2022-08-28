package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.AbstractAnchor;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.policies.StateNodeComponentEditPolicy;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class AbstractNodePart extends AbstractGraphicalEditPart implements PropertyChangeListener {
    protected abstract Figure createFigure();
    protected abstract void updateFigure(StateNode node);
    protected abstract Dimension getSize();

    public AbstractAnchor getSourceConnectionAnchor(AbstractConnectionEditPart connection) {
        return new CommonStyleAnchor(getFigure(), ((StateTransitionPart)connection).getStyle(), true);
    }

    public AbstractAnchor getTargetConnectionAnchor(AbstractConnectionEditPart connection) {
        return new CommonStyleAnchor(getFigure(), ((StateTransitionPart)connection).getStyle(), false);
    }

    protected EditPolicy createEditPolicy() {
        return new StateNodeComponentEditPolicy();
//        installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateNodeComponentEditPolicy());
//        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new StateMachineGraphicNodeEditPolicy());
    }
    
    public StateNode getStateNode() {
        return (StateNode)getModel();
    }

    public List getModelSourceConnections() {
        return (List)getStateNode().getOutputs();
    }

    public List getModelTargetConnections() {
        return (List)getStateNode().getInputs();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(StateNode.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(StateNode.PROP_OUTPUTS))
            refreshSourceConnections();
        else
            refreshVisuals();
    }

    protected void refreshVisuals() {
        StateNode node = getStateNode();
        
        Point loc = node.getLocation();
        getFigure().setLocation(loc);
        updateFigure(node);
    }
}
