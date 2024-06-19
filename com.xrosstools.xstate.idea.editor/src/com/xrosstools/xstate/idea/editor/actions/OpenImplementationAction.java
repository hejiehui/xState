package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.parts.ImplementationFinder;
import com.xrosstools.xstate.idea.editor.parts.ImplementationSource;

import java.awt.event.ActionEvent;

public class OpenImplementationAction extends Action implements ImplementationSource, StateMachineMessages {
    private Project project;
    private Accessor accessor;
    private ImplementationFinder finder;
    public OpenImplementationAction(Project project, ImplementationFinder finder, Accessor accessor){
        setText(String.format(OPEN_ACTION_MSG, accessor.name()));
        this.project = project;
        this.accessor = accessor;
        this.finder = finder;
    }

    public void actionPerformed(ActionEvent e) {
        finder.openImpl(project, this);
    }

    public Command createCommand() {return null;}

    @Override
    public String getImplementation() {
        return accessor.getClassName();
    }

    @Override
    public void implChanged(String newImpl) {
        ;
    }
}
