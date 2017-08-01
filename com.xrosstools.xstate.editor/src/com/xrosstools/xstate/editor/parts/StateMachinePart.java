package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.ContextMenuBuilder;
import com.xrosstools.xstate.editor.actions.StateMachineCreateEventAction;
import com.xrosstools.xstate.editor.figures.StateMachineFigure;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.policies.StateMachineComponentEditPolicy;
import com.xrosstools.xstate.editor.policies.StateMachineLayoutPolicy;

public class StateMachinePart  extends AbstractGraphicalEditPart implements PropertyChangeListener, ContextMenuBuilder {
    protected List<StateNode> getModelChildren() {
        return ((StateMachine)getModel()).getNodes();
    }

	protected IFigure createFigure() {
        return new StateMachineFigure();
	}
	
	public IFigure getContentPane(){
		return ((StateMachineFigure)getFigure()).getFigure();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
	
	public void activate() {
		super.activate();
		((StateMachine)getModel()).getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((StateMachine)getModel()).getListeners().removePropertyChangeListener(this);
	}

    protected void createEditPolicies() {
    	installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateMachineComponentEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new StateMachineLayoutPolicy());
    }
    
	protected void addChildVisual(EditPart childEditPart, int index) {
		StateMachineFigure figure = (StateMachineFigure)getFigure();
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		figure.getFigure().add(child);
	}
	
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		child.getParent().remove(child);
	}
	
//    protected void refreshVisuals() {
//    	StateMachine node = (StateMachine) getModel();
//    	StateMachineFigure figure = (StateMachineFigure)getFigure();
//    	
//       	figure.setName(node.getName(), node.getDescription());
//    }

	@Override
	public void buildContextMenu(IMenuManager menu, IWorkbenchPart editor, ImplementationFinder finder) {
    	menu.add(new Separator());
    	StateMachine machine = (StateMachine)getModel();
    	menu.add(new StateMachineCreateEventAction(editor, machine));
	}
}
