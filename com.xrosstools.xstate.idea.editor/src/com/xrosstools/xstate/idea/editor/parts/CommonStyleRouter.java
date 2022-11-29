package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.routers.ConnectionRouter;
import com.xrosstools.idea.gef.routers.PointList;
import com.xrosstools.xstate.idea.editor.model.RouteStyle;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import java.awt.*;
import java.util.List;

public class CommonStyleRouter implements ConnectionRouter {
    private RouteStyle style;

    public CommonStyleRouter(RouteStyle style) {
        this.style = style;
    }
    
    public void setStyle(RouteStyle style) {
        this.style = style;
    }

    @Override
    public void route(Connection conn) {
        if (conn.isConnectToSameNode()) {
            routeForSameNode(conn);
        }else {
            routeDifferentNode(conn);
        }
    }

    public void routeDifferentNode(Connection conn) {
        PointList pl = conn.getPoints();
        Point start = pl.getFirst();
        Point end = pl.getLast();
        //For Idea Gef, you need to remove all points after get start and end
        pl.removeAllPoints();

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
            if(middle.x == end.x) {
                return;
            }
        } else {
            middle = new Point(end.x, start.y);
            pl.addPoint(middle);
            if(middle.y == end.y) {
                return;
            }
        }
        
        pl.addPoint(end);
    }

    private void routeForSameNode(Connection conn) {
        Figure figure = conn.hasFeedback() ? (Figure) conn.getFeedback() : conn.getConnectionPart().getSourceFigure();
        PointList pl = conn.getPoints();
        Point start = pl.getFirst();
        Point end = pl.getLast();
        //For Idea Gef, you need to remove all points after get start and end
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
        List<StateTransition> outputs =  ((AbstractGraphicalEditPart)conn.getConnectionPart().getParent()).getModelSourceConnections();
        StateTransition curConn = (StateTransition)conn.getConnectionPart().getModel();

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
