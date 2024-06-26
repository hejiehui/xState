package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.commands.ChangeClassCommand;
import com.xrosstools.xstate.idea.editor.parts.ImplementationFinder;

public class ChangeImplementationAction extends Action implements StateMachineMessages {
    private Project project;
    private Accessor accessor;
    private ImplementationFinder finder;
    public ChangeImplementationAction(Project project, ImplementationFinder finder, Accessor accessor){
        setText(String.format(CHANGE_ACTION_MSG, accessor.name()));
        this.project = project;
        this.finder = finder;
        this.accessor = accessor;
    }

    public Command createCommand() {
        String impl = finder.assignImpl(project, accessor.get());
        return new ChangeClassCommand(accessor, impl);
    }
}
