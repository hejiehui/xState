package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.routers.ConnectionRouter;
import com.xrosstools.idea.gef.routers.PointList;
import com.xrosstools.xstate.idea.editor.model.RouteStyle;

import java.awt.*;

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

        if (style == RouteStyle.direct) {
            start = figure.getLeft();
            end = figure.getBottom();
            pl.addPoint(start);
            pl.addPoint(new Point(start.x - 50, start.y));
            pl.addPoint(new Point(start.x - 50, start.y + 50));
            pl.addPoint(new Point(end.x, end.y + 25));
            pl.addPoint(end);
            return;
        }

        if (style == RouteStyle.heightFirst) {
            start = figure.getTop();
            end = figure.getRight();
            pl.addPoint(start);
            pl.addPoint(new Point(start.x, start.y - 50));
            pl.addPoint(new Point(end.x + 50, start.y - 50));
            pl.addPoint(new Point(end.x + 50, end.y));
            pl.addPoint(end);
        } else {
            start = figure.getRight();
            end = figure.getBottom();
            pl.addPoint(start);
            pl.addPoint(new Point(start.x + 50, start.y));
            pl.addPoint(new Point(start.x + 50, start.y + 50));
            pl.addPoint(new Point(end.x, end.y + 25));
            pl.addPoint(end);
        }
    }
}
