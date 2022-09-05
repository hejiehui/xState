package com.xrosstools.xstate.idea.editor.figures;

import com.xrosstools.idea.gef.figures.*;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.xstate.idea.editor.StateMachineEditorProvider;

import java.awt.*;

public class StateNodeFigure extends RoundedRectangle {
    private Label nameLabel;
    private IconFigure entry;
    private IconFigure exit;

    public StateNodeFigure() {
    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setHorizontal(false);
    	layout.setSpacing(1);
    	layout.setStretchMinorAxis(false);
    	layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
    	setLayoutManager(layout);
    	this.getInsets().set(5,5,5,5);
    	
    	nameLabel = new Label();
        nameLabel.setLabelAlignment(PositionConstants.CENTER);
        nameLabel.setForegroundColor(ColorConstants.green);
        add(nameLabel);
    	
        Figure line = new RectangleFigure();
        line.setBackgroundColor(ColorConstants.lightGray);
        line.setForegroundColor(ColorConstants.lightGray);
        line.setPreferredSize(new Dimension(-1, 1));
        add(line);
        
        Figure actionsPanel = new Figure();
    	add(actionsPanel);
    	
    	ToolbarLayout actionsPanelLayout= new ToolbarLayout();
    	actionsPanelLayout.setHorizontal(true);
    	actionsPanelLayout.setSpacing(20);
    	actionsPanelLayout.setStretchMinorAxis(true);
    	actionsPanelLayout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
    	actionsPanel.setLayoutManager(actionsPanelLayout);
    	
    	entry = new IconFigure(StateMachineEditorProvider.ENTRY_ACTION);
    	actionsPanel.add(entry);

    	Figure actionLine = new RectangleFigure();
    	actionLine.setBackgroundColor(ColorConstants.lightGray);
    	actionLine.setForegroundColor(ColorConstants.lightGray);
    	actionLine.setPreferredSize(new Dimension(1, 20));
        actionsPanel.add(actionLine);
    	
    	exit = new IconFigure(StateMachineEditorProvider.EXIT_ACTION);
    	actionsPanel.add(exit);
    }
    
    public boolean isEntryAction(Figure figure) {
    	return figure == entry;
    }

    public Dimension getGoodSize() {
        return new Dimension(Math.max(100, nameLabel.getSize().width + 10), 50);
    }

    public void setName(String name) {
    	nameLabel.setText(name);
    	nameLabel.setToolTipText(name);
    	repaint();
    }
    
    public void setEntryAction(String entryAction) {
		entry.setToolTipText(entryAction);
        repaint();
    }
    
    public void setExistAction(String existAction) {
		exit.setToolTipText(existAction);
        repaint();
    }
}
