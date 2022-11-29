package com.xrosstools.xstate.editor.parts;

import java.util.List;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.xrosstools.xstate.editor.model.RouteStyle;
import com.xrosstools.xstate.editor.model.StateTransition;

public class CommonStyleRouter extends AbstractRouter {
    private StateTransition nodeConn;

    public CommonStyleRouter(StateTransition nodeConn) {
        this.nodeConn = nodeConn;
    }
    
    @Override
    public void route(Connection conn) {
        if (isConnectToSameNode(conn)) {
            routeForSameNode(conn);
        }else {
            routeDifferentNode(conn);
        }
    }
    
    private boolean isConnectToSameNode(Connection conn) {
        return conn.getTargetAnchor().getOwner() == conn.getSourceAnchor().getOwner();
    }

    public void routeDifferentNode(Connection conn) {
        PointList pl = conn.getPoints();
        pl.removeAllPoints();
        Point start = getStartPoint(conn);
        conn.translateToRelative(start);
        Point end = getEndPoint(conn);
        conn.translateToRelative(end);

        RouteStyle style = nodeConn.getStyle();
        if (style == RouteStyle.direct) {
            pl.addPoint(start);
            pl.addPoint(end);
            return;
        }

        Point middle;
        pl.addPoint(start);
        if (style == RouteStyle.heightFirst) {
            middle = new Point(start.x, end.y);
            pl.addPoint(middle);
            if(middle.x == end.x)
            	return;
        } else {
            middle = new Point(end.x, start.y);
            pl.addPoint(middle);
            if(middle.y == end.y)
            	return;
        }
        
        pl.addPoint(end);
    }
    
    private void routeForSameNode(Connection conn) {
        RouteStyle style = nodeConn.getStyle();
        Rectangle figure = conn.getTargetAnchor().getOwner().getBounds();
        PointList pl = conn.getPoints();
        Point start;
        Point end;
        pl.removeAllPoints();


        int gap = indexOf(conn) * 50;

        if (style == RouteStyle.direct) {
            start = figure.getLeft();
            end = figure.getBottom();
            pl.addPoint(start);
            pl.addPoint(new Point(start.x - gap, start.y));
            pl.addPoint(new Point(start.x - gap, start.y + gap));
            pl.addPoint(new Point(end.x, start.y + gap));
            pl.addPoint(end);
            return;
        }

        if (style == RouteStyle.heightFirst) {
            start = figure.getTop();
            end = figure.getRight();
            pl.addPoint(start);
            pl.addPoint(new Point(start.x, end.y - gap));
            pl.addPoint(new Point(end.x + gap, end.y - gap));
            pl.addPoint(new Point(end.x + gap, end.y));
            pl.addPoint(end);
        } else {
            start = figure.getRight();
            end = figure.getBottom();
            pl.addPoint(start);
            pl.addPoint(new Point(start.x + gap, start.y));
            pl.addPoint(new Point(start.x + gap, start.y + gap));
            pl.addPoint(new Point(end.x, start.y + gap));
            pl.addPoint(end);
        }
    }
    

    private int indexOf(Connection conn) {
        List<StateTransition> outputs =  nodeConn.getSource().getOutputs();
        StateTransition curConn = nodeConn;

        int i = 0;
        for (StateTransition  output: outputs) {
            if (output.getStyle() == curConn.getStyle() && output.getSource() == output.getTarget())
                i++;

            if (output == curConn)
                break;
        }
        return i;
    }
}
