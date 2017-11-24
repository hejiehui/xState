package com.xrosstools.xstate.editor.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;


public class StateDiagramFigure extends FreeformLayer {
    private Figure panel;

    public StateDiagramFigure() {
        setLayoutManager(new FreeformLayout());
        
        panel = new Figure();
        ToolbarLayout layout= new ToolbarLayout();
        layout.setHorizontal(false);
        layout.setSpacing(100);
        layout.setStretchMinorAxis(false);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
        panel.setLayoutManager(layout);
        panel.setBorder(new MarginBorder(50));

        add(panel);
    }
    
    public IFigure getContentPane(){
        return panel;
    }
}
