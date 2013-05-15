package com.xross.tools.xstate.editor.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Label;

public class StateMachineFigure extends Figure {
	public StateMachineFigure(){
//	    Figure figure = new FreeformLayer();
	    setLayoutManager(new FreeformLayout());
	    Label l = new Label("aaaaa");
	    add(l);
//	    return figure;
	}
}
