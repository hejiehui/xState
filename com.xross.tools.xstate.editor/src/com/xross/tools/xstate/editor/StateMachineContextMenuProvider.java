package com.xross.tools.xstate.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xross.tools.xstate.editor.actions.StateMachineChangeEntryAction;
import com.xross.tools.xstate.editor.actions.StateMachineChangeExitAction;
import com.xross.tools.xstate.editor.actions.StateMachineChangeTransitionAction;
import com.xross.tools.xstate.editor.actions.StateMachineCreateEntryAction;
import com.xross.tools.xstate.editor.actions.StateMachineCreateEventAction;
import com.xross.tools.xstate.editor.actions.StateMachineCreateExitAction;
import com.xross.tools.xstate.editor.actions.StateMachineCreateTransitionAction;
import com.xross.tools.xstate.editor.actions.StateMachineJunitCodeGenAction;
import com.xross.tools.xstate.editor.actions.StateMachineRemoveEntryAction;
import com.xross.tools.xstate.editor.actions.StateMachineRemoveExitAction;
import com.xross.tools.xstate.editor.actions.StateMachineRemoveTransitionAction;
import com.xross.tools.xstate.editor.actions.StateMachineUsageCodeGenAction;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;
import com.xross.tools.xstate.editor.parts.StateMachinePart;
import com.xross.tools.xstate.editor.parts.StateNodePart;
import com.xross.tools.xstate.editor.parts.StateTransitionPart;

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
		addEventMenu(menu, selected);
    }
	private void addEventMenu(IMenuManager menu, List selected) {
		if(selected.size() == 1 && selected.get(0) instanceof StateMachinePart){
	    	menu.add(new Separator());
	    	StateMachinePart part = (StateMachinePart)selected.get(0);
	    	StateMachine machine = (StateMachine)part.getModel();
	    	menu.add(new StateMachineCreateEventAction(editor, machine));
		}
	}

	private void addNodeActionMenu(IMenuManager menu, List selected) {
		if(selected.size() == 1 && selected.get(0) instanceof StateNodePart){
	    	menu.add(new Separator());
	    	StateNodePart part = (StateNodePart)selected.get(0);
	    	StateNode node = part.getStateNode();
	    	if(node.getEntryAction() == null)
	    		menu.add(new StateMachineCreateEntryAction(editor, node));
	    	else{
	    		menu.add(new StateMachineChangeEntryAction(editor, node));
	    		menu.add(new StateMachineRemoveEntryAction(editor, node));
	    	}

	    	if(node.getExitAction() == null)
	    		menu.add(new StateMachineCreateExitAction(editor, node));
	    	else{
	    		menu.add(new StateMachineChangeExitAction(editor, node));
	    		menu.add(new StateMachineRemoveExitAction(editor, node));
	    	}
		}
	}
	
	private void addTransitionMenu(IMenuManager menu, List selected) {
		if(selected.size() == 1 && selected.get(0) instanceof StateTransitionPart){
	    	menu.add(new Separator());
	    	StateMachinePart part = (StateMachinePart)selected.get(0);
	    	StateTransition transition = (StateTransition)part.getModel();

	    	if(transition.getTransitAction() == null)
	    		menu.add(new StateMachineCreateTransitionAction(editor, transition));
	    	else{
	    		menu.add(new StateMachineChangeTransitionAction(editor, transition));
	    		menu.add(new StateMachineRemoveTransitionAction(editor, transition));
	    	}
		}
	}
}
