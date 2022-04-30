package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xstate.editor.figures.StateMachineFigure;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineConstants;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.policies.StateMachineComponentEditPolicy;
import com.xrosstools.xstate.editor.policies.StateMachineLayoutPolicy;

public class StateMachinePart extends AbstractGraphicalEditPart implements PropertyChangeListener, StateMachineConstants {
    protected List<StateNode> getModelChildren() {
        return ((StateMachine)getModel()).getNodes();
    }

	protected IFigure createFigure() {
	    StateMachine node = (StateMachine) getModel();

        return new StateMachineFigure(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public IFigure getContentPane(){
		return ((StateMachineFigure)getFigure()).getFigure();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
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
    	installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateMachineComponentEditPolicy());
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
       	
       	Dimension size = checkConstraint();
        figure.getFigure().setPreferredSize(size);
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
