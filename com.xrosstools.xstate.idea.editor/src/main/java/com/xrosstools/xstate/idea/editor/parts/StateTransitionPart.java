package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.*;
import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.idea.gef.routers.MidpointLocator;
import com.xrosstools.xstate.idea.editor.model.RouteStyle;
import com.xrosstools.xstate.idea.editor.model.StateTransition;
import com.xrosstools.xstate.idea.editor.policies.StateTransitionComponentEditPolicy;

public class StateTransitionPart extends AbstractConnectionEditPart {
    private StateTransition nodeConn;
	private Label label;
	private CommonStyleRouter router;
    protected Figure createFigure() {
        nodeConn = (StateTransition)getModel();
        Connection conn = new Connection();
        conn.setTargetDecoration(new ArrowDecoration());
        router = new CommonStyleRouter(nodeConn.getStyle());
        conn.setRouter(router);
        conn.setForegroundColor(ColorConstants.black);
        
        label = new Label();
        label.setText(nodeConn.getDisplayLabel());
        label.setOpaque(true);
        conn.add(label, new MidpointLocator());
        return conn;
    }

    protected EditPolicy createEditPolicy() {
        return new StateTransitionComponentEditPolicy();
    }

    protected void refreshVisuals() {
        label.setText(nodeConn.getDisplayLabel());
        router.setStyle(nodeConn.getStyle());
    }

    public RouteStyle getStyle() {
        return nodeConn.getStyle();
    }
}
