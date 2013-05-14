package com.xross.tools.xstate.editor.treeparts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xross.tools.xstate.editor.Activator;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramTreePart extends AbstractTreeEditPart {
    public StateMachineDiagramTreePart(Object model) {
        super(model);
     }

    protected List<StateMachine> getModelChildren() {
    	return ((StateMachineDiagram)getModel()).getMachines();
    }
    
    protected String getText() {
    	StateMachineDiagram diagram = (StateMachineDiagram)getModel();
    	return diagram.getName();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(Activator.STATE_MACHINE_DIAGRAM);
    }
}
