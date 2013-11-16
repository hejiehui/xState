package com.xross.tools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xross.tools.xstate.editor.figures.StateMachineFigure;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.policies.StateMachineLayoutPolicy;

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
		IFigure figure  = new StateMachineFigure();
        return figure;
	}
	
	public IFigure getContentPane(){
		return ((StateMachineFigure)getFigure()).getFigure();
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
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new StateMachineLayoutPolicy());
    }
    
	protected void addChildVisual(EditPart childEditPart, int index) {
		StateMachineFigure figure = (StateMachineFigure)getFigure();
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		figure.getFigure().add(child);
	}
	
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		child.getParent().remove(child);
	}
	
    protected void refreshVisuals() {
    	StateMachine node = (StateMachine) getModel();
    	StateMachineFigure figure = (StateMachineFigure)getFigure();
    	
       	figure.setName(node.getName(), node.getDescription());
    }
}
