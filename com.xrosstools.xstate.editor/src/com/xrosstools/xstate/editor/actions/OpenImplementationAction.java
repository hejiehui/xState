package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xstate.editor.commands.Accessor;
import com.xrosstools.xstate.editor.commands.ChangeValueCommand;
import com.xrosstools.xstate.editor.parts.ImplementationFinder;
import com.xrosstools.xstate.editor.parts.ImplementationSource;

public class OpenImplementationAction extends WorkbenchPartAction implements ImplementationSource, StateMachineMessages {
    private Accessor<String> accessor;
    private ImplementationFinder finder;
    public OpenImplementationAction(IWorkbenchPart part, ImplementationFinder finder, Accessor<String> accessor){
        super(part);
        setText(String.format(OPEN_ACTION_MSG, accessor.name()));
        this.accessor = accessor;
        this.finder = finder;
    }
    
    protected boolean calculateEnabled() {
        return true;
    }
    
    public void run() {
        finder.openImpl(this);
    }

    @Override
    public String getImplementation() {
        return accessor.get();
    }

    @Override
    public void implChanged(String newImpl) {
        execute(new ChangeValueCommand<String>(accessor, newImpl));
    }
}
