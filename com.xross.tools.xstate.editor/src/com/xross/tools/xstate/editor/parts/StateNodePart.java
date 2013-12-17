package com.xross.tools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.ui.JavaUIMessages;
import org.eclipse.jdt.internal.ui.dialogs.OpenTypeSelectionDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.xross.tools.xstate.editor.commands.AssignClassCommand;
import com.xross.tools.xstate.editor.figures.StateNodeFigure;
import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;
import com.xross.tools.xstate.editor.policies.StateMachineGraphicNodeEditPolicy;
import com.xross.tools.xstate.editor.policies.StateNodeComponentEditPolicy;

public class StateNodePart extends AbstractGraphicalEditPart implements StateMachineConstants, PropertyChangeListener, NodeEditPart, MouseListener {
	protected IFigure createFigure() {
		StateNodeFigure figure = new StateNodeFigure();
		figure.addMouseListener(this);
        return figure;
    }
	
	private IType getSourceType(boolean isEntryAction){
		if(isEntryAction)
			return getDiagramPart().getSourceType(getStateNode().getEntryAction());
		else
			return getDiagramPart().getSourceType(getStateNode().getExitAction());
	}

	private void setSourceType(IType type){
		getDiagramPart().setSourceType(type);
	}
	
	private StateMachineDiagramPart getDiagramPart(){
		return (StateMachineDiagramPart)getRoot().getContents();
	}

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
	}
	
    private IType openDialog(boolean isEntryAction){
    	Shell parent = Display.getCurrent().getActiveShell();
    	OpenTypeSelectionDialog dialog= new OpenTypeSelectionDialog(parent, true, PlatformUI.getWorkbench().getProgressService(), null, IJavaSearchConstants.TYPE);
		dialog.setTitle(JavaUIMessages.OpenTypeAction_dialogTitle);
		dialog.setMessage(JavaUIMessages.OpenTypeAction_dialogMessage);
		String initValue = isEntryAction ? getStateNode().getEntryAction() : getStateNode().getExitAction(); 
		dialog.setInitialPattern(initValue, 2);
		
		int result= dialog.open();
		// if cancel clicked, will not change existing type;
		if (result != IDialogConstants.OK_ID)
			return getSourceType(isEntryAction);

		Object[] types= dialog.getResult();
		if (types == null || types.length != 1)
			return null;
		return (IType)types[0];
    }
	
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateNodeComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new StateMachineGraphicNodeEditPolicy());
	}
	
	public StateNode getStateNode() {
		return (StateNode)getModel();
	}
    protected List<StateTransition> getModelSourceConnections() {
    	return getStateNode().getOutputs();
    }

    protected List<StateTransition> getModelTargetConnections() {
    	return getStateNode().getInputs();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(StateNode.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(StateNode.PROP_OUTPUTS))
            refreshSourceConnections();
        else
            refreshVisuals();
    }
    
    public void activate() {
    	super.activate();
    	getStateNode().getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	getStateNode().getListeners().removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
    	StateNode node = getStateNode();
    	StateNodeFigure figure = (StateNodeFigure)getFigure();
       	figure.setName(node.getId());

		Point loc = node.getLocation();
		Dimension size = figure.getGoodSize();
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    	
       	figure.setExistAction(node.getExitAction());
       	figure.setEntryAction(node.getEntryAction());
    }
    
	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}
	
	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		StateNodeFigure figure = (StateNodeFigure)getFigure();
		boolean isEntryAction = figure.isEntryAction((IFigure)me.getSource());
		IType newType = openDialog(isEntryAction);
		if(newType == null)
			return;
		setSourceType(newType);
		if(isEntryAction)
			getStateNode().setEntryAction(newType.getFullyQualifiedName());
		else
			getStateNode().setExitAction(newType.getFullyQualifiedName());

		
//		if(newType.getFullyQualifiedName().equalsIgnoreCase(getNode().getImplClassName()))
//			return;
		getViewer().getEditDomain().getCommandStack().execute(new AssignClassCommand(getStateNode(), isEntryAction, newType.getFullyQualifiedName()));
    }    
}
