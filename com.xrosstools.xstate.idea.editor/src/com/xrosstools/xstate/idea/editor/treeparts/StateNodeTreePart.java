package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

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
}
