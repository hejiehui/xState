package com.xrosstools.xstate.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xstate.idea.editor.figures.StateDiagramFigure;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.policies.StateMachineDiagramLayoutPolicy;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;

public class StateMachineDiagramPart extends AbstractGraphicalEditPart implements PropertyChangeListener{
	public List getModelChildren() {
		return ((StateMachineDiagram)getModel()).getMachines();
	}
	
	protected Figure createFigure() {
	    return new StateDiagramFigure();
	}

	protected EditPolicy createEditPolicy() {
        return new StateMachineDiagramLayoutPolicy();
	}
}
