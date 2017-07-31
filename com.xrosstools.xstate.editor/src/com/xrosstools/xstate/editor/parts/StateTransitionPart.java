package com.xrosstools.xstate.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.ContextMenuBuilder;
import com.xrosstools.xstate.editor.actions.StateMachineChangeTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineOpenTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineRemoveTransitionAction;
import com.xrosstools.xstate.editor.model.RouteStyle;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.policies.StateTransitionComponentEditPolicy;

public class StateTransitionPart extends AbstractConnectionEditPart implements PropertyChangeListener, ContextMenuBuilder {
	private Label label;
	private PolylineConnection conn;
	private CommonStyleRouter router;
	RouteStyle style;
    protected IFigure createFigure() {
        StateTransition nodeConn = (StateTransition)getModel();
        this.style = nodeConn.getStyle();
        conn = new PolylineConnection();
        conn.setTargetDecoration(new PolygonDecoration());
        conn.setConnectionRouter(router = new CommonStyleRouter(nodeConn.getStyle()));
        conn.setForegroundColor(ColorConstants.black);
        
        label = new Label();
        label.setText(nodeConn.getDisplayLabel());
        label.setOpaque(true);
        conn.add(label, new MidpointLocator(conn, 0));
        return conn;
    }

    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new StateTransitionComponentEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
    }

    public void setSelected(int value) {
        super.setSelected(value);
        if (value != EditPart.SELECTED_NONE)
            ((PolylineConnection) getFigure()).setLineWidth(2);
        else
            ((PolylineConnection) getFigure()).setLineWidth(1);
    }
    
    public void activate() {
    	super.activate();
    	((StateTransition) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	((StateTransition) getModel()).getListeners().removePropertyChangeListener(this);
    }
    
    public void propertyChange(PropertyChangeEvent event){
    	StateTransition nodeConn = (StateTransition)getModel();
    	label.setText(nodeConn.getDisplayLabel());
    	this.style = nodeConn.getStyle();
    	router.style = nodeConn.getStyle();
    	refresh();
    }
    
	@Override
	public void buildContextMenu(IMenuManager menu, IWorkbenchPart editor, ImplementationFinder finder) {
    	menu.add(new Separator());
    	StateTransition transition = (StateTransition)getModel();

    	if(isEmpty(transition.getTransitAction()))
    		menu.add(new StateMachineCreateTransitionAction(editor, transition, finder));
    	else{
    		menu.add(new StateMachineChangeTransitionAction(editor, transition, finder));
    		menu.add(new StateMachineRemoveTransitionAction(editor, transition));
    		menu.add(new StateMachineOpenTransitionAction(editor, transition, finder));
    	}
	}
	
	private boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
}
