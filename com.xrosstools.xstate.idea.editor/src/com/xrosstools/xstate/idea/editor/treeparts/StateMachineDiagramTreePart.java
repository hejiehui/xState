package com.xrosstools.xstate.idea.editor.treeparts;

import java.util.List;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;

public class StateMachineDiagramTreePart extends AbstractTreeEditPart {
    public StateMachineDiagramTreePart(Object model) {
        super(model);
     }

    public List<StateMachine> getModelChildren() {
    	return ((StateMachineDiagram)getModel()).getMachines();
    }
    
    public String getText() {
    	StateMachineDiagram diagram = (StateMachineDiagram)getModel();
    	return diagram.getName();
    }
}
