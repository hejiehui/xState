package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.xrosstools.xstate.editor.model.RouteStyle;

public class CommonStyleRouter extends AbstractRouter {
    private RouteStyle style;

    public CommonStyleRouter(RouteStyle style) {
        this.style = style;
    }
    
    public void setStyle(RouteStyle style) {
        this.style = style;
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
        Rectangle figure = conn.getTargetAnchor().getOwner().getBounds();
        PointList pl = conn.getPoints();
        Point start;
        Point end;
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
