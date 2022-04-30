package com.xrosstools.xstate.editor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
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

        figure.setPreferredSize(new Dimension(width, height));

        label = new Label();

        ToolbarLayout layout= new ToolbarLayout();
        layout.setSpacing(TITLE_SPACE);
        setLayoutManager(layout);
        
        label.setLabelAlignment(PositionConstants.LEFT);
        label.setForegroundColor(ColorConstants.blue);
      
        RectangleFigure topLine = new RectangleFigure();
        topLine.setBackgroundColor(ColorConstants.lightGray);
        topLine.setForegroundColor(ColorConstants.lightGray);
        topLine.setSize(new Dimension(-1, 1));
        
        RectangleFigure bottomLine = new RectangleFigure();
        bottomLine.setBackgroundColor(ColorConstants.lightGray);
        bottomLine.setForegroundColor(ColorConstants.lightGray);
        bottomLine.setSize(new Dimension(-1, 2));

        
        add(label);
        add(topLine);
        add(figure);
        add(bottomLine);
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
