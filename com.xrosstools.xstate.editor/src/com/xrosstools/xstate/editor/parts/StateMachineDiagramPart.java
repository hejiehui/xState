package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xstate.editor.figures.StateDiagramFigure;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.policies.StateMachineDiagramLayoutPolicy;

public class StateMachineDiagramPart extends AbstractGraphicalEditPart implements PropertyChangeListener{
	private IFigure panel;
	protected List getModelChildren() {
		return ((StateMachineDiagram)getModel()).getMachines();
	}
	
	protected IFigure createFigure() {
	    StateDiagramFigure figure = new StateDiagramFigure();
	    panel = figure.getContentPane();
	    
		Point loc = new Point(0, 0);
		Dimension size = new Dimension(-1, -1);
        Rectangle rectangle = new Rectangle(loc, size);
        this.setLayoutConstraint(this, panel, rectangle);
        
        return figure;
	}

	public IFigure getContentPane(){
		return panel;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
	
	public void activate() {
		super.activate();
		((StateMachineDiagram)getModel()).getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((StateMachineDiagram)getModel()).getListeners().removePropertyChangeListener(this);
	}

	protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new StateMachineDiagramLayoutPolicy());
	}
}
