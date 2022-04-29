package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.Accessor;
import com.xrosstools.xstate.editor.commands.ChangeValueCommand;

public class RemoveImplementationAction extends WorkbenchPartAction implements StateMachineMessages {
    private Accessor<String> accessor;
    public RemoveImplementationAction(IWorkbenchPart part, Accessor<String> accessor){
        super(part);
        setText(String.format(REMOVE_ACTION_MSG, accessor.name()));
        this.accessor = accessor;
    }
    
    protected boolean calculateEnabled() {
        return true;
    }
    
    public void run() {
        execute(new ChangeValueCommand<String>(accessor, null));
    }
}
