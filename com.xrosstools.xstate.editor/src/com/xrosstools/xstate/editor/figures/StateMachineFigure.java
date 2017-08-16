package com.xrosstools.xstate.editor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;

import com.xrosstools.xstate.editor.model.StateMachineConstants;

public class StateMachineFigure extends Figure implements StateMachineConstants{
	private Label label;
	private IFigure figure;
    public StateMachineFigure(int width, int height) {
        figure = new Figure();
        figure.setLayoutManager(new XYLayout());
        figure.setBorder(new LineBorder(ColorConstants.lightGray, 1));

        figure.setPreferredSize(new Dimension(width, height));

        label = new Label();

    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setSpacing(TOP_LEVEL_SPACE);
    	setLayoutManager(layout);
    	
        label.setLabelAlignment(PositionConstants.LEFT);
        label.setForegroundColor(ColorConstants.blue);

        
        add(label);
        add(figure);
    }

    public void setName(String name, String toolTip) {
    	label.setText(name);
    	label.setToolTip(new Label(toolTip));
        repaint();
    }
    
    public IFigure getFigure(){
    	return figure;
    }
}
