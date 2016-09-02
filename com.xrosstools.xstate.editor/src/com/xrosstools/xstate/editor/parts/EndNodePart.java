package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.StateMachineConstants;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.policies.StateMachineGraphicNodeEditPolicy;
import com.xrosstools.xstate.editor.policies.StateNodeComponentEditPolicy;

public class EndNodePart extends AbstractGraphicalEditPart implements StateMachineConstants, PropertyChangeListener, NodeEditPart {
    private DirectEditManager manager;
	protected IFigure createFigure() {
		return new ImageFigure(Activator.getDefault().getImageRegistry().get(Activator.END_NODE));
    }
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
	}

	public void performRequest(Request req) {
//		if (req.getType() == RequestConstants.REQ_DIRECT_EDIT){
//            if (manager == null) {
//                StateNodeFigure figure = (StateNodeFigure) getFigure();
//                manager = new StateNodeDirectEditManager(this, ((StateMachine)getParent().getModel()).getFactors(), TextCellEditor.class, new StateNodeCellEditorLocator(figure));
//            }
//            manager.show();
//		}
	}
	
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new StateNodeDirectEditPolicy(((StateMachine)getParent().getModel()).getFactors()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateNodeComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new StateMachineGraphicNodeEditPolicy());
	}
	
    protected List<StateTransition> getModelSourceConnections() {
    	return ((StateNode)getModel()).getOutputs();
    }

    protected List<StateTransition> getModelTargetConnections() {
    	return ((StateNode)getModel()).getInputs();
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
    	((StateNode) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	((StateNode) getModel()).getListeners().removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
    	StateNode node = (StateNode) getModel();
    	ImageFigure figure = (ImageFigure)getFigure();

		Point loc = node.getLocation();
		Dimension size = figure.getPreferredSize();//node.getSize();
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    }
}
