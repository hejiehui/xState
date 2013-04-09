package com.xross.tools.xstate.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

public class StateNodeFigure extends RoundedRectangle {
    private Label nameLabel;
    private Label entryActionLabel;
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
    	
    	entryActionLabel = new Label();
    	entryActionLabel.setLabelAlignment(PositionConstants.CENTER);
    	entryActionLabel.setForegroundColor(ColorConstants.black);
        add(entryActionLabel);
    	layout.setConstraint(entryActionLabel, PositionConstants.CENTER);
    	
//    	existActionLabel = new Label();
//    	existActionLabel.setLabelAlignment(PositionConstants.CENTER);
//    	existActionLabel.setForegroundColor(ColorConstants.black);
//        add(existActionLabel);
//    	layout.setConstraint(existActionLabel, PositionConstants.CENTER);

    }

    public Rectangle getTextBounds() {
        return nameLabel.getTextBounds();
    }

    public void setName(String name) {
    	nameLabel.setText(name);
    	nameLabel.setToolTip(new Label(name));
    	repaint();
    }
    
    public void setEntryAction(String decision) {
    	entryActionLabel.setText(decision);
    	entryActionLabel.setToolTip(new Label(decision));
        repaint();
    }
    
    public void setExistAction(String decision) {
    	existActionLabel.setText(decision);
    	existActionLabel.setToolTip(new Label(decision));
        repaint();
    }
}
