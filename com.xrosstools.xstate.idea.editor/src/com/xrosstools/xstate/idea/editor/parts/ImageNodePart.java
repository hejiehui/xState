package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.IconFigure;
import com.xrosstools.xstate.idea.editor.model.StateNode;

import javax.swing.*;
import java.awt.*;

public class ImageNodePart extends AbstractNodePart {
    private Icon image;
    
	public ImageNodePart(Icon image) {
        this.image = image;
    }

    @Override
    protected Figure createFigure() {
	    return new IconFigure(image);
    }

    @Override
    protected Dimension getSize() {
        return getFigure().getPreferredSize();
    }
    
    @Override
    protected void updateFigure(StateNode node) {
    }
}
