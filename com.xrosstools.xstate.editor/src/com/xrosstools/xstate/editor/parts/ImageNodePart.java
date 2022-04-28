package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xstate.editor.Activator;
import com.xrosstools.xstate.editor.model.StateNode;

public class ImageNodePart extends AbstractNodePart {
    private String imageId;
    
	public ImageNodePart(String imageId) {
        this.imageId = imageId;
    }

    protected IFigure createFigure() {
	    return new ImageFigure(Activator.getDefault().getImageRegistry().get(imageId));
    }

    protected void refreshVisuals() {
    	StateNode node = (StateNode) getModel();
    	ImageFigure figure = (ImageFigure)getFigure();

		Point loc = node.getLocation();
		Dimension size = figure.getPreferredSize();//node.getSize();
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    }
}
