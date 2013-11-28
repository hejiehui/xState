package com.xross.tools.xstate.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;

public class StateNodeFigure extends RoundedRectangle {
    private Label nameLabel;
    private Label enterActionLabel;
    private Label existActionLabel;

    public StateNodeFigure() {
    	BorderLayout layout= new BorderLayout();
    	setLayoutManager(layout);
    	this.setBorder(new MarginBorder(5));
    	
    	nameLabel = new Label();
        nameLabel.setLabelAlignment(PositionConstants.CENTER);
        nameLabel.setForegroundColor(ColorConstants.darkGreen);
        add(nameLabel);
    	layout.setConstraint(nameLabel, PositionConstants.BOTTOM);
    	
    	enterActionLabel = new Label();
    	enterActionLabel.setLabelAlignment(PositionConstants.CENTER);
    	enterActionLabel.setForegroundColor(ColorConstants.black);
        add(enterActionLabel);
    	layout.setConstraint(enterActionLabel, PositionConstants.CENTER);
    	
    	existActionLabel = new Label();
    	existActionLabel.setLabelAlignment(PositionConstants.CENTER);
    	existActionLabel.setForegroundColor(ColorConstants.black);
        add(existActionLabel);
    	layout.setConstraint(existActionLabel, PositionConstants.TOP);

    }

    public Dimension getGoodSize() {
        return new Dimension(Math.max(100, nameLabel.getTextBounds().width + 10), 50);
    }

    public void setName(String name) {
    	nameLabel.setText(name);
    	nameLabel.setToolTip(new Label(name));
    	repaint();
    }
    
    public void setEnterAction(String enterAction) {
    	enterActionLabel.setText(enterAction);
        repaint();
    }
    
    public void setExistAction(String existAction) {
    	existActionLabel.setText(existAction);
    	existActionLabel.setToolTip(new Label(existAction));
        repaint();
    }
}
