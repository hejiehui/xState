package com.xrosstools.xstate.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.commands.ChangeValueCommand;

public class RemoveImplementationAction extends Action implements StateMachineMessages {
    private Accessor<String> accessor;
    public RemoveImplementationAction(Accessor<String> accessor){
        setText(String.format(REMOVE_ACTION_MSG, accessor.name()));
        this.accessor = accessor;
    }
    public Command createCommand() {
        return new ChangeValueCommand(accessor, null);
    }
}
