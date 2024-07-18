package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.xstate.idea.editor.figures.StateNodeFigure;
import com.xrosstools.xstate.idea.editor.model.StateNode;

import java.awt.*;

public class StateNodePart extends AbstractNodePart {
    protected Figure createFigure() {
        return new StateNodeFigure();
    }

    protected Dimension getSize() {
        StateNodeFigure figure = (StateNodeFigure)getFigure();
        return figure.getGoodSize();
    }

    protected void updateFigure(StateNode node) {
        StateNodeFigure figure = (StateNodeFigure)getFigure();
        figure.setName(node.getDisplayText());

        figure.setExitAction(node.getExitAction());
        figure.setEntryAction(node.getEntryAction());
    }
}
