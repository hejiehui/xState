package com.xrosstools.xstate.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IMenuManager;

import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.actions.CommandAction;
import com.xrosstools.xstate.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.editor.commands.DeleteEventCommand;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.treeparts.EventTreePart;

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
            Event e = (Event)part.getModel();
            StateMachine machine = (StateMachine)part.getParent().getModel();
            menu.add(new CommandAction(editor, String.format(REMOVE_ACTION_MSG, e.getId()), false, new DeleteEventCommand(machine, e)));
        }

    }
}
