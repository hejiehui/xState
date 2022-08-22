package com.xrosstools.xstate.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.model.StateMachine;

import java.util.ArrayList;
import java.util.List;

public class StateMachineTreePart extends AbstractTreeEditPart {
	private StateMachine machine;
    public StateMachineTreePart(Object model) {
        super(model);
        this.machine = (StateMachine)model;
     }

    public List<Object> getModelChildren() {
    	List<Object> children = new ArrayList<>();
    	children.addAll(machine.getEvents());
    	children.addAll(machine.getNodes());
    	return children;
    }
    
    public String getText() {
    	StateMachine stateMachine = (StateMachine)getModel();
    	return stateMachine.getName();
    }
}
