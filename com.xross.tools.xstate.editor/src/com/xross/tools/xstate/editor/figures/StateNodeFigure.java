package com.xross.tools.xstate.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;

import com.xross.tools.xstate.editor.Activator;

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
    	layout.setConstraint(nameLabel, PositionConstants.TOP);
    	
    	entryActionLabel = new Label();
    	entryActionLabel.setIcon(Activator.getDefault().getImageRegistry().get(Activator.ENTRY_ACTION));
    	entryActionLabel.setLabelAlignment(PositionConstants.CENTER);
    	entryActionLabel.setForegroundColor(ColorConstants.black);
//        add(entryActionLabel);
//    	layout.setConstraint(entryActionLabel, PositionConstants.WEST);
    	ImageFigure f = new ImageFigure(Activator.getDefault().getImageRegistry().get(Activator.ENTRY_ACTION));
    	add(f);
    	layout.setConstraint(entryActionLabel, PositionConstants.WEST);
    	
    	existActionLabel = new Label();
    	existActionLabel.setIcon(Activator.getDefault().getImageRegistry().get(Activator.EXIT_ACTION));
    	existActionLabel.setLabelAlignment(PositionConstants.CENTER);
    	existActionLabel.setForegroundColor(ColorConstants.black);
//        add(existActionLabel);
//    	layout.setConstraint(existActionLabel, PositionConstants.EAST);
    	ImageFigure exit = new ImageFigure(Activator.getDefault().getImageRegistry().get(Activator.EXIT_ACTION));
    	add(exit);
    	layout.setConstraint(existActionLabel, PositionConstants.EAST);
    }

    public Dimension getGoodSize() {
        return new Dimension(Math.max(100, nameLabel.getTextBounds().width + 10), 50);
    }

    public void setName(String name) {
    	nameLabel.setText(name);
    	nameLabel.setToolTip(new Label(name));
    	repaint();
    }
    
    public void setEntryAction(String entryAction) {
//    	entryActionLabel.setText(entryAction);
    	entryActionLabel.setToolTip(new Label(entryAction));
        repaint();
    }
    
    public void setExistAction(String existAction) {
//    	existActionLabel.setText(existAction);
    	existActionLabel.setToolTip(new Label(existAction));
        repaint();
    }
}
