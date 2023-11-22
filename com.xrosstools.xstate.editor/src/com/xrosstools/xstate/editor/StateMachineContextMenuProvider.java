package com.xrosstools.xstate.editor;

import java.util.List;
import java.util.Objects;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
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
import com.xrosstools.xstate.editor.commands.SelectReferenceCommand;
import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.parts.ImplementationFinder;
import com.xrosstools.xstate.editor.parts.StateMachinePart;
import com.xrosstools.xstate.editor.parts.StateNodePart;
import com.xrosstools.xstate.editor.parts.StateTransitionPart;

public class StateMachineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
	private GraphicalEditor editor;
	private static ImplementationFinder finder = new ImplementationFinder();
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

        if(part instanceof StateNodePart) {
            StateNode node = (StateNode)part.getModel();
            StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getParent().getModel();
            
		    buildStateNodeContextMenu(editor, menu, node, diagram);
        } else if(part instanceof StateMachinePart) {
            StateMachine machine = (StateMachine)part.getModel();
            buildStateMachineContextMenu(editor, menu, machine);
        } else if(part instanceof StateTransitionPart) {
		    StateTransitionPart transPart = (StateTransitionPart)part;
	        StateMachine machine = (StateMachine)transPart.getTarget().getParent().getModel();

	        buildStateTransitionContextMenu(editor, menu, (StateTransition)part.getModel(), machine);
		}
    }
    
    public static void buildStateNodeContextMenu(IWorkbenchPart editor, IMenuManager menu, StateNode node, StateMachineDiagram diagram) {
        if(node instanceof StartNode || node instanceof EndNode)
            return;

        menu.add(new Separator());
        buildModifyImplementationMenu(editor, finder, menu, new EntryActionAccessor(node));

        menu.add(new Separator());
        buildModifyImplementationMenu(editor, finder, menu, new ExitActionAccessor(node));

        menu.add(new Separator());
    
        //Add reference to child State Machine
        
        String refName = node.getReference();
        MenuManager referenceMenu = new MenuManager(REFERENCE_MSG + (refName == null ? "" : refName));
        for(StateMachine machine: diagram.getMachines()) {
            String name = machine.getName();
            if(!Objects.equals(name, refName))
                referenceMenu.add(new CommandAction(editor, name, (refName != null && refName.equals(name)), new SelectReferenceCommand(node, name)));
        }
        menu.add(referenceMenu);
    }

    private static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static void buildStateMachineContextMenu(IWorkbenchPart editor, IMenuManager menu, StateMachine machine) {
        menu.add(new StateMachineCreateEventAction(editor, machine));
    }

    public static void buildStateTransitionContextMenu(IWorkbenchPart editor, IMenuManager menu, StateTransition transition, StateMachine machine) {
        //Create and assign event
        menu.add(new StateMachineCreateEventAction(editor, machine, transition));
        menu.add(new Separator());

        String evtId = transition.getEvent() == null ? "" : transition.getEvent().getId();
        MenuManager eventMenu = new MenuManager(ON_EVENT_MSG + evtId);
        for(Event e: transition.getHelper().getEvents()) {
            if (transition.getEvent() != e)
                eventMenu.add(new CommandAction(editor, e.getId(), transition.getEvent() == e, new SelectEventCommand(transition, e)));
        }
        menu.add(eventMenu);

        
        menu.add(new Separator());
        buildModifyImplementationMenu(editor, finder, menu, new TransitionActionAccessor(transition));
        menu.add(new Separator());
        buildModifyImplementationMenu(editor, finder, menu, new TransitionGuardAccessor(transition));
    }
    
    private static class EntryActionAccessor implements Accessor<String> {
        StateNode node;
        EntryActionAccessor(StateNode node) {this.node = node;}
        public String get() {return node.getEntryAction();}
        public void set(String value) {node.setEntryAction(value);}
        public String name() {return ENTRY_MSG;}
    }

    private static class ExitActionAccessor implements Accessor<String> {
        StateNode node;
        ExitActionAccessor(StateNode node) {this.node = node;}
        public String get() {return node.getExitAction();}
        public void set(String value) {node.setExitAction(value);}
        public String name() {return EXIT_MSG;}
    }

    private static class TransitionActionAccessor implements Accessor<String> {
        StateTransition transition;
        TransitionActionAccessor(StateTransition transition) {this.transition = transition;}
        public String get() {return transition.getTransitAction();}
        public void set(String value) {transition.setTransitAction(value);}
        public String name() {return TRANSITION_MSG;}
    }

    private static class TransitionGuardAccessor implements Accessor<String> {
        StateTransition transition;
        TransitionGuardAccessor(StateTransition transition) {this.transition = transition;}
        public String get() {return transition.getTransitGuard();}
        public void set(String value) {transition.setTransitGuard(value);}
        public String name() {return TRANSITION_GUARD_MSG;}
    }

    private static void buildModifyImplementationMenu(IWorkbenchPart editor, ImplementationFinder finder, IMenuManager menu, Accessor<String> accessor) {
        if(isEmpty(accessor.get()))
            menu.add(new AssignImplementationAction(editor, finder, accessor));
        else{
            menu.add(new ChangeImplementationAction(editor, finder, accessor));
            menu.add(new RemoveImplementationAction(editor, accessor));
            menu.add(new OpenImplementationAction(editor, finder, accessor));
        }
    }
}
