package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.Request;

import com.xrosstools.xstate.editor.model.RouteStyle;

public class CommonStyleAnchor extends AbstractConnectionAnchor {
    private RouteStyle style;
    private boolean isSource;
    public CommonStyleAnchor(IFigure owner, RouteStyle style, boolean isSource) {
        super(owner);
        this.style = style;
        this.isSource = isSource;
    }

    public Point getLocation(Point loc)
    {
        Rectangle r = getOwner().getBounds();
        
        Point certer = r.getCenter();
        Point pos = null;
        getOwner().translateToRelative(certer);
        
        if(isSource){
            if(style == RouteStyle.heightFirst) {
                if(loc.x < r.x)
                    pos = r.getLeft();
                else if(loc.x > r.x + r.width)
                    pos = r.getRight();
                else if(loc.y > certer.y)
                    pos = r.getTop();
                else
                    pos = r.getBottom();
            }else{
                if(loc.y < r.y)
                    pos = r.getTop();
                else if(loc.y > r.y + r.height)
                    pos = r.getBottom();
                else if(loc.x < certer.x)
                    pos = r.getLeft();
                else
                    pos = r.getRight();
            }
        }else{
            if(style == RouteStyle.heightFirst) {
            	if(loc.y < r.y)
            	    pos = r.getTop();
            	else if(loc.y > r.y + r.height)
            	    pos = r.getBottom();
            	else if(loc.x < certer.x)
            	    pos = r.getLeft();
                else
                    pos = r.getRight();
            }else{
                if(loc.x < r.x)
                    pos = r.getLeft();
                else  if(loc.x > r.x + r.width)
                    pos = r.getRight();
                else if(loc.y > certer.y)
                    pos = r.getTop();
                else
                    pos = r.getBottom();
            }
        }
            
        Point p = new PrecisionPoint(pos);
        getOwner().translateToAbsolute(p);
        return p;
    }
}