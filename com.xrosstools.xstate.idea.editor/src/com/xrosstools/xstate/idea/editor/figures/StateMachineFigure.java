package com.xrosstools.xstate.idea.editor.figures;

import com.xrosstools.idea.gef.figures.*;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.xstate.idea.editor.model.StateMachineConstants;

import java.awt.*;

public class StateMachineFigure extends Figure implements StateMachineConstants {
	private Label label;
	private RectangleFigure topLine;
	private Figure figure;
	private RectangleFigure bottomLine;
	
    public StateMachineFigure(int width, int height) {
        figure = new Figure();
        figure.setLayoutManager(new FreeformLayout());

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
    	label.setToolTipText(toolTip);
        repaint();
    }
    
    public Figure getFigure(){
    	return figure;
    }
    
    public Dimension getPanelSize() {
        return figure.getPreferredSize();
    }

    public void setPanelSize(Dimension size) {
        figure.setPreferredSize(size);
        topLine.setSize(new Dimension(size.width, 1));
        bottomLine.setSize(new Dimension(size.width, 2));        
    }
}
