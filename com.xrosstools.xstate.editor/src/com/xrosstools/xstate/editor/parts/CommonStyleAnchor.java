package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;

import com.xrosstools.xstate.editor.model.RouteStyle;

public class CommonStyleAnchor extends ChopboxAnchor {
    private StateTransitionPart connPart;
    private boolean isSource;
    public CommonStyleAnchor(IFigure owner, ConnectionEditPart connection, boolean isSource) {
        super(owner);
        connPart = (StateTransitionPart)connection;
        this.isSource = isSource;
    }
    
    public CommonStyleAnchor(IFigure owner, Request request, boolean isSource) {
        super(owner);
        this.isSource = isSource;
    }

    public Point getLocation(Point loc)
    {
        RouteStyle style = connPart.style;
        if(style == RouteStyle.direct)
            return super.getLocation(loc);
        
        Rectangle r = getOwner().getBounds();
        int x;
        int y;
        
        Point point = r.getCenter();
        if(isSource){
            if(style == RouteStyle.heightFirst) {
                if(loc.x < r.x)
                    point = r.getLeft();
                else if(loc.x > r.x + r.width)
                	point = r.getRight();
            }else{
            	if(loc.y < r.y)
            		point = r.getTop();
            	else if(loc.y > r.y + r.height)
            		point = r.getBottom();
            }
        }else{
            if(style == RouteStyle.heightFirst) {
            	if(loc.y > r.y)
            		point = r.getTop();
            	else if(loc.y > r.y + r.height)
            		point = r.getBottom();
            }else{
                if(loc.x < r.x)
                	point = r.getRight();
                else if(loc.x > r.x + r.width)
                	point = r.getLeft();
            }
        }
            
        Point p = new PrecisionPoint(point);
        getOwner().translateToAbsolute(p);
        return p;
    }
}