package com.xrosstools.xstate.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateNode;

public class EventTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
	private Event event;
	public EventTreePart(Object model) {
        super(model);
        this.event = (Event)model;
    }
	
    protected String getText() {
        return event.getId();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(Activator.STATE_NODE);
    }
    
	public void activate() {
		super.activate();
		event.getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		event.getListeners().removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		refreshVisuals();
	}
}
