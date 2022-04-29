package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.Accessor;
import com.xrosstools.xstate.editor.commands.ChangeValueCommand;
import com.xrosstools.xstate.editor.parts.ImplementationFinder;

public class ChangeImplementationAction extends WorkbenchPartAction implements StateMachineMessages {
    private Accessor<String> accessor;
    private ImplementationFinder finder;
    public ChangeImplementationAction(IWorkbenchPart part, ImplementationFinder finder, Accessor<String> accessor){
        super(part);
        setText(String.format(CHANGE_ACTION_MSG, accessor.name()));
        this.finder = finder;
        this.accessor = accessor;
    }
    
    protected boolean calculateEnabled() {
        return true;
    }
    
    public void run() {
        String impl = finder.assignImpl(accessor.get());
        execute(new ChangeValueCommand<String>(accessor, impl));
    }
}
