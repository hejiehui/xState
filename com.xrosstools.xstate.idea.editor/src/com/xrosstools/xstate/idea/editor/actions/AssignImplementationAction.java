package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.commands.ChangeValueCommand;
import com.xrosstools.xstate.idea.editor.parts.ImplementationFinder;

public class AssignImplementationAction extends Action implements StateMachineMessages {
    private Project project;
    private Accessor<String> accessor;
    private ImplementationFinder finder;

    public AssignImplementationAction(Project project, ImplementationFinder finder, Accessor<String> accessor){
        setText(String.format(ASSIGN_ACTION_MSG, accessor.name()));
        this.project = project;
        this.finder = finder;
        this.accessor = accessor;
    }

    public Command createCommand() {
        String impl = finder.assignImpl(project, "");
        
        if(impl == null)
            return null;
        
        return new ChangeValueCommand(accessor, impl);
    }
}
