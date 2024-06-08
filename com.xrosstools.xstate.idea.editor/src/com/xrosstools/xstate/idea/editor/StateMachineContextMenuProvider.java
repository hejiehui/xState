package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xstate.idea.editor.actions.*;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.commands.SelectEventCommand;
import com.xrosstools.xstate.idea.editor.commands.SelectReferenceCommand;
import com.xrosstools.xstate.idea.editor.model.*;
import com.xrosstools.xstate.idea.editor.parts.*;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class StateMachineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
	private Project project;
	private static ImplementationFinder finder = new ImplementationFinder();

	public StateMachineContextMenuProvider(Project project, PropertyChangeListener listener) {
	    super(listener);
        this.project = project;
    }

    public JPopupMenu buildContextMenu(Object selected) {
        JPopupMenu menu = new JPopupMenu();
        EditPart part = (EditPart)selected;

        if(part instanceof StateNodePart) {
            StateNode node = (StateNode)part.getModel();
            StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getParent().getModel();

            buildStateNodeContextMenu(project, menu, node, diagram);
        } else if(part instanceof StateMachinePart) {
            StateMachine machine = (StateMachine)part.getModel();
            buildStateMachineContextMenu(project, menu, machine);
        } else if(part instanceof StateTransitionPart) {
            StateTransitionPart transPart = (StateTransitionPart)part;
            StateMachine machine = (StateMachine)transPart.getTarget().getParent().getModel();
            buildStateTransitionContextMenu(project, menu, (StateTransition)part.getModel(), machine);
        }

        return menu;
    }

    public static void buildStateNodeContextMenu(Project project, JPopupMenu menu, StateNode node, StateMachineDiagram diagram) {
        if(node instanceof StartNode || node instanceof EndNode)
            return;

        buildModifyImplementationMenu(project, menu, new EntryActionAccessor(node));

        addSeparator(menu);

        buildModifyImplementationMenu(project, menu, new ExitActionAccessor(node));

        addSeparator(menu);

        //Add reference to child State Machine
        String refName = node.getReference();
        JMenu referenceMenu = new JMenu(REFERENCE_MSG + (refName == null ? "" : refName));
        for(StateMachine machine: diagram.getMachines()) {
            String name = machine.getName();
            if(!Objects.equals(name, refName))
                referenceMenu.add(createItem(name, (refName != null && refName.equals(name)), new SelectReferenceCommand(node, name)));
        }
        menu.add(referenceMenu);
    }

    private static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static void buildStateMachineContextMenu(Project project, JPopupMenu menu, StateMachine machine) {
        menu.add(createItem(new StateMachineCreateEventAction(project, machine)));
    }

    public static void buildStateTransitionContextMenu(Project project, JPopupMenu menu, StateTransition transition, StateMachine machine) {
        //Create and assign event
        menu.add(createItem(new StateMachineCreateEventAction(project, machine, transition)));
        addSeparator(menu);

        String evtId = transition.getEvent() == null ? "" : transition.getEvent().getDisplayText();
        JMenu eventMenu = new JMenu(ON_EVENT_MSG + evtId);
        for(Event e: transition.getHelper().getEvents()) {
            if (transition.getEvent() != e)
                eventMenu .add(createItem(e.getDisplayText(), transition.getEvent() == e, new SelectEventCommand(transition, e)));
        }
        menu.add(eventMenu);

        addSeparator(menu);

        buildModifyImplementationMenu(project, menu, new TransitionActionAccessor(transition));
        addSeparator(menu);

        buildModifyImplementationMenu(project, menu, new TransitionGuardAccessor(transition));
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

    private static void buildModifyImplementationMenu(Project project, JPopupMenu menu, Accessor<String> accessor) {
        if(isEmpty(accessor.get()))
            menu.add(createItem(new AssignImplementationAction(project, finder, accessor)));
        else{
            menu.add(createItem(new ChangeImplementationAction(project, finder, accessor)));
            menu.add(createItem(new RemoveImplementationAction(accessor)));
            menu.add(createItem(new OpenImplementationAction(project, finder, accessor)));
        }
    }
}
