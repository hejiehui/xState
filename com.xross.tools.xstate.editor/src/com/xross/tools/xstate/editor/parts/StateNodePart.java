package com.xross.tools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
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
import org.eclipse.gef.tools.DirectEditManager;

import com.xross.tools.xstate.editor.figures.StateNodeFigure;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;
import com.xross.tools.xstate.editor.policies.StateNodeComponentEditPolicy;

public class StateNodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {
    private DirectEditManager manager;
	protected IFigure createFigure() {
        return new StateNodeFigure();
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
//                manager = new StateNodeDirectEditManager(this, ((DecisionTreeDiagram)getParent().getModel()).getFactors(), TextCellEditor.class, new StateNodeCellEditorLocator(figure));
//            }
//            manager.show();
//		}
	}
	
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new StateNodeDirectEditPolicy(((DecisionTreeDiagram)getParent().getModel()).getFactors()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateNodeComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new DecisionTreeGraphicNodeEditPolicy());
	}
	
    protected List<StateTransition> getModelSourceConnections() {
    	return ((StateNode)getModel()).getOutputs();
    }

    protected List<StateTransition> getModelTargetConnections() {
    	ArrayList<StateTransition> inputs = new ArrayList<StateTransition>();
    	if(((StateNode)getModel()).getInput() != null)
    		inputs.add(((StateNode)getModel()).getInput());
    	return inputs;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(StateNode.PROP_LOCATION))
            refreshVisuals();
        else if (evt.getPropertyName().equals(StateNode.PROP_FACTOR_ID))
            refreshVisuals();
        else if (evt.getPropertyName().equals(StateNode.PROP_DECISION_ID))
            refreshVisuals();
        else if (evt.getPropertyName().equals(StateNode.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(StateNode.PROP_OUTPUTS))
            refreshSourceConnections();

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
    	StateNodeFigure figure = (StateNodeFigure)getFigure();
//
//		Point loc = node.getLocation();
//		Dimension size = node.getSize();
//        Rectangle rectangle = new Rectangle(loc, size);
//        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
//    	
//        String factor;
//    	if(node.getFactorId() == -1)
//    		factor = "Not specified";
//    	else
//    		factor = ((DecisionTreeDiagram)getParent().getModel()).getFactors().get(node.getFactorId()).getFactorName();
//    	figure.setName(factor);
//    	
//        String decision;
//    	if(node.getDecisionId() == -1)
//    		decision = "No decision";
//    	else
//    		decision = ((DecisionTreeDiagram)getParent().getModel()).getDecisions().get(node.getDecisionId());
//
//    	figure.setDecision(decision);
    }
}
