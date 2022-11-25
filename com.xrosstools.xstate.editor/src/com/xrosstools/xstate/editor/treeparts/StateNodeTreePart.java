package com.xrosstools.xstate.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateNodeTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
	private StateNode node;
	public StateNodeTreePart(Object model) {
        super(model);
        this.node = (StateNode)model;
    }
	
    @Override
    public List<StateTransition> getModelChildren() {
        return node.getOutputs();
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
        refreshChildren();
	}
}
