package com.xross.tools.xstate.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xross.tools.xstate.editor.Activator;
import com.xross.tools.xstate.editor.model.EndNode;
import com.xross.tools.xstate.editor.model.StartNode;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateNodeTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
	private StateNode node;
	public StateNodeTreePart(Object model) {
        super(model);
        this.node = (StateNode)model;
    }
	
    protected String getText() {
        return node.getId() == null ? "" : node.getId();
    }
    
    protected Image getImage() {
    	if(node instanceof StartNode)
    		return Activator.getDefault().getImage(Activator.START_NODE);
    	
    	if(node instanceof EndNode)
    		return Activator.getDefault().getImage(Activator.END_NODE);
    	
    	return Activator.getDefault().getImage(Activator.STATE_NODE);
    }
    
	public void activate() {
		super.activate();
		node.getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((StateNode)getModel()).getListeners().removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		refreshVisuals();
	}
}
