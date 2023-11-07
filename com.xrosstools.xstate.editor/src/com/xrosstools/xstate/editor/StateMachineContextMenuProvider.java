package com.xrosstools.xstate.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.actions.AssignImplementationAction;
import com.xrosstools.xstate.editor.actions.ChangeImplementationAction;
import com.xrosstools.xstate.editor.actions.CommandAction;
import com.xrosstools.xstate.editor.actions.OpenImplementationAction;
import com.xrosstools.xstate.editor.actions.RemoveImplementationAction;
import com.xrosstools.xstate.editor.actions.StateMachineCreateEventAction;
import com.xrosstools.xstate.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.editor.commands.Accessor;
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

public class StateMachineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
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
    }
    
    private void buildStateNodeContextMenu(IMenuManager menu, IWorkbenchPart editor, StateNodePart part) {
        StateNode node = part.getStateNode();
        
        menu.add(new Separator());
        buildModifyImplementationMenu(menu, new EntryActionAccessor(node));

        menu.add(new Separator());
        buildModifyImplementationMenu(menu, new ExitActionAccessor(node));
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
        
        //Create and assign event
        StateMachine machine = (StateMachine)part.getTarget().getParent().getModel();
        menu.add(new StateMachineCreateEventAction(editor, machine, transition));
        menu.add(new Separator());

        for(Event e: transition.getHelper().getEvents()) {
            menu.add(new CommandAction(editor, e.getId(), transition.getEvent() == e, new SelectEventCommand(transition, e)));
        }
        menu.add(new Separator());
        buildModifyImplementationMenu(menu, new TransitionActionAccessor(transition));
        buildModifyImplementationMenu(menu, new TransitionGuardAccessor(transition));
    }
    
    private class EntryActionAccessor implements Accessor<String> {
        StateNode node;
        EntryActionAccessor(StateNode node) {this.node = node;}
        public String get() {return node.getEntryAction();}
        public void set(String value) {node.setEntryAction(value);}
        public String name() {return ENTRY_MSG;}
    }

    private class ExitActionAccessor implements Accessor<String> {
        StateNode node;
        ExitActionAccessor(StateNode node) {this.node = node;}
        public String get() {return node.getExitAction();}
        public void set(String value) {node.setExitAction(value);}
        public String name() {return EXIT_MSG;}
    }

    private class TransitionActionAccessor implements Accessor<String> {
        StateTransition transition;
        TransitionActionAccessor(StateTransition transition) {this.transition = transition;}
        public String get() {return transition.getTransitAction();}
        public void set(String value) {transition.setTransitAction(value);}
        public String name() {return TRANSITION_MSG;}
    }

    private class TransitionGuardAccessor implements Accessor<String> {
        StateTransition transition;
        TransitionGuardAccessor(StateTransition transition) {this.transition = transition;}
        public String get() {return transition.getTransitGuard();}
        public void set(String value) {transition.setTransitGuard(value);}
        public String name() {return TRANSITION_GUARD_MSG;}
    }

    private void buildModifyImplementationMenu(IMenuManager menu, Accessor<String> accessor) {
        if(isEmpty(accessor.get()))
            menu.add(new AssignImplementationAction(editor, finder, accessor));
        else{
            menu.add(new ChangeImplementationAction(editor, finder, accessor));
            menu.add(new RemoveImplementationAction(editor, accessor));
            menu.add(new OpenImplementationAction(editor, finder, accessor));
        }
    }
}
