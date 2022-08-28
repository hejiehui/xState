package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Connection;
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
        PointList pl = conn.getPoints();
        //pl.removeAllPoints();
        Point start = pl.getFirst();
        Point end = pl.getLast();

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
