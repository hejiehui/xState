package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xstate.idea.editor.actions.*;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.commands.SelectEventCommand;
import com.xrosstools.xstate.idea.editor.model.*;
import com.xrosstools.xstate.idea.editor.parts.*;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class StateMachineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
	private Project project;
	private ImplementationFinder finder = new ImplementationFinder();

	public StateMachineContextMenuProvider(Project project, PropertyChangeListener listener) {
	    super(listener);
        this.project = project;
    }

    public JPopupMenu buildContextMenu(Object selected) {
        JPopupMenu menu = new JPopupMenu();
        EditPart part = (EditPart)selected;

        if(part instanceof ImageNodePart)
            return menu;

        if(part instanceof StateNodePart) {
            buildStateNodeContextMenu(menu, (StateNodePart) part);
        } else if(part instanceof StateMachinePart) {
            buildStateMachineContextMenu(menu, (StateMachinePart) part);
        } else if(part instanceof StateTransitionPart) {
            buildStateTransitionContextMenu(menu, (StateTransitionPart) part);
        }

        return menu;
    }
    
    private void buildStateNodeContextMenu(JPopupMenu menu, StateNodePart part) {
        StateNode node = part.getStateNode();

        buildModifyImplementationMenu(menu, new EntryActionAccessor(node));

        addSeparator(menu);
        buildModifyImplementationMenu(menu, new ExitActionAccessor(node));
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    private void buildStateMachineContextMenu(JPopupMenu menu, StateMachinePart part) {
        StateMachine machine = (StateMachine)part.getModel();
        menu.add(createItem(new StateMachineCreateEventAction(project, machine)));
    }

    private void buildStateTransitionContextMenu(JPopupMenu menu, StateTransitionPart part) {
        StateTransition transition = (StateTransition)part.getModel();

        //Create and assign event
        StateMachine machine = (StateMachine)part.getParent().getParent().getModel();
        menu.add(createItem(new StateMachineCreateEventAction(project, machine, transition)));
        addSeparator(menu);

        for(Event e: transition.getHelper().getEvents()) {
            menu.add(createItem(e.getId(), transition.getEvent() == e, new SelectEventCommand(transition, e)));
        }
        addSeparator(menu);
        buildModifyImplementationMenu(menu, new TransitionActionAccessor(transition));
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

    private void buildModifyImplementationMenu(JPopupMenu menu, Accessor<String> accessor) {
        if(isEmpty(accessor.get()))
            menu.add(createItem(new AssignImplementationAction(project, finder, accessor)));
        else{
            menu.add(createItem(new ChangeImplementationAction(project, finder, accessor)));
            menu.add(createItem(new RemoveImplementationAction(accessor)));
            menu.add(createItem(new OpenImplementationAction(project, finder, accessor)));
        }
    }
}
