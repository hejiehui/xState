package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.ContextMenuBuilder;
import com.xrosstools.xstate.editor.actions.StateMachineChangeEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineChangeExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineOpenExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineRemoveEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineRemoveExitAction;
import com.xrosstools.xstate.editor.figures.StateNodeFigure;
import com.xrosstools.xstate.editor.model.StateMachineConstants;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.policies.StateMachineGraphicNodeEditPolicy;
import com.xrosstools.xstate.editor.policies.StateNodeComponentEditPolicy;

public class StateNodePart extends AbstractGraphicalEditPart implements StateMachineConstants, PropertyChangeListener, NodeEditPart, ContextMenuBuilder {
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
    	StateNodeFigure figure = (StateNodeFigure)getFigure();
       	figure.setName(node.getId());

		Point loc = node.getLocation();
		Dimension size = figure.getGoodSize();
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    	
       	figure.setExistAction(node.getExitAction());
       	figure.setEntryAction(node.getEntryAction());
    }
    
	@Override
	public void buildContextMenu(IMenuManager menu, IWorkbenchPart editor, ImplementationFinder finder) {
    	menu.add(new Separator());
    	StateNode node = getStateNode();
    	if(isEmpty(node.getEntryAction()))
    		menu.add(new StateMachineCreateEntryAction(editor, node, finder));
    	else{
    		menu.add(new StateMachineChangeEntryAction(editor, node, finder));
    		menu.add(new StateMachineRemoveEntryAction(editor, node));
    		menu.add(new StateMachineOpenExitAction(editor, node, finder));
    	}

    	menu.add(new Separator());
    	if(isEmpty(node.getExitAction()))
    		menu.add(new StateMachineCreateExitAction(editor, node, finder));
    	else{
    		menu.add(new StateMachineChangeExitAction(editor, node, finder));
    		menu.add(new StateMachineRemoveExitAction(editor, node));
    		menu.add(new StateMachineOpenExitAction(editor, node, finder));
    	}
	}
	
	private boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
}
