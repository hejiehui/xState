package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.xstate.idea.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.idea.editor.parts.ImplementationFinder;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class StateMachineOutlineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
    private Project project;
    private ImplementationFinder finder = new ImplementationFinder();

    public StateMachineOutlineContextMenuProvider(Project project, PropertyChangeListener listener) {
        super(listener);
        this.project = project;
    }

    public JPopupMenu buildContextMenu(Object selected) {
        JPopupMenu menu = new JPopupMenu();
        return menu;
    }
}
