package com.xross.tools.xstate.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xross.tools.xstate.editor.actions.StateMachineActionConstants;
import com.xross.tools.xstate.editor.actions.StateMachineCreateEventAction;
import com.xross.tools.xstate.editor.actions.StateMachineJunitCodeGenAction;
import com.xross.tools.xstate.editor.actions.StateMachineUsageCodeGenAction;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.parts.StateMachinePart;

public class StateMachineContextMenuProvider extends ContextMenuProvider {
	private GraphicalEditor editor;
    public StateMachineContextMenuProvider(EditPartViewer viewer, GraphicalEditor editor) {
        super(viewer);
        this.editor = editor;
    }
    public void buildContextMenu(IMenuManager menu) {
        // Add standard action groups to the menu
    	GEFActionConstants.addStandardActionGroups(menu);
     	menu.add(new StateMachineJunitCodeGenAction(editor));
     	menu.add(new StateMachineUsageCodeGenAction(editor));

    	EditPartViewer viewer = this.getViewer();
		List selected = viewer.getSelectedEditParts();
		if(selected.size() == 1 && selected.get(0) instanceof StateMachinePart){
	    	menu.add(new Separator());
	    	StateMachinePart part = (StateMachinePart)selected.get(0);
	    	StateMachine machine = (StateMachine)part.getModel();
	    	menu.add(new StateMachineCreateEventAction(editor, machine));
		}
    }
}
