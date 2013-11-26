package com.xross.tools.xstate.editor;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xross.tools.xstate.editor.actions.StateMachineActionConstants;

public class StateMachineContextMenuProvider extends ContextMenuProvider {
	private ActionRegistry actionRegistry;
    public StateMachineContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
        super(viewer);
        actionRegistry = registry;
    }
    public void buildContextMenu(IMenuManager menu) {
        // Add standard action groups to the menu
    	GEFActionConstants.addStandardActionGroups(menu);
     	menu.add(actionRegistry.getAction(StateMachineActionConstants.ID_PREFIX + StateMachineActionConstants.GEN_JUNIT_TEST_CODE));
    	menu.add(actionRegistry.getAction(StateMachineActionConstants.ID_PREFIX + StateMachineActionConstants.GEN_USAGE_CODE));
    	menu.add(new Separator());
    	menu.add(actionRegistry.getAction(StateMachineActionConstants.ID_PREFIX + StateMachineActionConstants.CREATE_EVENT));
    }    
}
