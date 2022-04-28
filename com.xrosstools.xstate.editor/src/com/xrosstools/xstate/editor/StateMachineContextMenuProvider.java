package com.xrosstools.xstate.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.actions.CommandAction;
import com.xrosstools.xstate.editor.actions.StateMachineChangeEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineChangeExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineChangeTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateEventAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineJunitCodeGenAction;
import com.xrosstools.xstate.editor.actions.StateMachineOpenEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineOpenExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineOpenTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineRemoveEntryAction;
import com.xrosstools.xstate.editor.actions.StateMachineRemoveExitAction;
import com.xrosstools.xstate.editor.actions.StateMachineRemoveTransitionAction;
import com.xrosstools.xstate.editor.actions.StateMachineUsageCodeGenAction;
import com.xrosstools.xstate.editor.commands.SelectEventCommand;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.parts.ImageNodePart;
import com.xrosstools.xstate.editor.parts.ImplementationFinder;
import com.xrosstools.xstate.editor.parts.StateMachinePart;
import com.xrosstools.xstate.editor.parts.StateNodePart;
import com.xrosstools.xstate.editor.parts.StateTransitionPart;

public class StateMachineContextMenuProvider extends ContextMenuProvider {
	private GraphicalEditor editor;
	private ImplementationFinder finder = new ImplementationFinder();
    public StateMachineContextMenuProvider(EditPartViewer viewer, GraphicalEditor editor) {
        super(viewer);
        this.editor = editor;
    }
    public void buildContextMenu(IMenuManager menu) {
    	EditPartViewer viewer = this.getViewer();
		List selected = viewer.getSelectedEditParts();
		if(selected.size() != 1)
			return;
		
		EditPart part = (EditPart)selected.get(0);

        if(part instanceof ImageNodePart)
            return;

        if(part instanceof StateNodePart)
		    buildStateNodeContextMenu(menu, editor, (StateNodePart)part);
		else if(part instanceof StateMachinePart)
            buildStateMachineContextMenu(menu, editor, (StateMachinePart)part);
		else if(part instanceof StateTransitionPart)
		    buildStateTransitionContextMenu(menu, editor, (StateTransitionPart)part);
        
        menu.add(new Separator());
        menu.add(new StateMachineJunitCodeGenAction(editor));
        menu.add(new StateMachineUsageCodeGenAction(editor));
    }
    
    private void buildStateNodeContextMenu(IMenuManager menu, IWorkbenchPart editor, StateNodePart part) {
        menu.add(new Separator());
        StateNode node = part.getStateNode();
        if(isEmpty(node.getEntryAction()))
            menu.add(new StateMachineCreateEntryAction(editor, node, finder));
        else{
            menu.add(new StateMachineChangeEntryAction(editor, node, finder));
            menu.add(new StateMachineRemoveEntryAction(editor, node));
            menu.add(new StateMachineOpenEntryAction(editor, node, finder));
        }

        menu.add(new Separator());
        if(isEmpty(node.getExitAction()))
            menu.add(new StateMachineCreateExitAction(editor, node, finder));
        else{
            menu.add(new StateMachineChangeExitAction(editor, node, finder));
            menu.add(new StateMachineRemoveExitAction(editor, node));
            menu.add(new StateMachineOpenExitAction(editor, node, finder));
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    private void buildStateMachineContextMenu(IMenuManager menu, IWorkbenchPart editor, StateMachinePart part) {
        menu.add(new Separator());
        StateMachine machine = (StateMachine)part.getModel();
        menu.add(new StateMachineCreateEventAction(editor, machine));
    }

    private void buildStateTransitionContextMenu(IMenuManager menu, IWorkbenchPart editor, StateTransitionPart part) {
        menu.add(new Separator());
        StateTransition transition = (StateTransition)part.getModel();

        for(Event e: transition.getHelper().getEvents()) {
            menu.add(new CommandAction(editor, e.getId(), transition.getEvent() == e, new SelectEventCommand(transition, e)));
        }
        menu.add(new Separator());
        
        if(isEmpty(transition.getTransitAction()))
            menu.add(new StateMachineCreateTransitionAction(editor, transition, finder));
        else{
            menu.add(new StateMachineChangeTransitionAction(editor, transition, finder));
            menu.add(new StateMachineRemoveTransitionAction(editor, transition));
            menu.add(new StateMachineOpenTransitionAction(editor, transition, finder));
        }
    }
}
