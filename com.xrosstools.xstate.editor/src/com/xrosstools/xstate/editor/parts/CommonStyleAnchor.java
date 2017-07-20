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
        
        if(isSource){
            if(style == RouteStyle.heightFirst) {
                if(loc.x < r.x)
                    x = r.x;
                else if(loc.x > r.x + r.width)
                    x = r.x + r.width;
                else
                    x = loc.x;
                
                if(loc.y > r.y && loc.y < r.y + r.height/2)
                    y = loc.y;
                else
                    y = r.y + r.height/2;
            }else{
                if(loc.y < r.y)
                    y = r.y;
                else if(loc.y > r.y + r.height)
                    y = r.y + r.height;
                else
                    y = loc.y;
                
                if(loc.x > r.x && loc.x < r.x + r.width/2)
                    x = loc.x;
                else
                    x = r.x + r.width/2;
            }
        }else{
            if(style == RouteStyle.heightFirst) {
                if(loc.x < r.x)
                    x = r.x;
                else if(loc.x > r.x + r.width)
                    x = r.x + r.width;
                else
                    x = loc.x;
                
                if(loc.y > r.y && loc.y < r.y + r.height/2)
                    y = loc.y;
                else
                    y = r.y + r.height/2;
            }else{
                if(loc.y < r.y)
                    y = r.y;
                else if(loc.y > r.y + r.height)
                    y = r.y + r.height;
                else
                    y = loc.y;
                
                if(loc.x > r.x && loc.x < r.x + r.width/2)
                    x = loc.x;
                else
                    x = r.x + r.width/2;
            }
        }
            
        Point p = new PrecisionPoint(x,y);
        getOwner().translateToAbsolute(p);
        return p;
    }
}