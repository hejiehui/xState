package com.xross.tools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramPart extends AbstractGraphicalEditPart implements PropertyChangeListener{
	private Figure panel;
	protected List getModelChildren() {
		return ((StateMachineDiagram)getModel()).getMachines();
	}
	
	protected IFigure createFigure() {
        Figure figure = new FreeformLayer();
        figure.setLayoutManager(new FreeformLayout());
        
        panel = new Figure();
    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setHorizontal(false);
    	layout.setSpacing(100);
    	layout.setStretchMinorAxis(false);
    	layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
    	panel.setLayoutManager(layout);
    	panel.setBorder(new MarginBorder(50));

    	figure.add(panel);
		Point loc = new Point(100, 100);
		Dimension size = new Dimension(-1, -1);
        Rectangle rectangle = new Rectangle(loc, size);
        this.setLayoutConstraint(this, panel, rectangle);
        
        return figure;
	}

	public IFigure getContentPane(){
		return panel;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
//		String prop = evt.getPropertyName();
		refreshChildren();
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
//        installEditPolicy(EditPolicy.LAYOUT_ROLE, new StateMachineDiagramLayoutPolicy());
	}
}
