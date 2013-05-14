package com.xross.tools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateMachinePart  extends AbstractGraphicalEditPart implements PropertyChangeListener{
	private boolean isLayoutUpdated;
    protected List<StateNode> getModelChildren() {
    	StateMachine diagram = (StateMachine)getModel();
    	if(!isLayoutUpdated){
    		new LayoutAlgorithm().layout(diagram);
    		isLayoutUpdated = true;
    	}
//    	
//    	DecisionTreeManager manager = new DecisionTreeManager(diagram);
//    	for(DecisionTreeNode node: diagram.getNodes())
//    		if(!node.isDecisionTreeManagerSet())
//    			node.setDecisionTreeManager(manager);
        return diagram.getNodes();
    }

	protected IFigure createFigure() {
        Figure figure = new FreeformLayer();
        figure.setLayoutManager(new FreeformLayout());
        return figure;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
//		if (StateMachine.LAYOUT.equals(prop)){
//			isLayoutUpdated = false;
//		}

		refreshChildren();
	}
	
	public void activate() {
		super.activate();
		((StateMachine)getModel()).getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((StateMachine)getModel()).getListeners().removePropertyChangeListener(this);
	}

    protected void createEditPolicies() {
//        installEditPolicy(EditPolicy.LAYOUT_ROLE, new StateMachineLayoutPolicy());
    }
}
