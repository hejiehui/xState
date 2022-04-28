package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xstate.editor.figures.StateNodeFigure;
import com.xrosstools.xstate.editor.model.StateNode;

public class StateNodePart extends AbstractNodePart {
    protected IFigure createFigure() {
        return new StateNodeFigure();
    }

    protected void refreshVisuals() {
        StateNode node = getStateNode();
        StateNodeFigure figure = (StateNodeFigure) getFigure();
        figure.setName(node.getId());

        Point loc = node.getLocation();
        Dimension size = figure.getGoodSize();
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);

        figure.setExistAction(node.getExitAction());
        figure.setEntryAction(node.getEntryAction());
    }
}
