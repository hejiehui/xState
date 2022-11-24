package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.ChopboxAnchor;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.xstate.idea.editor.model.RouteStyle;
import gherkin.lexer.Fi;

import java.awt.*;

public class CommonStyleAnchor extends ChopboxAnchor {
    private RouteStyle style;
    private boolean isSource;
    public CommonStyleAnchor(Figure owner, RouteStyle style, boolean isSource) {
        super(owner);
        this.style = style;
        this.isSource = isSource;
    }

    @Override
    public Point getLocation(Point loc) {
    	return style == RouteStyle.direct ? 
    			super.getLocation(loc) :
    				isSource ? 
    						getSourceLocation(loc) : 
    							getTargetLocation(loc);
    }
    private Point getSourceLocationV2(Point ref) {
        Rectangle r = new Rectangle(getOwner().getBounds());
        
        Point pos;
//        getOwner().translateToAbsolute(r);
        
        int x = ref.x < r.x ? r.x : r.x + r.width;
        int y = r.y + r.height/2;
        
        return new Point(x, y);
    }
    
    private Point getSourceLocation(Point ref) {
        Rectangle r = new Rectangle(getOwner().getBounds());
        Figure f = getOwner();
        
        Point pos;

        if(style == RouteStyle.heightFirst) {
            if(ref.y < r.y)
                pos = f.getTop();
            else if(ref.y > r.y + r.height)
                pos = f.getBottom();
            else if(ref.x < r.x) {
            	if(ref.y >= r.y && ref.y <= r.y + r.height)
            		pos = new Point(r.x, ref.y);
            	else
            		pos = f.getLeft();
            }else {
            	if(ref.y >= r.y && ref.y <= r.y + r.height)
            		pos = new Point(r.x + r.width, ref.y);
            	else
            		pos = f.getRight();
            }
        }else{
            if(ref.x < r.x)
                pos = f.getLeft();
            else  if(ref.x > r.x + r.width)
                pos = f.getRight();
            else if(ref.y < r.y) {
            	if(ref.x >= r.x && ref.x <= r.x + r.width)
            		pos = new Point(ref.x, r.y);
            	else
            		pos = f.getTop();
        	}else {
            	if(ref.x >= r.x && ref.x <= r.x + r.width)
            		pos = new Point(ref.x, r.y + r.height);
            	else
            		pos = f.getBottom();
        	}
        }
            
        Point p = new Point(pos);
        return p;
    }

    private Point getTargetLocation(Point ref)
    {
        Rectangle r = new Rectangle(getOwner().getBounds());
        Figure f = getOwner();
        
        Point pos;

        if(style == RouteStyle.heightFirst) {
            if(ref.x < r.x)
                pos = f.getLeft();
            else if(ref.x > r.x + r.width)
                pos = f.getRight();
            else if(ref.y < r.y)
                pos = new Point(ref.x, r.y);
            else
                pos = new Point(ref.x, r.y + r.height);
        }else{
            if(ref.y < r.y)
                pos = f.getTop();
            else if(ref.y > r.y + r.height)
                pos = f.getBottom();
            else if(ref.x < r.x)
            	pos = new Point(r.x, ref.y);
            else
            	pos = new Point(r.x + r.width, ref.y);
        }
            
        Point p = new Point(pos);
        return p;
    }
}
