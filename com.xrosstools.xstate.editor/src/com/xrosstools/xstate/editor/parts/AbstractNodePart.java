package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.ui.part.EditorPart;

import com.xrosstools.xstate.editor.commands.CreateTransitionCommand;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineConstants;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.policies.StateMachineGraphicNodeEditPolicy;
import com.xrosstools.xstate.editor.policies.StateNodeComponentEditPolicy;

public abstract class AbstractNodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {
    protected abstract IFigure createFigure();
    protected abstract void updateFigure(StateNode node);
    protected abstract Dimension getSize();

    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new CommonStyleAnchor(getFigure(), ((StateTransitionPart)connection).getStyle(), true);
    }

    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new CommonStyleAnchor(getFigure(), ((StateTransitionPart)connection).getStyle(), false);
    }

    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        CreateConnectionRequest req = (CreateConnectionRequest)request;
        CreateTransitionCommand cmd = (CreateTransitionCommand)req.getStartCommand();
        
        return new CommonStyleAnchor(getFigure(), cmd.getStyle(), true);
    }

    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        CreateConnectionRequest req = (CreateConnectionRequest)request;
        CreateTransitionCommand cmd = (CreateTransitionCommand)req.getStartCommand();
        
        return new CommonStyleAnchor(getFigure(), cmd.getStyle(), false);
    }
    
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateNodeComponentEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new StateMachineGraphicNodeEditPolicy());
    }
    
    public StateNode getStateNode() {
        return (StateNode)getModel();
    }

    protected List<StateTransition> getModelSourceConnections() {
        return getStateNode().getOutputs();
    }

    protected List<StateTransition> getModelTargetConnections() {
        return getStateNode().getInputs();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(StateNode.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(StateNode.PROP_OUTPUTS))
            refreshSourceConnections();
        else
            refreshVisuals();
    }
    
    public void activate() {
        super.activate();
        getStateNode().getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
        super.deactivate();
        getStateNode().getListeners().removePropertyChangeListener(this);
    }
    
    protected void refreshVisuals() {
        StateNode node = getStateNode();
        
        Point loc = node.getLocation();
        updateFigure(node);
        Dimension size = getSize();
        
        Rectangle constraint = new Rectangle(loc, size);
        StateMachinePart parent = (StateMachinePart)getParent();
        parent.setLayoutConstraint(this, getFigure(), constraint);
    }    
}
