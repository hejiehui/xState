package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Dimension;

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

    protected Dimension getSize() {
        return getFigure().getPreferredSize();
    }
    
    protected void updateFigure(StateNode node) {
    }
}
