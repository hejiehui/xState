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
	private RectangleFigure topLine; 
	private IFigure figure;
	private RectangleFigure bottomLine;
	
    public StateMachineFigure(int width, int height) {
        figure = new Figure();
        figure.setLayoutManager(new XYLayout());

        label = new Label();

        ToolbarLayout layout= new ToolbarLayout();
        layout.setSpacing(TITLE_SPACE);
        setLayoutManager(layout);
        
        label.setLabelAlignment(PositionConstants.LEFT);
        label.setForegroundColor(ColorConstants.blue);
      
        topLine = new RectangleFigure();
        topLine.setBackgroundColor(ColorConstants.lightGray);
        topLine.setForegroundColor(ColorConstants.lightGray);
        
        bottomLine = new RectangleFigure();
        bottomLine.setBackgroundColor(ColorConstants.lightGray);
        bottomLine.setForegroundColor(ColorConstants.lightGray);

        setPanelSize(new Dimension(width, height));
        
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
    
    public void setPanelSize(Dimension size) {
        figure.setPreferredSize(size);
        topLine.setSize(new Dimension(size.width, 1));
        bottomLine.setSize(new Dimension(size.width, 2));        
    }
}
