package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

import com.xrosstools.xstate.editor.model.RouteStyle;

public class CommonStyleAnchor extends ChopboxAnchor {
    private RouteStyle style;
    private boolean isSource;
    public CommonStyleAnchor(IFigure owner, RouteStyle style, boolean isSource) {
        super(owner);
        this.style = style;
        this.isSource = isSource;
    }
    public Point getLocation(Point loc) {
        return style == RouteStyle.direct ? super.getLocation(loc) : isSource ? getSourceLocation(loc) : getTargetLocation(loc);
    }

    private Point getSourceLocation(Point ref)
    {
        Rectangle r = getOwner().getBounds();
        
        Point pos;
        getOwner().translateToRelative(r);
        
        if(style == RouteStyle.heightFirst) {
            if(ref.y < r.y())
                pos = r.getTop();
            else if(ref.y > r.y + r.height)
                pos = r.getBottom();
            else if(ref.x < r.x + r.width)
                pos = r.getLeft();
            else
                pos = r.getRight();
        }else{
            if(ref.x < r.x)
                pos = r.getLeft();
            else  if(ref.x > r.x + r.width)
                pos = r.getRight();
            else if(ref.y < r.y)
                pos = r.getTop();
            else
                pos = r.getBottom();
        }
            
        Point p = new PrecisionPoint(pos);
        getOwner().translateToAbsolute(p);
        return p;
    }

    private Point getTargetLocation(Point ref)
    {
        Rectangle r = getOwner().getBounds();
        
        Point pos;
        getOwner().translateToRelative(r);
        
        if(style == RouteStyle.heightFirst) {
            if(ref.x < r.x)
                pos = r.getLeft();
            else if(ref.x > r.x + r.width)
                pos = r.getRight();
            else if(ref.y < r.y)
                pos = new Point(ref.x, r.y);
            else
                pos = new Point(ref.x, r.y + r.height);
        }else{
            if(ref.y < r.y)
                pos = r.getTop();
            else if(ref.y > r.y + r.height)
                pos = r.getBottom();
            else if(ref.x < r.x)
                pos = r.getLeft();
            else
                pos = r.getRight();
        }
            
        Point p = new PrecisionPoint(pos);
        getOwner().translateToAbsolute(p);
        return p;
    }
}
