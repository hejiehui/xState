package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.model.Event;

import java.beans.PropertyChangeListener;

public class EventTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
	private Event event;
	public EventTreePart(Object model) {
        super(model);
        this.event = (Event)model;
    }
	
    public String getText() {
        return event.getId();
    }
}
