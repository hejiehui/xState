package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

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
}
