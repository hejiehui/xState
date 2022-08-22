package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.figures.StateMachineFigure;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineConstants;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.policies.StateMachineLayoutPolicy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.List;

public class StateMachinePart extends AbstractGraphicalEditPart implements StateMachineConstants {
    public List<StateNode> getModelChildren() {
        return ((StateMachine)getModel()).getNodes();
    }

	protected Figure createFigure() {
        return new StateMachineFigure(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Figure getContentPane(){
		return ((StateMachineFigure)getFigure()).getFigure();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
	
	public void activate() {
		super.activate();
		for(StateNode node: ((StateMachine)getModel()).getNodes())
		    node.getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
        for(StateNode node: ((StateMachine)getModel()).getNodes())
            node.getListeners().removePropertyChangeListener(this);
	}

    protected EditPolicy createEditPolicy() {
    	return new StateMachineLayoutPolicy();
    }

	public void addChildVisual(EditPart childEditPart, int index) {
		StateMachineFigure figure = (StateMachineFigure)getFigure();
		Figure child = ((AbstractGraphicalEditPart) childEditPart).getFigure();
		figure.getFigure().add(child);
		figure.setPanelSize(checkConstraint());
		
		((StateNode)childEditPart.getModel()).getListeners().addPropertyChangeListener(this);
	}
	
	protected void removeChildVisual(EditPart childEditPart) {
		Figure child = ((AbstractGraphicalEditPart) childEditPart).getFigure();
		child.getParent().remove(child);
	}
	
    protected void refreshVisuals() {
    	StateMachine node = (StateMachine) getModel();
    	StateMachineFigure figure = (StateMachineFigure)getFigure();
    	
       	figure.setName(node.getName(), node.getDescription());
        figure.setPanelSize(checkConstraint());
    }

    private Dimension checkConstraint() {
        int x = 0;
        int y = 0;
        for(Object child: getChildren()) {
            AbstractNodePart part = (AbstractNodePart)child;
            Point loc = part.getStateNode().getLocation();
            Dimension size = part.getSize();
            x = Math.max(x, loc.x + size.width);
            y = Math.max(y, loc.y + size.height);
        }
        
        return new Dimension(
                Math.max(DEFAULT_WIDTH, x + INCREMENTAL), 
                Math.max(DEFAULT_HEIGHT, y + INCREMENTAL));
    }
}
