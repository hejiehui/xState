package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.StateMachineIcons;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;

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

    @Override
    public String getText() {
        return node.getId() == null ? "" : node.getId();
    }

    @Override
    public Icon getImage() {
	    switch (node.getClass().getSimpleName()) {
            case "StateNode":  return StateMachineIcons.STATE_NODE;
            case "StartNode":  return StateMachineIcons.START_NODE;
            case "EndNode":  return StateMachineIcons.END_NODE;
            default:  return StateMachineIcons.STATE_NODE;
        }
    }
}
