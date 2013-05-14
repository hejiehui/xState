package com.xross.tools.xstate.editor.treeparts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramTreePart extends AbstractTreeEditPart {
    public StateMachineDiagramTreePart(Object model) {
        super(model);
     }

    protected List<StateMachine> getModelChildren() {
    	return ((StateMachineDiagram)getModel()).getMachines();
    }
}
