package com.xrosstools.xstate.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
    private StateMachineDiagram diagram;
    public StateMachineDiagramTreePart(Object model) {
        super(model);
        this.diagram = (StateMachineDiagram)model;
     }

    protected List<StateMachine> getModelChildren() {
    	return ((StateMachineDiagram)getModel()).getMachines();
    }
    
    protected String getText() {
    	StateMachineDiagram diagram = (StateMachineDiagram)getModel();
    	return diagram.getName() == null ? "" : diagram.getName();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(Activator.STATE_MACHINE_DIAGRAM);
    }

    public void activate() {
        super.activate();
        diagram.getListeners().addPropertyChangeListener(this);
    }

    public void deactivate() {
        super.deactivate();
        diagram.getListeners().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
        refreshVisuals();
        refreshChildren();
    }
}
