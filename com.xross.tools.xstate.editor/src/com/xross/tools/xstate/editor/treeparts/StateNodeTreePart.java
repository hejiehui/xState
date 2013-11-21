package com.xross.tools.xstate.editor.treeparts;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xross.tools.xstate.editor.Activator;
import com.xross.tools.xstate.editor.model.StateNode;

public class StateNodeTreePart extends AbstractTreeEditPart {
	public StateNodeTreePart(Object model) {
        super(model);
    }
	
    protected String getText() {
    	StateNode node = (StateNode)getModel();

        return node.getId();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(Activator.STATE_NODE);
    }
}
