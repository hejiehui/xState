package com.xrosstools.xstate.editor.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;

import com.xrosstools.xstate.editor.figures.StateNodeFigure;
import com.xrosstools.xstate.editor.model.StateNode;

public class StateNodePart extends AbstractNodePart {
    protected IFigure createFigure() {
        return new StateNodeFigure();
    }

    protected Dimension getSize() {
        StateNodeFigure figure = (StateNodeFigure)getFigure();
        return figure.getGoodSize();
    }

    protected void updateFigure(StateNode node) {
        StateNodeFigure figure = (StateNodeFigure)getFigure();
        figure.setName(node.getId());

        figure.setExistAction(node.getExitAction());
        figure.setEntryAction(node.getEntryAction());
    }
}
