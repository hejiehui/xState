package com.xross.tools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jdt.core.IType;

import com.xross.tools.xstate.editor.figures.StateDiagramFigure;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramPart extends AbstractGraphicalEditPart implements PropertyChangeListener{
	// cached for next visit; TODO should be revised if there is only one match for the name
	private Map<String, IType> s_sourceTypes = new HashMap<String, IType>();
	
	public IType getSourceType(String className){
		if(className == null || className.trim().length() == 0)
			return null;
		return s_sourceTypes.get(className.trim());
	}
	
	public void setSourceType(IType type){
		if(type ==  null)
			return;
		s_sourceTypes.put(type.getFullyQualifiedName(), type);
	}
	
	private Figure panel;
	protected List getModelChildren() {
		return ((StateMachineDiagram)getModel()).getMachines();
	}
	
	protected IFigure createFigure() {
        Figure figure = new FreeformLayer();
        figure.setLayoutManager(new FreeformLayout());
        
        panel = new Figure();
    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setVertical(true);
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

	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		StateMachine sm = (StateMachine)childEditPart.getModel();
		getContentPane().add(new StateDiagramFigure(sm.getName(), child), index);
	}

	protected void removeChildVisual(EditPart childEditPart) {
		IFigure wrappedChild = ((GraphicalEditPart) childEditPart).getFigure();
		IFigure wrapper = null;
		for(Object figure:getContentPane().getChildren()){
			StateDiagramFigure curWrapper = (StateDiagramFigure)figure;
			if(curWrapper.getFigure() != wrappedChild)
				continue;
			
			wrapper = curWrapper;
		}
		
		getContentPane().remove(wrapper);
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
//        installEditPolicy(EditPolicy.LAYOUT_ROLE, new UnitNodeContainerLayoutPolicy());
	}
}
