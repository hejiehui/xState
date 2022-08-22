package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.IconFigure;
import com.xrosstools.xstate.idea.editor.model.StateNode;

import java.awt.*;

public class ImageNodePart extends AbstractNodePart {
    private String imageId;
    
	public ImageNodePart(String imageId) {
        this.imageId = imageId;
    }

    protected Figure createFigure() {
	    return new IconFigure(imageId);
    }

    protected Dimension getSize() {
        return getFigure().getPreferredSize();
    }
    
    protected void updateFigure(StateNode node) {
    }
}
