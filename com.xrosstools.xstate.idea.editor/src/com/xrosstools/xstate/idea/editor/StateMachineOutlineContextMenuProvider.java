package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.actions.CommandAction;
import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xstate.idea.editor.actions.StateMachineCreateEventAction;
import com.xrosstools.xstate.idea.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.idea.editor.commands.*;
import com.xrosstools.xstate.idea.editor.model.*;
import com.xrosstools.xstate.idea.editor.parts.ImplementationFinder;
import com.xrosstools.xstate.idea.editor.treeparts.*;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class StateMachineOutlineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
    private Project project;
    private ImplementationFinder finder = new ImplementationFinder();

    public StateMachineOutlineContextMenuProvider(Project project, PropertyChangeListener listener) {
        super(listener);
        this.project = project;
    }

    @Override
    public JPopupMenu buildContextMenu(Object selected) {
        AbstractTreeEditPart part = (AbstractTreeEditPart )selected;
        JPopupMenu menu = new JPopupMenu();

        if(part instanceof EventTreePart) {
            buildEventContextMenu(part, menu);
        }

        if(part instanceof StateNodeTreePart) {
            buildStateNodeContextMenu(part, menu);
        }

        if(part instanceof StateTransitionTreePart) {
            buildStateTransitionContextMenu(part, menu);
        }

        if(part instanceof StateMachineTreePart) {
            buildStateMachineContextMenu(part, menu);
        }
        return menu;
    }

    private void buildEventContextMenu(AbstractTreeEditPart part, JPopupMenu menu) {
        Event e = (Event)part.getModel();
        StateMachine machine = (StateMachine)part.getParent().getModel();
        menu.add(createItem(new CommandAction(String.format(REMOVE_ACTION_MSG, e.getDisplayText()), false, new DeleteEventCommand(machine, e))));
    }

    private void buildStateNodeContextMenu(AbstractTreeEditPart part, JPopupMenu menu) {
        StateNode node = (StateNode)part.getModel();
        StateMachine machine = (StateMachine)part.getParent().getModel();
        StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getParent().getModel();

        menu.add(createItem(new CommandAction(String.format(REMOVE_ACTION_MSG, node.getDisplayText()), false, new DeleteNodeCommand(machine, node))));

        addSeparator(menu);

        StateMachineContextMenuProvider.buildStateNodeContextMenu(project, menu, node, diagram);
    }

    private void buildStateTransitionContextMenu(AbstractTreeEditPart part, JPopupMenu menu) {
        StateTransition transition = (StateTransition)part.getModel();
        StateMachine machine = (StateMachine)part.getParent().getParent().getModel();

        menu.add(createItem(new CommandAction(String.format(REMOVE_ACTION_MSG, "transition"), false, new DeleteTransitionCommand(transition))));

        addSeparator(menu);

        StateMachineContextMenuProvider.buildStateTransitionContextMenu(project, menu, transition, machine);
    }

    private void buildStateMachineContextMenu(AbstractTreeEditPart part, JPopupMenu menu) {
        StateMachine stateMachine = (StateMachine)part.getModel();
        StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getModel();
        menu.add(createItem(new CommandAction(String.format(REMOVE_ACTION_MSG, stateMachine.getName()), false, new DeleteStateMachineCommand(diagram, stateMachine))));

        addSeparator(menu);

        StateMachineContextMenuProvider.buildStateMachineContextMenu(project, menu, stateMachine);

        addSeparator(menu);

        int index = diagram.indexOf(stateMachine);
        int length = diagram.getMachines().size();
        if(index > 0)
            menu.add(createItem(new CommandAction(String.format(MOVE_UP_MSG, stateMachine.getName()), false, new AddStateMachineCommand(diagram, stateMachine, index-1))));
        if(index < length - 1)
            menu.add(createItem(new CommandAction(String.format(MOVE_DOWN_MSG, stateMachine.getName()), false, new AddStateMachineCommand(diagram, stateMachine, index+2))));
    }
}
