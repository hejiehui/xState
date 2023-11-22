package com.xrosstools.xstate.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.actions.CommandAction;
import com.xrosstools.xstate.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.editor.commands.AddStateMachineCommand;
import com.xrosstools.xstate.editor.commands.DeleteEventCommand;
import com.xrosstools.xstate.editor.commands.DeleteNodeCommand;
import com.xrosstools.xstate.editor.commands.DeleteStateMachineCommand;
import com.xrosstools.xstate.editor.commands.DeleteTransitionCommand;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;
import com.xrosstools.xstate.editor.treeparts.EventTreePart;
import com.xrosstools.xstate.editor.treeparts.StateMachineTreePart;
import com.xrosstools.xstate.editor.treeparts.StateNodeTreePart;
import com.xrosstools.xstate.editor.treeparts.StateTransitionTreePart;

public class StateMachineOutlineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
    private StateMachineDiagramGraphicalEditor editor;
    public StateMachineOutlineContextMenuProvider(EditPartViewer viewer, StateMachineDiagramGraphicalEditor editor) {
        super(viewer);
        this.editor = editor;
    }
    public void buildContextMenu(IMenuManager menu) {
        EditPartViewer viewer = this.getViewer();
        List selected = viewer.getSelectedEditParts();
        if(selected.size() != 1)
            return;
        
        EditPart part = (EditPart)selected.get(0);
        
        if(part instanceof EventTreePart) {
            buildEventContextMenu(part, menu);
        }

        if(part instanceof StateNodeTreePart) {
            buildStateNodeContextMenu(editor, part, menu);
        }

        if(part instanceof StateTransitionTreePart) {
            buildStateTransitionContextMenu(editor, part, menu);
        }

        if(part instanceof StateMachineTreePart) {
            buildStateMachineContextMenu(editor, part, menu);
        }
    }

    private void buildEventContextMenu(EditPart part, IMenuManager menu) {
        Event e = (Event)part.getModel();
        StateMachine machine = (StateMachine)part.getParent().getModel();
        menu.add(new CommandAction(editor, String.format(REMOVE_ACTION_MSG, e.getId()), false, new DeleteEventCommand(machine, e)));
    }

    private void buildStateNodeContextMenu(IWorkbenchPart editor, EditPart part, IMenuManager menu) {
        StateNode node = (StateNode)part.getModel();
        StateMachine machine = (StateMachine)part.getParent().getModel();
        StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getParent().getModel();
        
        menu.add(new CommandAction(editor, String.format(REMOVE_ACTION_MSG, node.getId()), false, new DeleteNodeCommand(machine, node)));

        menu.add(new Separator());

        StateMachineContextMenuProvider.buildStateNodeContextMenu(editor, menu, node, diagram);
    }

    private void buildStateTransitionContextMenu(IWorkbenchPart editor, EditPart part, IMenuManager menu) {
        StateTransition transition = (StateTransition)part.getModel();
        StateMachine machine = (StateMachine)part.getParent().getParent().getModel();

        menu.add(new CommandAction(editor, String.format(REMOVE_ACTION_MSG, "transition"), false, new DeleteTransitionCommand(transition)));

        menu.add(new Separator());

        StateMachineContextMenuProvider.buildStateTransitionContextMenu(editor, menu, transition, machine);
    }

    private void buildStateMachineContextMenu(IWorkbenchPart editor, EditPart part, IMenuManager menu) {
        StateMachine stateMachine = (StateMachine)part.getModel();
        StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getModel();
        menu.add(new CommandAction(editor, String.format(REMOVE_ACTION_MSG, stateMachine.getName()), false, new DeleteStateMachineCommand(diagram, stateMachine)));

        menu.add(new Separator());

        StateMachineContextMenuProvider.buildStateMachineContextMenu(editor, menu, stateMachine);

        menu.add(new Separator());

        int index = diagram.indexOf(stateMachine);
        int length = diagram.getMachines().size();
        if(index > 0)
            menu.add(new CommandAction(editor, String.format(MOVE_UP_MSG, stateMachine.getName()), false, new AddStateMachineCommand(diagram, stateMachine, index-1)));
        if(index < length - 1)
            menu.add(new CommandAction(editor, String.format(MOVE_DOWN_MSG, stateMachine.getName()), false, new AddStateMachineCommand(diagram, stateMachine, index+2)));
    }
}
